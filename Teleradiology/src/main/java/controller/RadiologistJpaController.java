/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Medicalstaff;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Radiologist;

/**
 *
 * @author INFO-H-400
 */
public class RadiologistJpaController implements Serializable {

    public RadiologistJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Radiologist radiologist) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Medicalstaff medicalstaff1OrphanCheck = radiologist.getMedicalstaff1();
        if (medicalstaff1OrphanCheck != null) {
            Radiologist oldRadiologistOfMedicalstaff1 = medicalstaff1OrphanCheck.getRadiologist();
            if (oldRadiologistOfMedicalstaff1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Medicalstaff " + medicalstaff1OrphanCheck + " already has an item of type Radiologist whose medicalstaff1 column cannot be null. Please make another selection for the medicalstaff1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicalstaff medicalstaff1 = radiologist.getMedicalstaff1();
            if (medicalstaff1 != null) {
                medicalstaff1 = em.getReference(medicalstaff1.getClass(), medicalstaff1.getIdMedicalStaff());
                radiologist.setMedicalstaff1(medicalstaff1);
            }
            em.persist(radiologist);
            if (medicalstaff1 != null) {
                medicalstaff1.setRadiologist(radiologist);
                medicalstaff1 = em.merge(medicalstaff1);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Radiologist radiologist) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Radiologist persistentRadiologist = em.find(Radiologist.class, radiologist.getId());
            Medicalstaff medicalstaff1Old = persistentRadiologist.getMedicalstaff1();
            Medicalstaff medicalstaff1New = radiologist.getMedicalstaff1();
            List<String> illegalOrphanMessages = null;
            if (medicalstaff1New != null && !medicalstaff1New.equals(medicalstaff1Old)) {
                Radiologist oldRadiologistOfMedicalstaff1 = medicalstaff1New.getRadiologist();
                if (oldRadiologistOfMedicalstaff1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Medicalstaff " + medicalstaff1New + " already has an item of type Radiologist whose medicalstaff1 column cannot be null. Please make another selection for the medicalstaff1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (medicalstaff1New != null) {
                medicalstaff1New = em.getReference(medicalstaff1New.getClass(), medicalstaff1New.getIdMedicalStaff());
                radiologist.setMedicalstaff1(medicalstaff1New);
            }
            radiologist = em.merge(radiologist);
            if (medicalstaff1Old != null && !medicalstaff1Old.equals(medicalstaff1New)) {
                medicalstaff1Old.setRadiologist(null);
                medicalstaff1Old = em.merge(medicalstaff1Old);
            }
            if (medicalstaff1New != null && !medicalstaff1New.equals(medicalstaff1Old)) {
                medicalstaff1New.setRadiologist(radiologist);
                medicalstaff1New = em.merge(medicalstaff1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = radiologist.getId();
                if (findRadiologist(id) == null) {
                    throw new NonexistentEntityException("The radiologist with id " + id + " no longer exists.");
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
            Radiologist radiologist;
            try {
                radiologist = em.getReference(Radiologist.class, id);
                radiologist.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The radiologist with id " + id + " no longer exists.", enfe);
            }
            Medicalstaff medicalstaff1 = radiologist.getMedicalstaff1();
            if (medicalstaff1 != null) {
                medicalstaff1.setRadiologist(null);
                medicalstaff1 = em.merge(medicalstaff1);
            }
            em.remove(radiologist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Radiologist> findRadiologistEntities() {
        return findRadiologistEntities(true, -1, -1);
    }

    public List<Radiologist> findRadiologistEntities(int maxResults, int firstResult) {
        return findRadiologistEntities(false, maxResults, firstResult);
    }

    private List<Radiologist> findRadiologistEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Radiologist.class));
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

    public Radiologist findRadiologist(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Radiologist.class, id);
        } finally {
            em.close();
        }
    }

    public int getRadiologistCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Radiologist> rt = cq.from(Radiologist.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
