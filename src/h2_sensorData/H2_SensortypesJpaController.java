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
public class H2_SensortypesJpaController implements Serializable {

    public H2_SensortypesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(H2_Sensortypes h2_Sensortypes) {
        if (h2_Sensortypes.getH2SensorsCollection() == null) {
            h2_Sensortypes.setH2SensorsCollection(new ArrayList<H2_Sensors>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<H2_Sensors> attachedH2SensorsCollection = new ArrayList<H2_Sensors>();
            for (H2_Sensors h2SensorsCollectionH2_SensorsToAttach : h2_Sensortypes.getH2SensorsCollection()) {
                h2SensorsCollectionH2_SensorsToAttach = em.getReference(h2SensorsCollectionH2_SensorsToAttach.getClass(), h2SensorsCollectionH2_SensorsToAttach.getIdsensors());
                attachedH2SensorsCollection.add(h2SensorsCollectionH2_SensorsToAttach);
            }
            h2_Sensortypes.setH2SensorsCollection(attachedH2SensorsCollection);
            em.persist(h2_Sensortypes);
            for (H2_Sensors h2SensorsCollectionH2_Sensors : h2_Sensortypes.getH2SensorsCollection()) {
                H2_Sensortypes oldSensortypesIdsensortypesOfH2SensorsCollectionH2_Sensors = h2SensorsCollectionH2_Sensors.getSensortypesIdsensortypes();
                h2SensorsCollectionH2_Sensors.setSensortypesIdsensortypes(h2_Sensortypes);
                h2SensorsCollectionH2_Sensors = em.merge(h2SensorsCollectionH2_Sensors);
                if (oldSensortypesIdsensortypesOfH2SensorsCollectionH2_Sensors != null) {
                    oldSensortypesIdsensortypesOfH2SensorsCollectionH2_Sensors.getH2SensorsCollection().remove(h2SensorsCollectionH2_Sensors);
                    oldSensortypesIdsensortypesOfH2SensorsCollectionH2_Sensors = em.merge(oldSensortypesIdsensortypesOfH2SensorsCollectionH2_Sensors);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(H2_Sensortypes h2_Sensortypes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            H2_Sensortypes persistentH2_Sensortypes = em.find(H2_Sensortypes.class, h2_Sensortypes.getIdsensortypes());
            Collection<H2_Sensors> h2SensorsCollectionOld = persistentH2_Sensortypes.getH2SensorsCollection();
            Collection<H2_Sensors> h2SensorsCollectionNew = h2_Sensortypes.getH2SensorsCollection();
            List<String> illegalOrphanMessages = null;
            for (H2_Sensors h2SensorsCollectionOldH2_Sensors : h2SensorsCollectionOld) {
                if (!h2SensorsCollectionNew.contains(h2SensorsCollectionOldH2_Sensors)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain H2_Sensors " + h2SensorsCollectionOldH2_Sensors + " since its sensortypesIdsensortypes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<H2_Sensors> attachedH2SensorsCollectionNew = new ArrayList<H2_Sensors>();
            for (H2_Sensors h2SensorsCollectionNewH2_SensorsToAttach : h2SensorsCollectionNew) {
                h2SensorsCollectionNewH2_SensorsToAttach = em.getReference(h2SensorsCollectionNewH2_SensorsToAttach.getClass(), h2SensorsCollectionNewH2_SensorsToAttach.getIdsensors());
                attachedH2SensorsCollectionNew.add(h2SensorsCollectionNewH2_SensorsToAttach);
            }
            h2SensorsCollectionNew = attachedH2SensorsCollectionNew;
            h2_Sensortypes.setH2SensorsCollection(h2SensorsCollectionNew);
            h2_Sensortypes = em.merge(h2_Sensortypes);
            for (H2_Sensors h2SensorsCollectionNewH2_Sensors : h2SensorsCollectionNew) {
                if (!h2SensorsCollectionOld.contains(h2SensorsCollectionNewH2_Sensors)) {
                    H2_Sensortypes oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors = h2SensorsCollectionNewH2_Sensors.getSensortypesIdsensortypes();
                    h2SensorsCollectionNewH2_Sensors.setSensortypesIdsensortypes(h2_Sensortypes);
                    h2SensorsCollectionNewH2_Sensors = em.merge(h2SensorsCollectionNewH2_Sensors);
                    if (oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors != null && !oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors.equals(h2_Sensortypes)) {
                        oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors.getH2SensorsCollection().remove(h2SensorsCollectionNewH2_Sensors);
                        oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors = em.merge(oldSensortypesIdsensortypesOfH2SensorsCollectionNewH2_Sensors);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = h2_Sensortypes.getIdsensortypes();
                if (findH2_Sensortypes(id) == null) {
                    throw new NonexistentEntityException("The h2_Sensortypes with id " + id + " no longer exists.");
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
            H2_Sensortypes h2_Sensortypes;
            try {
                h2_Sensortypes = em.getReference(H2_Sensortypes.class, id);
                h2_Sensortypes.getIdsensortypes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The h2_Sensortypes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<H2_Sensors> h2SensorsCollectionOrphanCheck = h2_Sensortypes.getH2SensorsCollection();
            for (H2_Sensors h2SensorsCollectionOrphanCheckH2_Sensors : h2SensorsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This H2_Sensortypes (" + h2_Sensortypes + ") cannot be destroyed since the H2_Sensors " + h2SensorsCollectionOrphanCheckH2_Sensors + " in its h2SensorsCollection field has a non-nullable sensortypesIdsensortypes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(h2_Sensortypes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<H2_Sensortypes> findH2_SensortypesEntities() {
        return findH2_SensortypesEntities(true, -1, -1);
    }

    public List<H2_Sensortypes> findH2_SensortypesEntities(int maxResults, int firstResult) {
        return findH2_SensortypesEntities(false, maxResults, firstResult);
    }

    private List<H2_Sensortypes> findH2_SensortypesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(H2_Sensortypes.class));
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

    public H2_Sensortypes findH2_Sensortypes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(H2_Sensortypes.class, id);
        } finally {
            em.close();
        }
    }

    public int getH2_SensortypesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<H2_Sensortypes> rt = cq.from(H2_Sensortypes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
