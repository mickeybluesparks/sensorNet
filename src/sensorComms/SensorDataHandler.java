/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorComms;

import LLAP.LLAPMessageParser;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sensorData.DatabaseFunctions;
import sensorData.Locations;
import sensorData.Sensortypes;


/**
 *
 * @author Mike
 */
public class SensorDataHandler implements SerialPortDataListener {

    private sensorComms port;
    private LLAPMessageParser msgParser;
    private DatabaseFunctions database;
    private final String ackMsg = "ACK------";
    private final String changeDeviceID = "CHDEVID";
    private final String rebootDevice = "REBOOT---";
    private final String setIntervalPeriod = "INTVL";
    
    private final String temperatureInterval = "030S";
    
    private String newSensorID;
    private String newSensorType;

    
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
        
        msgParser = new LLAPMessageParser();
        database = DatabaseFunctions.getInstance();
    }
    
    @Override
    public void receiveData(byte[] recBuffer) {
       
        String sensorID;
        String message;
        
        // wait to receive data and then parse it for LLAP message
     
        boolean result = msgParser.parseMessage(recBuffer);
        
        if (result)
        {
                
            sensorID = new String(msgParser.getDeviceID());
           
            // LLAP Message detected
            
            if (msgParser.receivedStartMsg())
            {
               // sensor just started up                             
               // send ACK Message
               
               message = "a" + sensorID + ackMsg;              
               port.sendMsg(message);
               
               // check network id for new sensor
               
               if (sensorID.equals("--"))
               {
                   // we have a new sensor
                   // notify user for the sensor type and location for the sensor
                   
                   JOptionPane.showMessageDialog(null, "New Sensor Detected", "New Sensor", JOptionPane.INFORMATION_MESSAGE);

                   List<Sensortypes> sensorTypeList = database.getListOfSensorTypes();
                   
                   String[] optionList = new String[sensorTypeList.size()];
                   int index = 0;
                   
                   for (Sensortypes sensorTypeData : sensorTypeList)
                   {
                       optionList[index] = sensorTypeData.getType();
                       
                       index++;
                   }
                   
                   newSensorType = (String)JOptionPane.showInputDialog(null, "Please select sensor type", "Sensor Type", 
                           JOptionPane.QUESTION_MESSAGE, null, optionList, "");
                   
                   // no select the room this sensor is in
                   
                   List<Locations> roomList = database.getListOfRooms();
                   
                   optionList = new String[roomList.size()];
                   index = 0;
                   
                   for (Locations roomData : roomList)
                   {
                       optionList[index] = roomData.getRoomName();
                       
                       index++;
                   }
                   
                   String room = (String)JOptionPane.showInputDialog(null, "Please select the room this sensor is in", "Room Selection", 
                           JOptionPane.QUESTION_MESSAGE, null, optionList, "");
                   
                   // now create the sensor record
                   
                   newSensorID = database.addNewSensor(newSensorType, room);
                   
                   // set new network ID
                   
                   message = "a" + sensorID + changeDeviceID + newSensorID;                  
                   port.sendMsg(message);                                     
               }
               else
               {
                   // set up this sensor to start sending data
                   
                   char networkPrefix = sensorID.charAt(0);
                   
                   if (networkPrefix == 'T')
                   {
                       message = "a" + sensorID + setIntervalPeriod + temperatureInterval;
                       port.sendMsg(message);                       
                   }                   
               }
             }
            
            if (msgParser.confirmDeviceIDChange())
            {
                if (msgParser.getMessageText().equals(newSensorID))
                {
                    // confirm device ID change by rebooting the device
                    
                    message = "a" + sensorID + rebootDevice;
                    port.sendMsg(message);
                    
                    newSensorID = "";
                    
                }
             }
            
            if (msgParser.receivedTemperature())
            {
                // we have a temperature reading to record
                
                database.addDataRecord(new Date(), sensorID, msgParser.getMessageValue());
                              
            }
        }               
    }    
}
