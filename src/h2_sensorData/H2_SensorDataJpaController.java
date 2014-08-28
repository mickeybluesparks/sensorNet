/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package h2_sensorData;

import h2_sensorData.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Mike
 */
public class H2_SensorDataJpaController implements Serializable {

    public H2_SensorDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(H2_SensorData h2_SensorData) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            H2_Sensors sensorsIdsensors = h2_SensorData.getSensorsIdsensors();
            if (sensorsIdsensors != null) {
                sensorsIdsensors = em.getReference(sensorsIdsensors.getClass(), sensorsIdsensors.getIdsensors());
                h2_SensorData.setSensorsIdsensors(sensorsIdsensors);
            }
            em.persist(h2_SensorData);
            if (sensorsIdsensors != null) {
                sensorsIdsensors.getH2SensorDataCollection().add(h2_SensorData);
                sensorsIdsensors = em.merge(sensorsIdsensors);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(H2_SensorData h2_SensorData) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            H2_SensorData persistentH2_SensorData = em.find(H2_SensorData.class, h2_SensorData.getIdsensorData());
            H2_Sensors sensorsIdsensorsOld = persistentH2_SensorData.getSensorsIdsensors();
            H2_Sensors sensorsIdsensorsNew = h2_SensorData.getSensorsIdsensors();
            if (sensorsIdsensorsNew != null) {
                sensorsIdsensorsNew = em.getReference(sensorsIdsensorsNew.getClass(), sensorsIdsensorsNew.getIdsensors());
                h2_SensorData.setSensorsIdsensors(sensorsIdsensorsNew);
            }
            h2_SensorData = em.merge(h2_SensorData);
            if (sensorsIdsensorsOld != null && !sensorsIdsensorsOld.equals(sensorsIdsensorsNew)) {
                sensorsIdsensorsOld.getH2SensorDataCollection().remove(h2_SensorData);
                sensorsIdsensorsOld = em.merge(sensorsIdsensorsOld);
            }
            if (sensorsIdsensorsNew != null && !sensorsIdsensorsNew.equals(sensorsIdsensorsOld)) {
                sensorsIdsensorsNew.getH2SensorDataCollection().add(h2_SensorData);
                sensorsIdsensorsNew = em.merge(sensorsIdsensorsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = h2_SensorData.getIdsensorData();
                if (findH2_SensorData(id) == null) {
                    throw new NonexistentEntityException("The h2_SensorData with id " + id + " no longer exists.");
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
            H2_SensorData h2_SensorData;
            try {
                h2_SensorData = em.getReference(H2_SensorData.class, id);
                h2_SensorData.getIdsensorData();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The h2_SensorData with id " + id + " no longer exists.", enfe);
            }
            H2_Sensors sensorsIdsensors = h2_SensorData.getSensorsIdsensors();
            if (sensorsIdsensors != null) {
                sensorsIdsensors.getH2SensorDataCollection().remove(h2_SensorData);
                sensorsIdsensors = em.merge(sensorsIdsensors);
            }
            em.remove(h2_SensorData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<H2_SensorData> findH2_SensorDataEntities() {
        return findH2_SensorDataEntities(true, -1, -1);
    }

    public List<H2_SensorData> findH2_SensorDataEntities(int maxResults, int firstResult) {
        return findH2_SensorDataEntities(false, maxResults, firstResult);
    }

    private List<H2_SensorData> findH2_SensorDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(H2_SensorData.class));
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

    public H2_SensorData findH2_SensorData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(H2_SensorData.class, id);
        } finally {
            em.close();
        }
    }

    public int getH2_SensorDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<H2_SensorData> rt = cq.from(H2_SensorData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
