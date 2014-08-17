/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorComms;

/**
 *
 * @author Mike
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import purejavacomm.*;
/**
 *
 * @author Mike
 */
public class sensorComms implements SerialPortEventListener {
    
    // class member definitions
    
    private SerialPort m_port;
    private CommPortIdentifier m_portid = null;
    private InputStream m_input = null;
    private OutputStream m_output=  null;
    private byte[] m_recBuffer = null;
    private int m_recBufferLen = 0;
    private boolean mb_portOpen = false;
    private boolean mb_portCheck = false;
    private SerialPortDataListener m_dataListener = null;
    
    private static final int mc_portTimeout = 2000;
    
    // class methods
    
    public synchronized boolean isPortOpen()
    {
        return mb_portOpen;
    }
    
    public synchronized String[] listSerialPorts()
    {
        m_portid = null;
        ArrayList portList = new ArrayList();
        
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements())
        {
            m_portid = (CommPortIdentifier)portEnum.nextElement();

            portList.add(m_portid.getName());

        }
        
        String[] names = new String[portList.size()];
            
       names = (String[]) portList.toArray(names);
       
       return names;
       
    }
    
    public synchronized boolean openPort(String portName)
    {
        boolean portSelected = false;
        mb_portCheck = true;
        m_portid = null;
        
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements())
        {
            m_portid = (CommPortIdentifier)portEnum.nextElement();

            if (portName.equals(m_portid.getName()))
            {
                portSelected = true;
                break;
            }
        }
        
        if (!portSelected)
            return false;
        
        try {
            m_port = (SerialPort) m_portid.open("sensor Comms", 1000);
        } catch (PortInUseException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }

        // set up m_port parameters

        try {
            m_port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            m_port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // set up event listener on data available

            m_port.addEventListener(this);
        } catch (TooManyListenersException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }

        m_port.notifyOnDataAvailable(true);
        try {
            m_port.enableReceiveTimeout(mc_portTimeout);
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // set up m_input and m_output stream for the m_port

            m_input = m_port.getInputStream();
            m_output = m_port.getOutputStream();

        } catch (IOException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mb_portOpen = true;
        
        // check here that we are indeed connected to the sensor network base station
        
        m_recBufferLen = 0;
        sendMsg("+++");         // AT Wakeup command
        try {
            // wait for 'OK' - wait for 1 seconds in total before aborting
            
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        if ((m_recBufferLen == 0) || (m_recBuffer == null))
        {
            mb_portOpen = false;        // wrong port selected
            m_port.close();
            return false;            
        }
        
        // check for valid message return
        
        if ((m_recBuffer[0] == 'O') && (m_recBuffer[1] == 'K'))
        {           
            
            mb_portOpen = true;
            
            // Wait for 1.1 seconds for port to exit command mode
            
            try {
                Thread.currentThread().sleep(1100);
            } catch (InterruptedException ex) {
                Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // we have the correct port open
            mb_portCheck = false;
            return true;
        }
        
        mb_portOpen = false;        // wrong port selected
        m_port.close();
        return false;            

    }
    
    public synchronized void closePort()
    {
        if (!mb_portOpen)
            return;
        
        m_port.close();
        
        mb_portOpen = false;
    }
    
    public synchronized void sendMsg(String msg)
    {
        if (mb_portOpen)
        {
            if (msg.length() > 0)
            {
                try {
                    m_output.write(msg.getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }            
        }                
    }
    
    @Override
    public void serialEvent(SerialPortEvent ev) {
        
        if (ev.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            
            try {
                // receive Data
                
                m_recBufferLen = m_input.available();
                m_recBuffer = new byte[m_recBufferLen];
                m_recBufferLen = m_input.read(m_recBuffer, 0, m_recBufferLen);
              
                if (!mb_portCheck)
                    notifyListeners();
                
            } catch (IOException ex) {
                Logger.getLogger(sensorComms.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }
    }
    
    public synchronized void addSerialPortDataListener(SerialPortDataListener listener) throws TooManyListenersException
    {
        if (listener == null)
            throw new IllegalArgumentException("listener cannot be null");
        
        if (m_dataListener != null)       
            throw new TooManyListenersException();
        
        m_dataListener = listener;
    }
    
    public synchronized void removeSerialPortDataListener(SerialPortDataListener listener)
    {
        m_dataListener = null;        
    }
    
    private synchronized void notifyListeners()
    {
        if (m_dataListener != null)
            m_dataListener.receiveData(m_recBuffer);       
    }
    
}
