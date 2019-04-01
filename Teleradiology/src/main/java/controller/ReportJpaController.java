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
import model.Report;
import model.Series;

/**
 *
 * @author INFO-H-400
 */
public class ReportJpaController implements Serializable {

    public ReportJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Report report) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Series idseries = report.getIdseries();
            if (idseries != null) {
                idseries = em.getReference(idseries.getClass(), idseries.getIdSeries());
                report.setIdseries(idseries);
            }
            em.persist(report);
            if (idseries != null) {
                idseries.getReportCollection().add(report);
                idseries = em.merge(idseries);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Report report) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Report persistentReport = em.find(Report.class, report.getIdreport());
            Series idseriesOld = persistentReport.getIdseries();
            Series idseriesNew = report.getIdseries();
            if (idseriesNew != null) {
                idseriesNew = em.getReference(idseriesNew.getClass(), idseriesNew.getIdSeries());
                report.setIdseries(idseriesNew);
            }
            report = em.merge(report);
            if (idseriesOld != null && !idseriesOld.equals(idseriesNew)) {
                idseriesOld.getReportCollection().remove(report);
                idseriesOld = em.merge(idseriesOld);
            }
            if (idseriesNew != null && !idseriesNew.equals(idseriesOld)) {
                idseriesNew.getReportCollection().add(report);
                idseriesNew = em.merge(idseriesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = report.getIdreport();
                if (findReport(id) == null) {
                    throw new NonexistentEntityException("The report with id " + id + " no longer exists.");
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
            Report report;
            try {
                report = em.getReference(Report.class, id);
                report.getIdreport();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The report with id " + id + " no longer exists.", enfe);
            }
            Series idseries = report.getIdseries();
            if (idseries != null) {
                idseries.getReportCollection().remove(report);
                idseries = em.merge(idseries);
            }
            em.remove(report);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Report> findReportEntities() {
        return findReportEntities(true, -1, -1);
    }

    public List<Report> findReportEntities(int maxResults, int firstResult) {
        return findReportEntities(false, maxResults, firstResult);
    }

    private List<Report> findReportEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Report.class));
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

    public Report findReport(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Report.class, id);
        } finally {
            em.close();
        }
    }

    public int getReportCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Report> rt = cq.from(Report.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
