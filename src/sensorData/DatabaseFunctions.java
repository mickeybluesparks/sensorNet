/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import sensorData.exceptions.*;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import sensorReports.locationDataInfo;

/**
 *
 * @author Mike
 */
public class DatabaseFunctions {

    private static DatabaseFunctions instance = null;
    
    private LocationsJpaController controller = null;
    private SensortypesJpaController sensorTypeController = null;
    private SensorsJpaController sensorController = null;
    private SensorDataJpaController sensorDataController = null;
    private EntityManager em = null;

    
    // ensure this is a singleton class
    
    public static DatabaseFunctions getInstance()
    {
        if (instance == null)
            instance = new DatabaseFunctions();
        
        return instance;
    }
    
    public boolean openDatabase()
    {
        
        controller = LocationsJpaController.getInstance();
        EntityManagerFactory emf = controller.getEntityManagerFactory();
        sensorTypeController = SensortypesJpaController.getInstance(emf);
        sensorController = SensorsJpaController.getInstance(emf);
        sensorDataController = SensorDataJpaController.getInstance(emf);
        
        return true;
    }
    
    public boolean shutDownDatabase()
    {
        if (controller != null)
        {
            controller.shutDown();
            return true;
        }
        
        Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, "Tried to close an unopened Database");
        
        return false;
    }
    
    public List<Locations> getListOfRooms()
    {        
        return controller.findLocationsEntities();
    }
    
    public boolean addRoom(String name)
    {
        Locations roomData = new Locations();
        
        roomData.setRoomName(name);
        
        controller.create(roomData);
        
        return true;
    }
    
    public boolean addSensorType(String sensorType, String prefix) throws Exception
    {
        Sensortypes sensorTypeData = new Sensortypes();
        
        sensorTypeData.setType(sensorType);
        sensorTypeData.setNetworkprefix(prefix);
        
        sensorTypeController.create(sensorTypeData);
        
        return true;
    }
    
    public void deleteRoomRecord(String name) throws IllegalOrphanException
    {
        Locations roomData = controller.findLocationByName(name);
        
        if (roomData == null)
            return;
        
        if (roomData.getSensorsCollection().isEmpty())
        {

            try {
                controller.destroy(roomData.getIdlocations());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            // cannot delete this room as there is sensor data attached to it
            
            JOptionPane.showMessageDialog(null, "Sensor Data attached to room. Can Not Delete", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public boolean isRoomInDatabase(String name)
    {
        
        Locations roomData = controller.findLocationByName(name);
        
        if (roomData == null)
            return true;
        
        return false;
        
    }

    public List<Sensortypes> getListOfSensorTypes() {

        return sensorTypeController.findSensortypesEntities();
   }

    public void deleteSensorTypeRecord(String sensorType) throws NonexistentEntityException {
   
        Sensortypes sensorTypeData = sensorTypeController.findSensorByType(sensorType);
        
        if (sensorTypeData == null)
            return;
        try {
            sensorTypeController.destroy(sensorTypeData.getIdsensorTypes());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String addNewSensor(String type, String roomName)
    {
        Sensors sensor = new Sensors();     // create a new record
        
        // determine sensor network ID
        
        Sensortypes sensorTypeRecord = sensorTypeController.findSensorByType(type);
        
        String networkID = sensorTypeRecord.getNetworkprefix();
        
        if (sensorController.getSensorsCount() > 0)
        {
            String lastNetworkID = sensorController.findLastNetwordID(networkID);
                       
            networkID += (char)(lastNetworkID.charAt(1) + 1);
            
        }
        else
            networkID += "A";
        
        sensor.setNetworkId(networkID);
        sensor.setLocationsIdlocations(controller.findLocationByName(roomName));
        sensor.setSensorTypesidsensorTypes(sensorTypeRecord);
        sensor.setActive(true);
        
        sensorController.create(sensor);
        
        // modify the appropiate location record to link to this sensor
        
        Locations roomRecord = controller.findLocationByName(roomName);
        Collection<Sensors> sensorList = roomRecord.getSensorsCollection();
        
        if (sensorList == null)
        {
            sensorList = new ArrayList<Sensors>();
            roomRecord.setSensorsCollection(sensorList);
        }
        
        sensorList.add(sensor);
        try {
            controller.edit(roomRecord);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // now modify the appropiate sensor type record to link to this sensor
        
        sensorList = sensorTypeRecord.getSensorsCollection();
        
        if (sensorList == null)
        {
            sensorList = new ArrayList<Sensors>();
            sensorTypeRecord.setSensorsCollection(sensorList);
        }
        
        sensorList.add(sensor);
        try {
            sensorTypeController.edit(sensorTypeRecord);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return networkID;
    }

    public void addDataRecord(Date timeStamp, String sensorID, double messageValue) {
        
        Sensors sensor = sensorController.findActiveSensor(sensorID);
        
        if (sensor == null)
            return;             // error - no active sensor found
        
        SensorData dataRec = new SensorData();
        
        dataRec.setTimeStamp(timeStamp);
        dataRec.setValue1((float)messageValue);
        dataRec.setSensorsIdsensors(sensor);
        
        sensorDataController.create(dataRec);
        
    }
    
     public List<locationDataInfo> getLocationDataInformation()
    {
        List<locationDataInfo> results = new ArrayList();
        
        // step 1 - get a list of locations
        
        List<Locations> roomList = getListOfRooms();
        
        // step 2 - skip those locations with no sensors attached
        
        for (Locations roomData : roomList )
        {
            if (!(roomData.getSensorsCollection() == null) && 
                    (!roomData.getSensorsCollection().isEmpty()))
            {
                
                ArrayList sensorList = (ArrayList)roomData.getSensorsCollection();
                
                for (int i = 0; i < sensorList.size(); i++)
                {
                
                    // step 3 - create a locationDataInfo record for each sensor
                    // attached to this room

                    locationDataInfo info = new locationDataInfo();

                    // step 4 - fill in the data in the locationDataInfo record

                    info.setLocationName(roomData.getRoomName());

                    // step 4a - get the sensor record
                    
                    int sensorID = ((Sensors)sensorList.get(i)).getIdsensors();                   
                    Sensors sensor = sensorController.findSensors(sensorID);
                    
                    info.setSensorID(sensor.getNetworkId());
                    info.setSensorState(sensor.getActive());
                    
                    // step 4b - get the sensor type
                    
                    int sensorTypeID = sensor.getSensorTypesidsensorTypes().getIdsensorTypes();                   
                    Sensortypes sensorType = 
                            sensorTypeController.findSensortypes(sensorTypeID);
                    
                    info.setSensorType(sensorType.getType());
                    
                    int dataRecordCount = sensorDataController.getSensorDataCount();
                    info.setNumOfRecords(dataRecordCount);
                    
                    // step 4c - get the start date of sensor data records
                    
                    List<SensorData> dataList = sensorDataController.findSensorDataEntities(1, 0);
                    info.setStartDate(dataList.get(0).getTimeStamp());
                    
                    // step 4d - get the end data of the sensor data records
                    
                    dataList = sensorDataController.findSensorDataEntities(1, dataRecordCount-1);
                    info.setStartDate(dataList.get(0).getTimeStamp());                    

                    // step 5 - add this into list
                    
                    results.add(info);
                }
            }          
        }
        
        
        
        return results;
    }
    
}
