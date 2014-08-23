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
import sensorData.exceptions.IllegalOrphanException;
import sensorData.exceptions.NonexistentEntityException;

/**
 *
 * @author Mike
 */
public class SensorsJpaController implements Serializable {
    
    private static SensorsJpaController instance = null;
    
    public static SensorsJpaController getInstance(EntityManagerFactory emf) {
        
        if (instance == null)
            instance = new SensorsJpaController(emf);
        
        return instance;
    }

    public SensorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sensors sensors) {
        if (sensors.getSensorDataCollection() == null) {
            sensors.setSensorDataCollection(new ArrayList<SensorData>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locations locationsIdlocations = sensors.getLocationsIdlocations();
            if (locationsIdlocations != null) {
                locationsIdlocations = em.getReference(locationsIdlocations.getClass(), locationsIdlocations.getIdlocations());
                sensors.setLocationsIdlocations(locationsIdlocations);
            }
            Collection<SensorData> attachedSensorDataCollection = new ArrayList<SensorData>();
            for (SensorData sensorDataCollectionSensorDataToAttach : sensors.getSensorDataCollection()) {
                sensorDataCollectionSensorDataToAttach = em.getReference(sensorDataCollectionSensorDataToAttach.getClass(), sensorDataCollectionSensorDataToAttach.getIdsensorData());
                attachedSensorDataCollection.add(sensorDataCollectionSensorDataToAttach);
            }
            sensors.setSensorDataCollection(attachedSensorDataCollection);
            em.persist(sensors);
            if (locationsIdlocations != null) {
                locationsIdlocations.getSensorsCollection().add(sensors);
                locationsIdlocations = em.merge(locationsIdlocations);
            }
            for (SensorData sensorDataCollectionSensorData : sensors.getSensorDataCollection()) {
                Sensors oldSensorsIdsensorsOfSensorDataCollectionSensorData = sensorDataCollectionSensorData.getSensorsIdsensors();
                sensorDataCollectionSensorData.setSensorsIdsensors(sensors);
                sensorDataCollectionSensorData = em.merge(sensorDataCollectionSensorData);
                if (oldSensorsIdsensorsOfSensorDataCollectionSensorData != null) {
                    oldSensorsIdsensorsOfSensorDataCollectionSensorData.getSensorDataCollection().remove(sensorDataCollectionSensorData);
                    oldSensorsIdsensorsOfSensorDataCollectionSensorData = em.merge(oldSensorsIdsensorsOfSensorDataCollectionSensorData);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sensors sensors) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sensors persistentSensors = em.find(Sensors.class, sensors.getIdsensors());
            Locations locationsIdlocationsOld = persistentSensors.getLocationsIdlocations();
            Locations locationsIdlocationsNew = sensors.getLocationsIdlocations();
            Collection<SensorData> sensorDataCollectionOld = persistentSensors.getSensorDataCollection();
            Collection<SensorData> sensorDataCollectionNew = sensors.getSensorDataCollection();
            List<String> illegalOrphanMessages = null;
            for (SensorData sensorDataCollectionOldSensorData : sensorDataCollectionOld) {
                if (!sensorDataCollectionNew.contains(sensorDataCollectionOldSensorData)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SensorData " + sensorDataCollectionOldSensorData + " since its sensorsIdsensors field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (locationsIdlocationsNew != null) {
                locationsIdlocationsNew = em.getReference(locationsIdlocationsNew.getClass(), locationsIdlocationsNew.getIdlocations());
                sensors.setLocationsIdlocations(locationsIdlocationsNew);
            }
            Collection<SensorData> attachedSensorDataCollectionNew = new ArrayList<SensorData>();
            for (SensorData sensorDataCollectionNewSensorDataToAttach : sensorDataCollectionNew) {
                sensorDataCollectionNewSensorDataToAttach = em.getReference(sensorDataCollectionNewSensorDataToAttach.getClass(), sensorDataCollectionNewSensorDataToAttach.getIdsensorData());
                attachedSensorDataCollectionNew.add(sensorDataCollectionNewSensorDataToAttach);
            }
            sensorDataCollectionNew = attachedSensorDataCollectionNew;
            sensors.setSensorDataCollection(sensorDataCollectionNew);
            sensors = em.merge(sensors);
            if (locationsIdlocationsOld != null && !locationsIdlocationsOld.equals(locationsIdlocationsNew)) {
                locationsIdlocationsOld.getSensorsCollection().remove(sensors);
                locationsIdlocationsOld = em.merge(locationsIdlocationsOld);
            }
            if (locationsIdlocationsNew != null && !locationsIdlocationsNew.equals(locationsIdlocationsOld)) {
                locationsIdlocationsNew.getSensorsCollection().add(sensors);
                locationsIdlocationsNew = em.merge(locationsIdlocationsNew);
            }
            for (SensorData sensorDataCollectionNewSensorData : sensorDataCollectionNew) {
                if (!sensorDataCollectionOld.contains(sensorDataCollectionNewSensorData)) {
                    Sensors oldSensorsIdsensorsOfSensorDataCollectionNewSensorData = sensorDataCollectionNewSensorData.getSensorsIdsensors();
                    sensorDataCollectionNewSensorData.setSensorsIdsensors(sensors);
                    sensorDataCollectionNewSensorData = em.merge(sensorDataCollectionNewSensorData);
                    if (oldSensorsIdsensorsOfSensorDataCollectionNewSensorData != null && !oldSensorsIdsensorsOfSensorDataCollectionNewSensorData.equals(sensors)) {
                        oldSensorsIdsensorsOfSensorDataCollectionNewSensorData.getSensorDataCollection().remove(sensorDataCollectionNewSensorData);
                        oldSensorsIdsensorsOfSensorDataCollectionNewSensorData = em.merge(oldSensorsIdsensorsOfSensorDataCollectionNewSensorData);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sensors.getIdsensors();
                if (findSensors(id) == null) {
                    throw new NonexistentEntityException("The sensors with id " + id + " no longer exists.");
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
            Sensors sensors;
            try {
                sensors = em.getReference(Sensors.class, id);
                sensors.getIdsensors();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sensors with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SensorData> sensorDataCollectionOrphanCheck = sensors.getSensorDataCollection();
            for (SensorData sensorDataCollectionOrphanCheckSensorData : sensorDataCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sensors (" + sensors + ") cannot be destroyed since the SensorData " + sensorDataCollectionOrphanCheckSensorData + " in its sensorDataCollection field has a non-nullable sensorsIdsensors field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Locations locationsIdlocations = sensors.getLocationsIdlocations();
            if (locationsIdlocations != null) {
                locationsIdlocations.getSensorsCollection().remove(sensors);
                locationsIdlocations = em.merge(locationsIdlocations);
            }
            em.remove(sensors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sensors> findSensorsEntities() {
        return findSensorsEntities(true, -1, -1);
    }

    public List<Sensors> findSensorsEntities(int maxResults, int firstResult) {
        return findSensorsEntities(false, maxResults, firstResult);
    }

    private List<Sensors> findSensorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sensors.class));
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

    public Sensors findSensors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sensors.class, id);
        } finally {
            em.close();
        }
    }

    public int getSensorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sensors> rt = cq.from(Sensors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String findLastNetwordID(String networkPrefix)
    {
        
        List<Sensors> results = null;
        
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();     // begin the transaction
        
        try
        {
            // create the query to retrieve the tuples

            Query q = em.createNamedQuery("Sensors.findAllTypes");
            q.setParameter("networkPrefix", networkPrefix + "%");
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

        return results.get(results.size() - 1).getNetworkId();
        
    }
    
}
