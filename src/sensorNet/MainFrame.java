/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorNet;

import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sensorData.*;

/**
 *
 * @author Mike
 */
public class MainFrame extends javax.swing.JFrame implements LocationDataListener {

    private DatabaseFunctions database = null;
    private Location_crud locationAdminDataView = null;

    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        database = DatabaseFunctions.getInstance();
        locationAdminDataView = new Location_crud(this, true);

        try {
            // add the action event listner
            
            locationAdminDataView.addRequestListener(this);
        } catch (TooManyListenersException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jStatusBar = new javax.swing.JPanel();
        statusMsg = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jFileMenu = new javax.swing.JMenu();
        jExitMenuItem = new javax.swing.JMenuItem();
        jAdminMenu = new javax.swing.JMenu();
        jRoomMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jStatusBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jStatusBar.setPreferredSize(new java.awt.Dimension(4, 20));

        statusMsg.setText(" ");

        javax.swing.GroupLayout jStatusBarLayout = new javax.swing.GroupLayout(jStatusBar);
        jStatusBar.setLayout(jStatusBarLayout);
        jStatusBarLayout.setHorizontalGroup(
            jStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jStatusBarLayout.createSequentialGroup()
                .addComponent(statusMsg)
                .addGap(0, 303, Short.MAX_VALUE))
        );
        jStatusBarLayout.setVerticalGroup(
            jStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jStatusBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(statusMsg))
        );

        jFileMenu.setText("File");

        jExitMenuItem.setText("Exit");
        jExitMenuItem.setToolTipText("Exit the application");
        jExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExitMenuItemActionPerformed(evt);
            }
        });
        jFileMenu.add(jExitMenuItem);

        jMenuBar1.add(jFileMenu);

        jAdminMenu.setText("Admin");

        jRoomMenuItem.setText("Rooms");
        jRoomMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRoomMenuItemActionPerformed(evt);
            }
        });
        jAdminMenu.add(jRoomMenuItem);

        jMenuBar1.add(jAdminMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(210, Short.MAX_VALUE)
                .addComponent(jStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void processWindowEvent(WindowEvent e)        
    {
        boolean result = false;
        
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            // close the database
            
            if (database != null)
               database.shutDownDatabase();
        }
        
        super.processWindowEvent(e);        
        
        if (e.getID() == WindowEvent.WINDOW_OPENED)
        {
            // Open the Database 
            
            statusMsg.setText("Opening Database.....");
             
            if (database != null)
                result = database.openDatabase();        

            if (result)
            {
               statusMsg.setText("Database Opened OK");
            }
            else
            {
                // display a message box with error message
                
                JOptionPane.showMessageDialog(this,  
                        "Database Failed to Open - Please Check Server","ERROR",            
                        JOptionPane.ERROR_MESSAGE);
            }
        }
       
    }
    
    private void refreshRoomList()
    {
        // retrieve the current room data records from the database and
        // update the table in the dialog class
        
        if (database == null)
            return;             // database has failed to open
        
        List<Locations> roomList = database.getListOfRooms();
        JTable roomTable = locationAdminDataView.getTable();
        DefaultTableModel table = (DefaultTableModel)roomTable.getModel();
        
        if (!roomList.isEmpty())
        {
            int rowIndex = 0;
            
            // add the tuples to the table for display
            
            for (Locations roomData : roomList)
            {
                if (rowIndex >= table.getRowCount())
                {
                    table.addRow(new Object[] {0, "....", false});
                }
                
                
                roomTable.setValueAt(roomData.getIdlocations(), rowIndex, 0);
                roomTable.setValueAt(roomData.getRoomName(), rowIndex, 1);
                
                if (roomData.getSensorsCollection().isEmpty())
                    roomTable.setValueAt(false, rowIndex, 2);
                else
                    roomTable.setValueAt(true, rowIndex, 2);
                
                rowIndex++;
                
            }
        }
        
    }
    
    private void jRoomMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRoomMenuItemActionPerformed
        // open up a new dialog to list all the existing rooms/locations
        // then able to add, edit. delete records
        
        refreshRoomList();
        
        locationAdminDataView.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        locationAdminDataView.setTitle("Room Database");
        locationAdminDataView.setVisible(true);
    }//GEN-LAST:event_jRoomMenuItemActionPerformed

    private void jExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExitMenuItemActionPerformed
        // close the application
        
        WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
    }//GEN-LAST:event_jExitMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jAdminMenu;
    private javax.swing.JMenuItem jExitMenuItem;
    private javax.swing.JMenu jFileMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jRoomMenuItem;
    private javax.swing.JPanel jStatusBar;
    private javax.swing.JLabel statusMsg;
    // End of variables declaration//GEN-END:variables

    @Override
    public void locationDatabaseAction(actionRequest request) {

        // open up an OptionPane window to ask for room name
        
        String roomName = JOptionPane.showInputDialog(this, "Enter Room Name", "Enter Data", 
                JOptionPane.QUESTION_MESSAGE);
    
        database.addRoom(roomName);
        
        // refresh the room list
        
        refreshRoomList();
        
    }
}
