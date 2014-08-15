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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sensorData.exceptions.IllegalOrphanException;
import sensorData.exceptions.NonexistentEntityException;
import sensorData.exceptions.PreexistingEntityException;

/**
 *
 * @author Mike
 */
public class SensortypesJpaController implements Serializable {

    public SensortypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sensortypes sensortypes) throws PreexistingEntityException, Exception {
        if (sensortypes.getSensorsCollection() == null) {
            sensortypes.setSensorsCollection(new ArrayList<Sensors>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sensors> attachedSensorsCollection = new ArrayList<Sensors>();
            for (Sensors sensorsCollectionSensorsToAttach : sensortypes.getSensorsCollection()) {
                sensorsCollectionSensorsToAttach = em.getReference(sensorsCollectionSensorsToAttach.getClass(), sensorsCollectionSensorsToAttach.getIdsensors());
                attachedSensorsCollection.add(sensorsCollectionSensorsToAttach);
            }
            sensortypes.setSensorsCollection(attachedSensorsCollection);
            em.persist(sensortypes);
            for (Sensors sensorsCollectionSensors : sensortypes.getSensorsCollection()) {
                Sensortypes oldSensorTypesidsensorTypesOfSensorsCollectionSensors = sensorsCollectionSensors.getSensorTypesidsensorTypes();
                sensorsCollectionSensors.setSensorTypesidsensorTypes(sensortypes);
                sensorsCollectionSensors = em.merge(sensorsCollectionSensors);
                if (oldSensorTypesidsensorTypesOfSensorsCollectionSensors != null) {
                    oldSensorTypesidsensorTypesOfSensorsCollectionSensors.getSensorsCollection().remove(sensorsCollectionSensors);
                    oldSensorTypesidsensorTypesOfSensorsCollectionSensors = em.merge(oldSensorTypesidsensorTypesOfSensorsCollectionSensors);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSensortypes(sensortypes.getIdsensorTypes()) != null) {
                throw new PreexistingEntityException("Sensortypes " + sensortypes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sensortypes sensortypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sensortypes persistentSensortypes = em.find(Sensortypes.class, sensortypes.getIdsensorTypes());
            Collection<Sensors> sensorsCollectionOld = persistentSensortypes.getSensorsCollection();
            Collection<Sensors> sensorsCollectionNew = sensortypes.getSensorsCollection();
            List<String> illegalOrphanMessages = null;
            for (Sensors sensorsCollectionOldSensors : sensorsCollectionOld) {
                if (!sensorsCollectionNew.contains(sensorsCollectionOldSensors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sensors " + sensorsCollectionOldSensors + " since its sensorTypesidsensorTypes field is not nullable.");
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
            sensortypes.setSensorsCollection(sensorsCollectionNew);
            sensortypes = em.merge(sensortypes);
            for (Sensors sensorsCollectionNewSensors : sensorsCollectionNew) {
                if (!sensorsCollectionOld.contains(sensorsCollectionNewSensors)) {
                    Sensortypes oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors = sensorsCollectionNewSensors.getSensorTypesidsensorTypes();
                    sensorsCollectionNewSensors.setSensorTypesidsensorTypes(sensortypes);
                    sensorsCollectionNewSensors = em.merge(sensorsCollectionNewSensors);
                    if (oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors != null && !oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors.equals(sensortypes)) {
                        oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors.getSensorsCollection().remove(sensorsCollectionNewSensors);
                        oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors = em.merge(oldSensorTypesidsensorTypesOfSensorsCollectionNewSensors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sensortypes.getIdsensorTypes();
                if (findSensortypes(id) == null) {
                    throw new NonexistentEntityException("The sensortypes with id " + id + " no longer exists.");
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
            Sensortypes sensortypes;
            try {
                sensortypes = em.getReference(Sensortypes.class, id);
                sensortypes.getIdsensorTypes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sensortypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sensors> sensorsCollectionOrphanCheck = sensortypes.getSensorsCollection();
            for (Sensors sensorsCollectionOrphanCheckSensors : sensorsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sensortypes (" + sensortypes + ") cannot be destroyed since the Sensors " + sensorsCollectionOrphanCheckSensors + " in its sensorsCollection field has a non-nullable sensorTypesidsensorTypes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sensortypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sensortypes> findSensortypesEntities() {
        return findSensortypesEntities(true, -1, -1);
    }

    public List<Sensortypes> findSensortypesEntities(int maxResults, int firstResult) {
        return findSensortypesEntities(false, maxResults, firstResult);
    }

    private List<Sensortypes> findSensortypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sensortypes.class));
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

    public Sensortypes findSensortypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sensortypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSensortypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sensortypes> rt = cq.from(Sensortypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
