/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package h2_sensorData;

import h2_sensorData.exceptions.IllegalOrphanException;
import h2_sensorData.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mike
 */
public class H2_SensorsJpaController implements Serializable {

    public H2_SensorsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(H2_Sensors h2_Sensors) {
        if (h2_Sensors.getH2SensorDataCollection() == null) {
            h2_Sensors.setH2SensorDataCollection(new ArrayList<H2_SensorData>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            H2_Sensortypes sensortypesIdsensortypes = h2_Sensors.getSensortypesIdsensortypes();
            if (sensortypesIdsensortypes != null) {
                sensortypesIdsensortypes = em.getReference(sensortypesIdsensortypes.getClass(), sensortypesIdsensortypes.getIdsensortypes());
                h2_Sensors.setSensortypesIdsensortypes(sensortypesIdsensortypes);
            }
            H2_Locations locationsIdlocations = h2_Sensors.getLocationsIdlocations();
            if (locationsIdlocations != null) {
                locationsIdlocations = em.getReference(locationsIdlocations.getClass(), locationsIdlocations.getIdlocations());
                h2_Sensors.setLocationsIdlocations(locationsIdlocations);
            }
            Collection<H2_SensorData> attachedH2SensorDataCollection = new ArrayList<H2_SensorData>();
            for (H2_SensorData h2SensorDataCollectionH2_SensorDataToAttach : h2_Sensors.getH2SensorDataCollection()) {
                h2SensorDataCollectionH2_SensorDataToAttach = em.getReference(h2SensorDataCollectionH2_SensorDataToAttach.getClass(), h2SensorDataCollectionH2_SensorDataToAttach.getIdsensorData());
                attachedH2SensorDataCollection.add(h2SensorDataCollectionH2_SensorDataToAttach);
            }
            h2_Sensors.setH2SensorDataCollection(attachedH2SensorDataCollection);
            em.persist(h2_Sensors);
            if (sensortypesIdsensortypes != null) {
                sensortypesIdsensortypes.getH2SensorsCollection().add(h2_Sensors);
                sensortypesIdsensortypes = em.merge(sensortypesIdsensortypes);
            }
            if (locationsIdlocations != null) {
                locationsIdlocations.getH2SensorsCollection().add(h2_Sensors);
                locationsIdlocations = em.merge(locationsIdlocations);
            }
            for (H2_SensorData h2SensorDataCollectionH2_SensorData : h2_Sensors.getH2SensorDataCollection()) {
                H2_Sensors oldSensorsIdsensorsOfH2SensorDataCollectionH2_SensorData = h2SensorDataCollectionH2_SensorData.getSensorsIdsensors();
                h2SensorDataCollectionH2_SensorData.setSensorsIdsensors(h2_Sensors);
                h2SensorDataCollectionH2_SensorData = em.merge(h2SensorDataCollectionH2_SensorData);
                if (oldSensorsIdsensorsOfH2SensorDataCollectionH2_SensorData != null) {
                    oldSensorsIdsensorsOfH2SensorDataCollectionH2_SensorData.getH2SensorDataCollection().remove(h2SensorDataCollectionH2_SensorData);
                    oldSensorsIdsensorsOfH2SensorDataCollectionH2_SensorData = em.merge(oldSensorsIdsensorsOfH2SensorDataCollectionH2_SensorData);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(H2_Sensors h2_Sensors) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            H2_Sensors persistentH2_Sensors = em.find(H2_Sensors.class, h2_Sensors.getIdsensors());
            H2_Sensortypes sensortypesIdsensortypesOld = persistentH2_Sensors.getSensortypesIdsensortypes();
            H2_Sensortypes sensortypesIdsensortypesNew = h2_Sensors.getSensortypesIdsensortypes();
            H2_Locations locationsIdlocationsOld = persistentH2_Sensors.getLocationsIdlocations();
            H2_Locations locationsIdlocationsNew = h2_Sensors.getLocationsIdlocations();
            Collection<H2_SensorData> h2SensorDataCollectionOld = persistentH2_Sensors.getH2SensorDataCollection();
            Collection<H2_SensorData> h2SensorDataCollectionNew = h2_Sensors.getH2SensorDataCollection();
            List<String> illegalOrphanMessages = null;
            for (H2_SensorData h2SensorDataCollectionOldH2_SensorData : h2SensorDataCollectionOld) {
                if (!h2SensorDataCollectionNew.contains(h2SensorDataCollectionOldH2_SensorData)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain H2_SensorData " + h2SensorDataCollectionOldH2_SensorData + " since its sensorsIdsensors field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sensortypesIdsensortypesNew != null) {
                sensortypesIdsensortypesNew = em.getReference(sensortypesIdsensortypesNew.getClass(), sensortypesIdsensortypesNew.getIdsensortypes());
                h2_Sensors.setSensortypesIdsensortypes(sensortypesIdsensortypesNew);
            }
            if (locationsIdlocationsNew != null) {
                locationsIdlocationsNew = em.getReference(locationsIdlocationsNew.getClass(), locationsIdlocationsNew.getIdlocations());
                h2_Sensors.setLocationsIdlocations(locationsIdlocationsNew);
            }
            Collection<H2_SensorData> attachedH2SensorDataCollectionNew = new ArrayList<H2_SensorData>();
            for (H2_SensorData h2SensorDataCollectionNewH2_SensorDataToAttach : h2SensorDataCollectionNew) {
                h2SensorDataCollectionNewH2_SensorDataToAttach = em.getReference(h2SensorDataCollectionNewH2_SensorDataToAttach.getClass(), h2SensorDataCollectionNewH2_SensorDataToAttach.getIdsensorData());
                attachedH2SensorDataCollectionNew.add(h2SensorDataCollectionNewH2_SensorDataToAttach);
            }
            h2SensorDataCollectionNew = attachedH2SensorDataCollectionNew;
            h2_Sensors.setH2SensorDataCollection(h2SensorDataCollectionNew);
            h2_Sensors = em.merge(h2_Sensors);
            if (sensortypesIdsensortypesOld != null && !sensortypesIdsensortypesOld.equals(sensortypesIdsensortypesNew)) {
                sensortypesIdsensortypesOld.getH2SensorsCollection().remove(h2_Sensors);
                sensortypesIdsensortypesOld = em.merge(sensortypesIdsensortypesOld);
            }
            if (sensortypesIdsensortypesNew != null && !sensortypesIdsensortypesNew.equals(sensortypesIdsensortypesOld)) {
                sensortypesIdsensortypesNew.getH2SensorsCollection().add(h2_Sensors);
                sensortypesIdsensortypesNew = em.merge(sensortypesIdsensortypesNew);
            }
            if (locationsIdlocationsOld != null && !locationsIdlocationsOld.equals(locationsIdlocationsNew)) {
                locationsIdlocationsOld.getH2SensorsCollection().remove(h2_Sensors);
                locationsIdlocationsOld = em.merge(locationsIdlocationsOld);
            }
            if (locationsIdlocationsNew != null && !locationsIdlocationsNew.equals(locationsIdlocationsOld)) {
                locationsIdlocationsNew.getH2SensorsCollection().add(h2_Sensors);
                locationsIdlocationsNew = em.merge(locationsIdlocationsNew);
            }
            for (H2_SensorData h2SensorDataCollectionNewH2_SensorData : h2SensorDataCollectionNew) {
                if (!h2SensorDataCollectionOld.contains(h2SensorDataCollectionNewH2_SensorData)) {
                    H2_Sensors oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData = h2SensorDataCollectionNewH2_SensorData.getSensorsIdsensors();
                    h2SensorDataCollectionNewH2_SensorData.setSensorsIdsensors(h2_Sensors);
                    h2SensorDataCollectionNewH2_SensorData = em.merge(h2SensorDataCollectionNewH2_SensorData);
                    if (oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData != null && !oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData.equals(h2_Sensors)) {
                        oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData.getH2SensorDataCollection().remove(h2SensorDataCollectionNewH2_SensorData);
                        oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData = em.merge(oldSensorsIdsensorsOfH2SensorDataCollectionNewH2_SensorData);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = h2_Sensors.getIdsensors();
                if (findH2_Sensors(id) == null) {
                    throw new NonexistentEntityException("The h2_Sensors with id " + id + " no longer exists.");
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
            H2_Sensors h2_Sensors;
            try {
                h2_Sensors = em.getReference(H2_Sensors.class, id);
                h2_Sensors.getIdsensors();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The h2_Sensors with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<H2_SensorData> h2SensorDataCollectionOrphanCheck = h2_Sensors.getH2SensorDataCollection();
            for (H2_SensorData h2SensorDataCollectionOrphanCheckH2_SensorData : h2SensorDataCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This H2_Sensors (" + h2_Sensors + ") cannot be destroyed since the H2_SensorData " + h2SensorDataCollectionOrphanCheckH2_SensorData + " in its h2SensorDataCollection field has a non-nullable sensorsIdsensors field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            H2_Sensortypes sensortypesIdsensortypes = h2_Sensors.getSensortypesIdsensortypes();
            if (sensortypesIdsensortypes != null) {
                sensortypesIdsensortypes.getH2SensorsCollection().remove(h2_Sensors);
                sensortypesIdsensortypes = em.merge(sensortypesIdsensortypes);
            }
            H2_Locations locationsIdlocations = h2_Sensors.getLocationsIdlocations();
            if (locationsIdlocations != null) {
                locationsIdlocations.getH2SensorsCollection().remove(h2_Sensors);
                locationsIdlocations = em.merge(locationsIdlocations);
            }
            em.remove(h2_Sensors);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<H2_Sensors> findH2_SensorsEntities() {
        return findH2_SensorsEntities(true, -1, -1);
    }

    public List<H2_Sensors> findH2_SensorsEntities(int maxResults, int firstResult) {
        return findH2_SensorsEntities(false, maxResults, firstResult);
    }

    private List<H2_Sensors> findH2_SensorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(H2_Sensors.class));
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

    public H2_Sensors findH2_Sensors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(H2_Sensors.class, id);
        } finally {
            em.close();
        }
    }

    public int getH2_SensorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<H2_Sensors> rt = cq.from(H2_Sensors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
