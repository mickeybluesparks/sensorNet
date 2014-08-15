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
import javax.persistence.EntityTransaction;

/**
 *
 * @author Mike
 */
public class DatabaseFunctions {

    private static DatabaseFunctions instance = null;
    
    private LocationsJpaController controller = null;
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
    
}
