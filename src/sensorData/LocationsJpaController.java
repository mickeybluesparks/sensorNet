/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import sensorData.exceptions.IllegalOrphanException;
import sensorData.exceptions.NonexistentEntityException;

/**
 *
 * @author Mike
 */
public class LocationsJpaController implements Serializable {
    
    private static LocationsJpaController instance = null;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    
    public LocationsJpaController()
    {
        // generate emf here

        try 
        {
            emf = Persistence.createEntityManagerFactory("sensorNetPU");
            em = emf.createEntityManager();
            Logger.getLogger(LocationsJpaController.class.getName()).log(Level.INFO, "Database Connected");       
        }
        catch (IllegalStateException ex)
        {
            Logger.getLogger(LocationsJpaController.class.getName()).log(Level.SEVERE, "Entity Manager/Factory not created");
            throw ex;
        }

    }
    
    public static LocationsJpaController getInstance()
    {
        if (instance == null)
            instance = new LocationsJpaController();
        
        return instance;     
    }

    public LocationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManagerFactory getEntityManagerFactory()
    {
        return emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public boolean shutDown()
    {
        boolean success = true;
        
        try
        {
            em.close();
            emf.close();
        }
        catch (IllegalStateException ex)
        {
            Logger.getLogger(LocationsJpaController.class.getName()).log(Level.SEVERE, "Entity Manager/Factory shutdown fail");
            throw ex;           
        }
        
        Logger.getLogger(LocationsJpaController.class.getName()).log(Level.INFO, "Database shutdown ok");
        
        return success;
    }


    public void create(Locations locations) {
        if (locations.getSensorsCollection() == null) {
            locations.setSensorsCollection(new ArrayList<Sensors>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sensors> attachedSensorsCollection = new ArrayList<Sensors>();
            for (Sensors sensorsCollectionSensorsToAttach : locations.getSensorsCollection()) {
                sensorsCollectionSensorsToAttach = em.getReference(sensorsCollectionSensorsToAttach.getClass(), sensorsCollectionSensorsToAttach.getIdsensors());
                attachedSensorsCollection.add(sensorsCollectionSensorsToAttach);
            }
            locations.setSensorsCollection(attachedSensorsCollection);
            em.persist(locations);
            for (Sensors sensorsCollectionSensors : locations.getSensorsCollection()) {
                Locations oldLocationsIdlocationsOfSensorsCollectionSensors = sensorsCollectionSensors.getLocationsIdlocations();
                sensorsCollectionSensors.setLocationsIdlocations(locations);
                sensorsCollectionSensors = em.merge(sensorsCollectionSensors);
                if (oldLocationsIdlocationsOfSensorsCollectionSensors != null) {
                    oldLocationsIdlocationsOfSensorsCollectionSensors.getSensorsCollection().remove(sensorsCollectionSensors);
                    oldLocationsIdlocationsOfSensorsCollectionSensors = em.merge(oldLocationsIdlocationsOfSensorsCollectionSensors);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locations locations) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locations persistentLocations = em.find(Locations.class, locations.getIdlocations());
            Collection<Sensors> sensorsCollectionOld = persistentLocations.getSensorsCollection();
            Collection<Sensors> sensorsCollectionNew = locations.getSensorsCollection();
            List<String> illegalOrphanMessages = null;
            for (Sensors sensorsCollectionOldSensors : sensorsCollectionOld) {
                if (!sensorsCollectionNew.contains(sensorsCollectionOldSensors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sensors " + sensorsCollectionOldSensors + " since its locationsIdlocations field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sensors> attachedSensorsCollectionNew = new ArrayList<Sensors>();
            for (Sensors sensorsCollectionNewSensorsToAttach : sensorsCollectionNew) {
                sensorsCollectionNewSensorsToAttach = em.getReference(sensorsCollectionNewSensorsToAttach.getClass(), sensorsCollectionNewSensorsToAttach.getIdsensors());
                attachedSensorsCollectionNew.add(sensorsCollectionNewSensorsToAttach);
            }
            sensorsCollectionNew = attachedSensorsCollectionNew;
            locations.setSensorsCollection(sensorsCollectionNew);
            locations = em.merge(locations);
            for (Sensors sensorsCollectionNewSensors : sensorsCollectionNew) {
                if (!sensorsCollectionOld.contains(sensorsCollectionNewSensors)) {
                    Locations oldLocationsIdlocationsOfSensorsCollectionNewSensors = sensorsCollectionNewSensors.getLocationsIdlocations();
                    sensorsCollectionNewSensors.setLocationsIdlocations(locations);
                    sensorsCollectionNewSensors = em.merge(sensorsCollectionNewSensors);
                    if (oldLocationsIdlocationsOfSensorsCollectionNewSensors != null && !oldLocationsIdlocationsOfSensorsCollectionNewSensors.equals(locations)) {
                        oldLocationsIdlocationsOfSensorsCollectionNewSensors.getSensorsCollection().remove(sensorsCollectionNewSensors);
                        oldLocationsIdlocationsOfSensorsCollectionNewSensors = em.merge(oldLocationsIdlocationsOfSensorsCollectionNewSensors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locations.getIdlocations();
                if (findLocations(id) == null) {
                    throw new NonexistentEntityException("The locations with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locations locations;
            try {
                locations = em.getReference(Locations.class, id);
                locations.getIdlocations();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locations with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sensors> sensorsCollectionOrphanCheck = locations.getSensorsCollection();
            for (Sensors sensorsCollectionOrphanCheckSensors : sensorsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locations (" + locations + ") cannot be destroyed since the Sensors " + sensorsCollectionOrphanCheckSensors + " in its sensorsCollection field has a non-nullable locationsIdlocations field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(locations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locations> findLocationsEntities() {
        return findLocationsEntities(true, -1, -1);
    }

    public List<Locations> findLocationsEntities(int maxResults, int firstResult) {
        return findLocationsEntities(false, maxResults, firstResult);
    }

    private List<Locations> findLocationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locations.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Locations findLocations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locations.class, id);
        } finally {
            em.close();
        }
    }
    
    public Locations findLocationByName(String name)
    {
        List<Locations> results = null;
        
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();     // begin the transaction
        
        try
        {
            // create the query to retrieve the tuples

            Query q = em.createNamedQuery("Locations.findByRoomName");
            q.setParameter("roomName", name);
            results = q.getResultList();
            tx.commit();
        }
        catch (Exception ex)
        {
            Logger.getLogger(LocationsJpaController.class.getName()).log(Level.SEVERE, "Exception - ", ex);
            tx.rollback();
        }
        
        if (results.isEmpty())
            return null;
        
        return (Locations)results.get(0);
    }

    public int getLocationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locations> rt = cq.from(Locations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
