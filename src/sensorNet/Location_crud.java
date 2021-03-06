/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorNet;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.TooManyListenersException;
import javax.swing.JTable;

/**
 *
 * @author Mike
 */
public class Location_crud extends javax.swing.JDialog
{

    private LocationDataListener m_requestListener;
    
    /**
     * Creates new form Location_crud
     */
    public Location_crud(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.m_requestListener = null;
        initComponents();
                
        
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLocationTable = new javax.swing.JTable();
        btnAddLocRecord = new javax.swing.JButton();
        btnRemoveLocRecord = new javax.swing.JButton();
        btnEditLocRecord = new javax.swing.JButton();
        jExitLocDialog = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLocationTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLocationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Room", "Sensors"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jLocationTable.setCellSelectionEnabled(false);
        jLocationTable.setRowSelectionAllowed(true);
        jLocationTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLocationTable.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jLocationTable);
        jLocationTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnAddLocRecord.setText("Add");
        btnAddLocRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLocRecordActionPerformed(evt);
            }
        });

        btnRemoveLocRecord.setText("Remove");
        btnRemoveLocRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveLocRecordActionPerformed(evt);
            }
        });

        btnEditLocRecord.setText("Edit");

        jExitLocDialog.setText("Exit");
        jExitLocDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExitLocDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnAddLocRecord)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btnRemoveLocRecord)
                            .addComponent(jExitLocDialog))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditLocRecord)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEditLocRecord)
                    .addComponent(btnRemoveLocRecord)
                    .addComponent(btnAddLocRecord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jExitLocDialog)
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jExitLocDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExitLocDialogActionPerformed
        WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

    }//GEN-LAST:event_jExitLocDialogActionPerformed

    private void btnAddLocRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLocRecordActionPerformed
        notifyListeners(LocationDataListener.actionRequest.ADD);
    }//GEN-LAST:event_btnAddLocRecordActionPerformed

    private void btnRemoveLocRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveLocRecordActionPerformed
        notifyListeners(LocationDataListener.actionRequest.DELETE);
    }//GEN-LAST:event_btnRemoveLocRecordActionPerformed

    public JTable getTable()
    {
        return jLocationTable;
    }
            
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
            java.util.logging.Logger.getLogger(Location_crud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Location_crud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Location_crud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Location_crud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Location_crud dialog = new Location_crud(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddLocRecord;
    private javax.swing.JButton btnEditLocRecord;
    private javax.swing.JButton btnRemoveLocRecord;
    private javax.swing.JButton jExitLocDialog;
    private javax.swing.JTable jLocationTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
    public void addRequestListener(LocationDataListener listener) throws TooManyListenersException
    {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");
        
        if (m_requestListener != null)       
            throw new TooManyListenersException();
        
        m_requestListener = listener;       
    }
    
    public void removeRequestListener(LocationDataListener listener)
    {
        m_requestListener = null;
    }
    
    private void notifyListeners(LocationDataListener.actionRequest request)
    {
        if (m_requestListener != null)
            m_requestListener.locationDatabaseAction(request);
    }
    
}