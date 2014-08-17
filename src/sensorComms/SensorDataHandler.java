/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorComms;

import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class SensorDataHandler implements SerialPortDataListener {

    private sensorComms port;
    
    
    public SensorDataHandler(sensorComms sensorPort)
    {
        this.port = sensorPort;       
    }
    
    public void enable()
    {
        try {
            
            // add the data listener

            port.addSerialPortDataListener(this);
        } catch (TooManyListenersException ex) {
            Logger.getLogger(SensorDataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Override
    public void receiveData(byte[] recBuffer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
