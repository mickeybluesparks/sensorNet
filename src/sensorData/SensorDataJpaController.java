/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sensorData.exceptions.NonexistentEntityException;

/**
 *
 * @author Mike
 */
public class SensorDataJpaController implements Serializable {

    private static SensorDataJpaController instance = null;
     
    public static SensorDataJpaController getInstance(EntityManagerFactory emf)
    {
        
        if (instance == null)
            instance = new SensorDataJpaController(emf);
            
        return instance;
    }
    
    public SensorDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SensorData sensorData) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sensors sensorsIdsensors = sensorData.getSensorsIdsensors();
            if (sensorsIdsensors != null) {
                sensorsIdsensors = em.getReference(sensorsIdsensors.getClass(), sensorsIdsensors.getIdsensors());
                sensorData.setSensorsIdsensors(sensorsIdsensors);
            }
            em.persist(sensorData);
            if (sensorsIdsensors != null) {
                sensorsIdsensors.getSensorDataCollection().add(sensorData);
                sensorsIdsensors = em.merge(sensorsIdsensors);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SensorData sensorData) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SensorData persistentSensorData = em.find(SensorData.class, sensorData.getIdsensorData());
            Sensors sensorsIdsensorsOld = persistentSensorData.getSensorsIdsensors();
            Sensors sensorsIdsensorsNew = sensorData.getSensorsIdsensors();
            if (sensorsIdsensorsNew != null) {
                sensorsIdsensorsNew = em.getReference(sensorsIdsensorsNew.getClass(), sensorsIdsensorsNew.getIdsensors());
                sensorData.setSensorsIdsensors(sensorsIdsensorsNew);
            }
            sensorData = em.merge(sensorData);
            if (sensorsIdsensorsOld != null && !sensorsIdsensorsOld.equals(sensorsIdsensorsNew)) {
                sensorsIdsensorsOld.getSensorDataCollection().remove(sensorData);
                sensorsIdsensorsOld = em.merge(sensorsIdsensorsOld);
            }
            if (sensorsIdsensorsNew != null && !sensorsIdsensorsNew.equals(sensorsIdsensorsOld)) {
                sensorsIdsensorsNew.getSensorDataCollection().add(sensorData);
                sensorsIdsensorsNew = em.merge(sensorsIdsensorsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sensorData.getIdsensorData();
                if (findSensorData(id) == null) {
                    throw new NonexistentEntityException("The sensorData with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SensorData sensorData;
            try {
                sensorData = em.getReference(SensorData.class, id);
                sensorData.getIdsensorData();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sensorData with id " + id + " no longer exists.", enfe);
            }
            Sensors sensorsIdsensors = sensorData.getSensorsIdsensors();
            if (sensorsIdsensors != null) {
                sensorsIdsensors.getSensorDataCollection().remove(sensorData);
                sensorsIdsensors = em.merge(sensorsIdsensors);
            }
            em.remove(sensorData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SensorData> findSensorDataEntities() {
        return findSensorDataEntities(true, -1, -1);
    }

    public List<SensorData> findSensorDataEntities(int maxResults, int firstResult) {
        return findSensorDataEntities(false, maxResults, firstResult);
    }

    private List<SensorData> findSensorDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SensorData.class));
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

    public SensorData findSensorData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SensorData.class, id);
        } finally {
            em.close();
        }
    }

    public int getSensorDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SensorData> rt = cq.from(SensorData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
