/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import sensorData.exceptions.*;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.swing.JOptionPane;

/**
 *
 * @author Mike
 */
public class DatabaseFunctions {

    private static DatabaseFunctions instance = null;
    
    private LocationsJpaController controller = null;
    private SensortypesJpaController sensorTypeController = null;
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
    
}
