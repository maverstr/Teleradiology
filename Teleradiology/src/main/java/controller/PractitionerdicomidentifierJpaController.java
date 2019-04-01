/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Medicalstaff;
import model.Practitionerdicomidentifier;

/**
 *
 * @author INFO-H-400
 */
public class PractitionerdicomidentifierJpaController implements Serializable {

    public PractitionerdicomidentifierJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Practitionerdicomidentifier practitionerdicomidentifier) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicalstaff practitioner = practitionerdicomidentifier.getPractitioner();
            if (practitioner != null) {
                practitioner = em.getReference(practitioner.getClass(), practitioner.getIdMedicalStaff());
                practitionerdicomidentifier.setPractitioner(practitioner);
            }
            em.persist(practitionerdicomidentifier);
            if (practitioner != null) {
                practitioner.getPractitionerdicomidentifierCollection().add(practitionerdicomidentifier);
                practitioner = em.merge(practitioner);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Practitionerdicomidentifier practitionerdicomidentifier) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Practitionerdicomidentifier persistentPractitionerdicomidentifier = em.find(Practitionerdicomidentifier.class, practitionerdicomidentifier.getIdPractitionerDicomIdentifier());
            Medicalstaff practitionerOld = persistentPractitionerdicomidentifier.getPractitioner();
            Medicalstaff practitionerNew = practitionerdicomidentifier.getPractitioner();
            if (practitionerNew != null) {
                practitionerNew = em.getReference(practitionerNew.getClass(), practitionerNew.getIdMedicalStaff());
                practitionerdicomidentifier.setPractitioner(practitionerNew);
            }
            practitionerdicomidentifier = em.merge(practitionerdicomidentifier);
            if (practitionerOld != null && !practitionerOld.equals(practitionerNew)) {
                practitionerOld.getPractitionerdicomidentifierCollection().remove(practitionerdicomidentifier);
                practitionerOld = em.merge(practitionerOld);
            }
            if (practitionerNew != null && !practitionerNew.equals(practitionerOld)) {
                practitionerNew.getPractitionerdicomidentifierCollection().add(practitionerdicomidentifier);
                practitionerNew = em.merge(practitionerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = practitionerdicomidentifier.getIdPractitionerDicomIdentifier();
                if (findPractitionerdicomidentifier(id) == null) {
                    throw new NonexistentEntityException("The practitionerdicomidentifier with id " + id + " no longer exists.");
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
            Practitionerdicomidentifier practitionerdicomidentifier;
            try {
                practitionerdicomidentifier = em.getReference(Practitionerdicomidentifier.class, id);
                practitionerdicomidentifier.getIdPractitionerDicomIdentifier();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The practitionerdicomidentifier with id " + id + " no longer exists.", enfe);
            }
            Medicalstaff practitioner = practitionerdicomidentifier.getPractitioner();
            if (practitioner != null) {
                practitioner.getPractitionerdicomidentifierCollection().remove(practitionerdicomidentifier);
                practitioner = em.merge(practitioner);
            }
            em.remove(practitionerdicomidentifier);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Practitionerdicomidentifier> findPractitionerdicomidentifierEntities() {
        return findPractitionerdicomidentifierEntities(true, -1, -1);
    }

    public List<Practitionerdicomidentifier> findPractitionerdicomidentifierEntities(int maxResults, int firstResult) {
        return findPractitionerdicomidentifierEntities(false, maxResults, firstResult);
    }

    private List<Practitionerdicomidentifier> findPractitionerdicomidentifierEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Practitionerdicomidentifier.class));
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

    public Practitionerdicomidentifier findPractitionerdicomidentifier(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Practitionerdicomidentifier.class, id);
        } finally {
            em.close();
        }
    }

    public int getPractitionerdicomidentifierCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Practitionerdicomidentifier> rt = cq.from(Practitionerdicomidentifier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
