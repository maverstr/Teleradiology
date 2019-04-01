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
import model.Imagingstudy;
import model.Instance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Report;
import model.Series;

/**
 *
 * @author INFO-H-400
 */
public class SeriesJpaController implements Serializable {

    public SeriesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Series series) {
        if (series.getInstanceCollection() == null) {
            series.setInstanceCollection(new ArrayList<Instance>());
        }
        if (series.getReportCollection() == null) {
            series.setReportCollection(new ArrayList<Report>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicalstaff performer = series.getPerformer();
            if (performer != null) {
                performer = em.getReference(performer.getClass(), performer.getIdMedicalStaff());
                series.setPerformer(performer);
            }
            Imagingstudy study = series.getStudy();
            if (study != null) {
                study = em.getReference(study.getClass(), study.getIdImagingStudy());
                series.setStudy(study);
            }
            Collection<Instance> attachedInstanceCollection = new ArrayList<Instance>();
            for (Instance instanceCollectionInstanceToAttach : series.getInstanceCollection()) {
                instanceCollectionInstanceToAttach = em.getReference(instanceCollectionInstanceToAttach.getClass(), instanceCollectionInstanceToAttach.getIdInstance());
                attachedInstanceCollection.add(instanceCollectionInstanceToAttach);
            }
            series.setInstanceCollection(attachedInstanceCollection);
            Collection<Report> attachedReportCollection = new ArrayList<Report>();
            for (Report reportCollectionReportToAttach : series.getReportCollection()) {
                reportCollectionReportToAttach = em.getReference(reportCollectionReportToAttach.getClass(), reportCollectionReportToAttach.getIdreport());
                attachedReportCollection.add(reportCollectionReportToAttach);
            }
            series.setReportCollection(attachedReportCollection);
            em.persist(series);
            if (performer != null) {
                performer.getSeriesCollection().add(series);
                performer = em.merge(performer);
            }
            if (study != null) {
                study.getSeriesCollection().add(series);
                study = em.merge(study);
            }
            for (Instance instanceCollectionInstance : series.getInstanceCollection()) {
                Series oldSeriesOfInstanceCollectionInstance = instanceCollectionInstance.getSeries();
                instanceCollectionInstance.setSeries(series);
                instanceCollectionInstance = em.merge(instanceCollectionInstance);
                if (oldSeriesOfInstanceCollectionInstance != null) {
                    oldSeriesOfInstanceCollectionInstance.getInstanceCollection().remove(instanceCollectionInstance);
                    oldSeriesOfInstanceCollectionInstance = em.merge(oldSeriesOfInstanceCollectionInstance);
                }
            }
            for (Report reportCollectionReport : series.getReportCollection()) {
                Series oldIdseriesOfReportCollectionReport = reportCollectionReport.getIdseries();
                reportCollectionReport.setIdseries(series);
                reportCollectionReport = em.merge(reportCollectionReport);
                if (oldIdseriesOfReportCollectionReport != null) {
                    oldIdseriesOfReportCollectionReport.getReportCollection().remove(reportCollectionReport);
                    oldIdseriesOfReportCollectionReport = em.merge(oldIdseriesOfReportCollectionReport);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Series series) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Series persistentSeries = em.find(Series.class, series.getIdSeries());
            Medicalstaff performerOld = persistentSeries.getPerformer();
            Medicalstaff performerNew = series.getPerformer();
            Imagingstudy studyOld = persistentSeries.getStudy();
            Imagingstudy studyNew = series.getStudy();
            Collection<Instance> instanceCollectionOld = persistentSeries.getInstanceCollection();
            Collection<Instance> instanceCollectionNew = series.getInstanceCollection();
            Collection<Report> reportCollectionOld = persistentSeries.getReportCollection();
            Collection<Report> reportCollectionNew = series.getReportCollection();
            List<String> illegalOrphanMessages = null;
            for (Instance instanceCollectionOldInstance : instanceCollectionOld) {
                if (!instanceCollectionNew.contains(instanceCollectionOldInstance)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Instance " + instanceCollectionOldInstance + " since its series field is not nullable.");
                }
            }
            for (Report reportCollectionOldReport : reportCollectionOld) {
                if (!reportCollectionNew.contains(reportCollectionOldReport)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Report " + reportCollectionOldReport + " since its idseries field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (performerNew != null) {
                performerNew = em.getReference(performerNew.getClass(), performerNew.getIdMedicalStaff());
                series.setPerformer(performerNew);
            }
            if (studyNew != null) {
                studyNew = em.getReference(studyNew.getClass(), studyNew.getIdImagingStudy());
                series.setStudy(studyNew);
            }
            Collection<Instance> attachedInstanceCollectionNew = new ArrayList<Instance>();
            for (Instance instanceCollectionNewInstanceToAttach : instanceCollectionNew) {
                instanceCollectionNewInstanceToAttach = em.getReference(instanceCollectionNewInstanceToAttach.getClass(), instanceCollectionNewInstanceToAttach.getIdInstance());
                attachedInstanceCollectionNew.add(instanceCollectionNewInstanceToAttach);
            }
            instanceCollectionNew = attachedInstanceCollectionNew;
            series.setInstanceCollection(instanceCollectionNew);
            Collection<Report> attachedReportCollectionNew = new ArrayList<Report>();
            for (Report reportCollectionNewReportToAttach : reportCollectionNew) {
                reportCollectionNewReportToAttach = em.getReference(reportCollectionNewReportToAttach.getClass(), reportCollectionNewReportToAttach.getIdreport());
                attachedReportCollectionNew.add(reportCollectionNewReportToAttach);
            }
            reportCollectionNew = attachedReportCollectionNew;
            series.setReportCollection(reportCollectionNew);
            series = em.merge(series);
            if (performerOld != null && !performerOld.equals(performerNew)) {
                performerOld.getSeriesCollection().remove(series);
                performerOld = em.merge(performerOld);
            }
            if (performerNew != null && !performerNew.equals(performerOld)) {
                performerNew.getSeriesCollection().add(series);
                performerNew = em.merge(performerNew);
            }
            if (studyOld != null && !studyOld.equals(studyNew)) {
                studyOld.getSeriesCollection().remove(series);
                studyOld = em.merge(studyOld);
            }
            if (studyNew != null && !studyNew.equals(studyOld)) {
                studyNew.getSeriesCollection().add(series);
                studyNew = em.merge(studyNew);
            }
            for (Instance instanceCollectionNewInstance : instanceCollectionNew) {
                if (!instanceCollectionOld.contains(instanceCollectionNewInstance)) {
                    Series oldSeriesOfInstanceCollectionNewInstance = instanceCollectionNewInstance.getSeries();
                    instanceCollectionNewInstance.setSeries(series);
                    instanceCollectionNewInstance = em.merge(instanceCollectionNewInstance);
                    if (oldSeriesOfInstanceCollectionNewInstance != null && !oldSeriesOfInstanceCollectionNewInstance.equals(series)) {
                        oldSeriesOfInstanceCollectionNewInstance.getInstanceCollection().remove(instanceCollectionNewInstance);
                        oldSeriesOfInstanceCollectionNewInstance = em.merge(oldSeriesOfInstanceCollectionNewInstance);
                    }
                }
            }
            for (Report reportCollectionNewReport : reportCollectionNew) {
                if (!reportCollectionOld.contains(reportCollectionNewReport)) {
                    Series oldIdseriesOfReportCollectionNewReport = reportCollectionNewReport.getIdseries();
                    reportCollectionNewReport.setIdseries(series);
                    reportCollectionNewReport = em.merge(reportCollectionNewReport);
                    if (oldIdseriesOfReportCollectionNewReport != null && !oldIdseriesOfReportCollectionNewReport.equals(series)) {
                        oldIdseriesOfReportCollectionNewReport.getReportCollection().remove(reportCollectionNewReport);
                        oldIdseriesOfReportCollectionNewReport = em.merge(oldIdseriesOfReportCollectionNewReport);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = series.getIdSeries();
                if (findSeries(id) == null) {
                    throw new NonexistentEntityException("The series with id " + id + " no longer exists.");
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
            Series series;
            try {
                series = em.getReference(Series.class, id);
                series.getIdSeries();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The series with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Instance> instanceCollectionOrphanCheck = series.getInstanceCollection();
            for (Instance instanceCollectionOrphanCheckInstance : instanceCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Series (" + series + ") cannot be destroyed since the Instance " + instanceCollectionOrphanCheckInstance + " in its instanceCollection field has a non-nullable series field.");
            }
            Collection<Report> reportCollectionOrphanCheck = series.getReportCollection();
            for (Report reportCollectionOrphanCheckReport : reportCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Series (" + series + ") cannot be destroyed since the Report " + reportCollectionOrphanCheckReport + " in its reportCollection field has a non-nullable idseries field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Medicalstaff performer = series.getPerformer();
            if (performer != null) {
                performer.getSeriesCollection().remove(series);
                performer = em.merge(performer);
            }
            Imagingstudy study = series.getStudy();
            if (study != null) {
                study.getSeriesCollection().remove(series);
                study = em.merge(study);
            }
            em.remove(series);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Series> findSeriesEntities() {
        return findSeriesEntities(true, -1, -1);
    }

    public List<Series> findSeriesEntities(int maxResults, int firstResult) {
        return findSeriesEntities(false, maxResults, firstResult);
    }

    private List<Series> findSeriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Series.class));
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

    public Series findSeries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Series.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Series> rt = cq.from(Series.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
