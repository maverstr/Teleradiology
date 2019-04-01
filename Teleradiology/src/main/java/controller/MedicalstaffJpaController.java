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
import model.Person;
import model.Radiologist;
import model.Imagingstudy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Medicalstaff;
import model.Practitionerdicomidentifier;
import model.Patient;
import model.Series;

/**
 *
 * @author INFO-H-400
 */
public class MedicalstaffJpaController implements Serializable {

    public MedicalstaffJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medicalstaff medicalstaff) {
        if (medicalstaff.getImagingstudyCollection() == null) {
            medicalstaff.setImagingstudyCollection(new ArrayList<Imagingstudy>());
        }
        if (medicalstaff.getImagingstudyCollection1() == null) {
            medicalstaff.setImagingstudyCollection1(new ArrayList<Imagingstudy>());
        }
        if (medicalstaff.getPractitionerdicomidentifierCollection() == null) {
            medicalstaff.setPractitionerdicomidentifierCollection(new ArrayList<Practitionerdicomidentifier>());
        }
        if (medicalstaff.getPatientCollection() == null) {
            medicalstaff.setPatientCollection(new ArrayList<Patient>());
        }
        if (medicalstaff.getSeriesCollection() == null) {
            medicalstaff.setSeriesCollection(new ArrayList<Series>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person person = medicalstaff.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getIdPerson());
                medicalstaff.setPerson(person);
            }
            Radiologist radiologist = medicalstaff.getRadiologist();
            if (radiologist != null) {
                radiologist = em.getReference(radiologist.getClass(), radiologist.getId());
                medicalstaff.setRadiologist(radiologist);
            }
            Collection<Imagingstudy> attachedImagingstudyCollection = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollectionImagingstudyToAttach : medicalstaff.getImagingstudyCollection()) {
                imagingstudyCollectionImagingstudyToAttach = em.getReference(imagingstudyCollectionImagingstudyToAttach.getClass(), imagingstudyCollectionImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollection.add(imagingstudyCollectionImagingstudyToAttach);
            }
            medicalstaff.setImagingstudyCollection(attachedImagingstudyCollection);
            Collection<Imagingstudy> attachedImagingstudyCollection1 = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollection1ImagingstudyToAttach : medicalstaff.getImagingstudyCollection1()) {
                imagingstudyCollection1ImagingstudyToAttach = em.getReference(imagingstudyCollection1ImagingstudyToAttach.getClass(), imagingstudyCollection1ImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollection1.add(imagingstudyCollection1ImagingstudyToAttach);
            }
            medicalstaff.setImagingstudyCollection1(attachedImagingstudyCollection1);
            Collection<Practitionerdicomidentifier> attachedPractitionerdicomidentifierCollection = new ArrayList<Practitionerdicomidentifier>();
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionPractitionerdicomidentifierToAttach : medicalstaff.getPractitionerdicomidentifierCollection()) {
                practitionerdicomidentifierCollectionPractitionerdicomidentifierToAttach = em.getReference(practitionerdicomidentifierCollectionPractitionerdicomidentifierToAttach.getClass(), practitionerdicomidentifierCollectionPractitionerdicomidentifierToAttach.getIdPractitionerDicomIdentifier());
                attachedPractitionerdicomidentifierCollection.add(practitionerdicomidentifierCollectionPractitionerdicomidentifierToAttach);
            }
            medicalstaff.setPractitionerdicomidentifierCollection(attachedPractitionerdicomidentifierCollection);
            Collection<Patient> attachedPatientCollection = new ArrayList<Patient>();
            for (Patient patientCollectionPatientToAttach : medicalstaff.getPatientCollection()) {
                patientCollectionPatientToAttach = em.getReference(patientCollectionPatientToAttach.getClass(), patientCollectionPatientToAttach.getIdPatient());
                attachedPatientCollection.add(patientCollectionPatientToAttach);
            }
            medicalstaff.setPatientCollection(attachedPatientCollection);
            Collection<Series> attachedSeriesCollection = new ArrayList<Series>();
            for (Series seriesCollectionSeriesToAttach : medicalstaff.getSeriesCollection()) {
                seriesCollectionSeriesToAttach = em.getReference(seriesCollectionSeriesToAttach.getClass(), seriesCollectionSeriesToAttach.getIdSeries());
                attachedSeriesCollection.add(seriesCollectionSeriesToAttach);
            }
            medicalstaff.setSeriesCollection(attachedSeriesCollection);
            em.persist(medicalstaff);
            if (person != null) {
                person.getMedicalstaffCollection().add(medicalstaff);
                person = em.merge(person);
            }
            if (radiologist != null) {
                Medicalstaff oldMedicalstaff1OfRadiologist = radiologist.getMedicalstaff1();
                if (oldMedicalstaff1OfRadiologist != null) {
                    oldMedicalstaff1OfRadiologist.setRadiologist(null);
                    oldMedicalstaff1OfRadiologist = em.merge(oldMedicalstaff1OfRadiologist);
                }
                radiologist.setMedicalstaff1(medicalstaff);
                radiologist = em.merge(radiologist);
            }
            for (Imagingstudy imagingstudyCollectionImagingstudy : medicalstaff.getImagingstudyCollection()) {
                Medicalstaff oldInterpreterOfImagingstudyCollectionImagingstudy = imagingstudyCollectionImagingstudy.getInterpreter();
                imagingstudyCollectionImagingstudy.setInterpreter(medicalstaff);
                imagingstudyCollectionImagingstudy = em.merge(imagingstudyCollectionImagingstudy);
                if (oldInterpreterOfImagingstudyCollectionImagingstudy != null) {
                    oldInterpreterOfImagingstudyCollectionImagingstudy.getImagingstudyCollection().remove(imagingstudyCollectionImagingstudy);
                    oldInterpreterOfImagingstudyCollectionImagingstudy = em.merge(oldInterpreterOfImagingstudyCollectionImagingstudy);
                }
            }
            for (Imagingstudy imagingstudyCollection1Imagingstudy : medicalstaff.getImagingstudyCollection1()) {
                Medicalstaff oldReferrerOfImagingstudyCollection1Imagingstudy = imagingstudyCollection1Imagingstudy.getReferrer();
                imagingstudyCollection1Imagingstudy.setReferrer(medicalstaff);
                imagingstudyCollection1Imagingstudy = em.merge(imagingstudyCollection1Imagingstudy);
                if (oldReferrerOfImagingstudyCollection1Imagingstudy != null) {
                    oldReferrerOfImagingstudyCollection1Imagingstudy.getImagingstudyCollection1().remove(imagingstudyCollection1Imagingstudy);
                    oldReferrerOfImagingstudyCollection1Imagingstudy = em.merge(oldReferrerOfImagingstudyCollection1Imagingstudy);
                }
            }
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionPractitionerdicomidentifier : medicalstaff.getPractitionerdicomidentifierCollection()) {
                Medicalstaff oldPractitionerOfPractitionerdicomidentifierCollectionPractitionerdicomidentifier = practitionerdicomidentifierCollectionPractitionerdicomidentifier.getPractitioner();
                practitionerdicomidentifierCollectionPractitionerdicomidentifier.setPractitioner(medicalstaff);
                practitionerdicomidentifierCollectionPractitionerdicomidentifier = em.merge(practitionerdicomidentifierCollectionPractitionerdicomidentifier);
                if (oldPractitionerOfPractitionerdicomidentifierCollectionPractitionerdicomidentifier != null) {
                    oldPractitionerOfPractitionerdicomidentifierCollectionPractitionerdicomidentifier.getPractitionerdicomidentifierCollection().remove(practitionerdicomidentifierCollectionPractitionerdicomidentifier);
                    oldPractitionerOfPractitionerdicomidentifierCollectionPractitionerdicomidentifier = em.merge(oldPractitionerOfPractitionerdicomidentifierCollectionPractitionerdicomidentifier);
                }
            }
            for (Patient patientCollectionPatient : medicalstaff.getPatientCollection()) {
                Medicalstaff oldGeneralPractitionerOfPatientCollectionPatient = patientCollectionPatient.getGeneralPractitioner();
                patientCollectionPatient.setGeneralPractitioner(medicalstaff);
                patientCollectionPatient = em.merge(patientCollectionPatient);
                if (oldGeneralPractitionerOfPatientCollectionPatient != null) {
                    oldGeneralPractitionerOfPatientCollectionPatient.getPatientCollection().remove(patientCollectionPatient);
                    oldGeneralPractitionerOfPatientCollectionPatient = em.merge(oldGeneralPractitionerOfPatientCollectionPatient);
                }
            }
            for (Series seriesCollectionSeries : medicalstaff.getSeriesCollection()) {
                Medicalstaff oldPerformerOfSeriesCollectionSeries = seriesCollectionSeries.getPerformer();
                seriesCollectionSeries.setPerformer(medicalstaff);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
                if (oldPerformerOfSeriesCollectionSeries != null) {
                    oldPerformerOfSeriesCollectionSeries.getSeriesCollection().remove(seriesCollectionSeries);
                    oldPerformerOfSeriesCollectionSeries = em.merge(oldPerformerOfSeriesCollectionSeries);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medicalstaff medicalstaff) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicalstaff persistentMedicalstaff = em.find(Medicalstaff.class, medicalstaff.getIdMedicalStaff());
            Person personOld = persistentMedicalstaff.getPerson();
            Person personNew = medicalstaff.getPerson();
            Radiologist radiologistOld = persistentMedicalstaff.getRadiologist();
            Radiologist radiologistNew = medicalstaff.getRadiologist();
            Collection<Imagingstudy> imagingstudyCollectionOld = persistentMedicalstaff.getImagingstudyCollection();
            Collection<Imagingstudy> imagingstudyCollectionNew = medicalstaff.getImagingstudyCollection();
            Collection<Imagingstudy> imagingstudyCollection1Old = persistentMedicalstaff.getImagingstudyCollection1();
            Collection<Imagingstudy> imagingstudyCollection1New = medicalstaff.getImagingstudyCollection1();
            Collection<Practitionerdicomidentifier> practitionerdicomidentifierCollectionOld = persistentMedicalstaff.getPractitionerdicomidentifierCollection();
            Collection<Practitionerdicomidentifier> practitionerdicomidentifierCollectionNew = medicalstaff.getPractitionerdicomidentifierCollection();
            Collection<Patient> patientCollectionOld = persistentMedicalstaff.getPatientCollection();
            Collection<Patient> patientCollectionNew = medicalstaff.getPatientCollection();
            Collection<Series> seriesCollectionOld = persistentMedicalstaff.getSeriesCollection();
            Collection<Series> seriesCollectionNew = medicalstaff.getSeriesCollection();
            List<String> illegalOrphanMessages = null;
            if (radiologistOld != null && !radiologistOld.equals(radiologistNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Radiologist " + radiologistOld + " since its medicalstaff1 field is not nullable.");
            }
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionOldPractitionerdicomidentifier : practitionerdicomidentifierCollectionOld) {
                if (!practitionerdicomidentifierCollectionNew.contains(practitionerdicomidentifierCollectionOldPractitionerdicomidentifier)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Practitionerdicomidentifier " + practitionerdicomidentifierCollectionOldPractitionerdicomidentifier + " since its practitioner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getIdPerson());
                medicalstaff.setPerson(personNew);
            }
            if (radiologistNew != null) {
                radiologistNew = em.getReference(radiologistNew.getClass(), radiologistNew.getId());
                medicalstaff.setRadiologist(radiologistNew);
            }
            Collection<Imagingstudy> attachedImagingstudyCollectionNew = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollectionNewImagingstudyToAttach : imagingstudyCollectionNew) {
                imagingstudyCollectionNewImagingstudyToAttach = em.getReference(imagingstudyCollectionNewImagingstudyToAttach.getClass(), imagingstudyCollectionNewImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollectionNew.add(imagingstudyCollectionNewImagingstudyToAttach);
            }
            imagingstudyCollectionNew = attachedImagingstudyCollectionNew;
            medicalstaff.setImagingstudyCollection(imagingstudyCollectionNew);
            Collection<Imagingstudy> attachedImagingstudyCollection1New = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollection1NewImagingstudyToAttach : imagingstudyCollection1New) {
                imagingstudyCollection1NewImagingstudyToAttach = em.getReference(imagingstudyCollection1NewImagingstudyToAttach.getClass(), imagingstudyCollection1NewImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollection1New.add(imagingstudyCollection1NewImagingstudyToAttach);
            }
            imagingstudyCollection1New = attachedImagingstudyCollection1New;
            medicalstaff.setImagingstudyCollection1(imagingstudyCollection1New);
            Collection<Practitionerdicomidentifier> attachedPractitionerdicomidentifierCollectionNew = new ArrayList<Practitionerdicomidentifier>();
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionNewPractitionerdicomidentifierToAttach : practitionerdicomidentifierCollectionNew) {
                practitionerdicomidentifierCollectionNewPractitionerdicomidentifierToAttach = em.getReference(practitionerdicomidentifierCollectionNewPractitionerdicomidentifierToAttach.getClass(), practitionerdicomidentifierCollectionNewPractitionerdicomidentifierToAttach.getIdPractitionerDicomIdentifier());
                attachedPractitionerdicomidentifierCollectionNew.add(practitionerdicomidentifierCollectionNewPractitionerdicomidentifierToAttach);
            }
            practitionerdicomidentifierCollectionNew = attachedPractitionerdicomidentifierCollectionNew;
            medicalstaff.setPractitionerdicomidentifierCollection(practitionerdicomidentifierCollectionNew);
            Collection<Patient> attachedPatientCollectionNew = new ArrayList<Patient>();
            for (Patient patientCollectionNewPatientToAttach : patientCollectionNew) {
                patientCollectionNewPatientToAttach = em.getReference(patientCollectionNewPatientToAttach.getClass(), patientCollectionNewPatientToAttach.getIdPatient());
                attachedPatientCollectionNew.add(patientCollectionNewPatientToAttach);
            }
            patientCollectionNew = attachedPatientCollectionNew;
            medicalstaff.setPatientCollection(patientCollectionNew);
            Collection<Series> attachedSeriesCollectionNew = new ArrayList<Series>();
            for (Series seriesCollectionNewSeriesToAttach : seriesCollectionNew) {
                seriesCollectionNewSeriesToAttach = em.getReference(seriesCollectionNewSeriesToAttach.getClass(), seriesCollectionNewSeriesToAttach.getIdSeries());
                attachedSeriesCollectionNew.add(seriesCollectionNewSeriesToAttach);
            }
            seriesCollectionNew = attachedSeriesCollectionNew;
            medicalstaff.setSeriesCollection(seriesCollectionNew);
            medicalstaff = em.merge(medicalstaff);
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.getMedicalstaffCollection().remove(medicalstaff);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.getMedicalstaffCollection().add(medicalstaff);
                personNew = em.merge(personNew);
            }
            if (radiologistNew != null && !radiologistNew.equals(radiologistOld)) {
                Medicalstaff oldMedicalstaff1OfRadiologist = radiologistNew.getMedicalstaff1();
                if (oldMedicalstaff1OfRadiologist != null) {
                    oldMedicalstaff1OfRadiologist.setRadiologist(null);
                    oldMedicalstaff1OfRadiologist = em.merge(oldMedicalstaff1OfRadiologist);
                }
                radiologistNew.setMedicalstaff1(medicalstaff);
                radiologistNew = em.merge(radiologistNew);
            }
            for (Imagingstudy imagingstudyCollectionOldImagingstudy : imagingstudyCollectionOld) {
                if (!imagingstudyCollectionNew.contains(imagingstudyCollectionOldImagingstudy)) {
                    imagingstudyCollectionOldImagingstudy.setInterpreter(null);
                    imagingstudyCollectionOldImagingstudy = em.merge(imagingstudyCollectionOldImagingstudy);
                }
            }
            for (Imagingstudy imagingstudyCollectionNewImagingstudy : imagingstudyCollectionNew) {
                if (!imagingstudyCollectionOld.contains(imagingstudyCollectionNewImagingstudy)) {
                    Medicalstaff oldInterpreterOfImagingstudyCollectionNewImagingstudy = imagingstudyCollectionNewImagingstudy.getInterpreter();
                    imagingstudyCollectionNewImagingstudy.setInterpreter(medicalstaff);
                    imagingstudyCollectionNewImagingstudy = em.merge(imagingstudyCollectionNewImagingstudy);
                    if (oldInterpreterOfImagingstudyCollectionNewImagingstudy != null && !oldInterpreterOfImagingstudyCollectionNewImagingstudy.equals(medicalstaff)) {
                        oldInterpreterOfImagingstudyCollectionNewImagingstudy.getImagingstudyCollection().remove(imagingstudyCollectionNewImagingstudy);
                        oldInterpreterOfImagingstudyCollectionNewImagingstudy = em.merge(oldInterpreterOfImagingstudyCollectionNewImagingstudy);
                    }
                }
            }
            for (Imagingstudy imagingstudyCollection1OldImagingstudy : imagingstudyCollection1Old) {
                if (!imagingstudyCollection1New.contains(imagingstudyCollection1OldImagingstudy)) {
                    imagingstudyCollection1OldImagingstudy.setReferrer(null);
                    imagingstudyCollection1OldImagingstudy = em.merge(imagingstudyCollection1OldImagingstudy);
                }
            }
            for (Imagingstudy imagingstudyCollection1NewImagingstudy : imagingstudyCollection1New) {
                if (!imagingstudyCollection1Old.contains(imagingstudyCollection1NewImagingstudy)) {
                    Medicalstaff oldReferrerOfImagingstudyCollection1NewImagingstudy = imagingstudyCollection1NewImagingstudy.getReferrer();
                    imagingstudyCollection1NewImagingstudy.setReferrer(medicalstaff);
                    imagingstudyCollection1NewImagingstudy = em.merge(imagingstudyCollection1NewImagingstudy);
                    if (oldReferrerOfImagingstudyCollection1NewImagingstudy != null && !oldReferrerOfImagingstudyCollection1NewImagingstudy.equals(medicalstaff)) {
                        oldReferrerOfImagingstudyCollection1NewImagingstudy.getImagingstudyCollection1().remove(imagingstudyCollection1NewImagingstudy);
                        oldReferrerOfImagingstudyCollection1NewImagingstudy = em.merge(oldReferrerOfImagingstudyCollection1NewImagingstudy);
                    }
                }
            }
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionNewPractitionerdicomidentifier : practitionerdicomidentifierCollectionNew) {
                if (!practitionerdicomidentifierCollectionOld.contains(practitionerdicomidentifierCollectionNewPractitionerdicomidentifier)) {
                    Medicalstaff oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier = practitionerdicomidentifierCollectionNewPractitionerdicomidentifier.getPractitioner();
                    practitionerdicomidentifierCollectionNewPractitionerdicomidentifier.setPractitioner(medicalstaff);
                    practitionerdicomidentifierCollectionNewPractitionerdicomidentifier = em.merge(practitionerdicomidentifierCollectionNewPractitionerdicomidentifier);
                    if (oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier != null && !oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier.equals(medicalstaff)) {
                        oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier.getPractitionerdicomidentifierCollection().remove(practitionerdicomidentifierCollectionNewPractitionerdicomidentifier);
                        oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier = em.merge(oldPractitionerOfPractitionerdicomidentifierCollectionNewPractitionerdicomidentifier);
                    }
                }
            }
            for (Patient patientCollectionOldPatient : patientCollectionOld) {
                if (!patientCollectionNew.contains(patientCollectionOldPatient)) {
                    patientCollectionOldPatient.setGeneralPractitioner(null);
                    patientCollectionOldPatient = em.merge(patientCollectionOldPatient);
                }
            }
            for (Patient patientCollectionNewPatient : patientCollectionNew) {
                if (!patientCollectionOld.contains(patientCollectionNewPatient)) {
                    Medicalstaff oldGeneralPractitionerOfPatientCollectionNewPatient = patientCollectionNewPatient.getGeneralPractitioner();
                    patientCollectionNewPatient.setGeneralPractitioner(medicalstaff);
                    patientCollectionNewPatient = em.merge(patientCollectionNewPatient);
                    if (oldGeneralPractitionerOfPatientCollectionNewPatient != null && !oldGeneralPractitionerOfPatientCollectionNewPatient.equals(medicalstaff)) {
                        oldGeneralPractitionerOfPatientCollectionNewPatient.getPatientCollection().remove(patientCollectionNewPatient);
                        oldGeneralPractitionerOfPatientCollectionNewPatient = em.merge(oldGeneralPractitionerOfPatientCollectionNewPatient);
                    }
                }
            }
            for (Series seriesCollectionOldSeries : seriesCollectionOld) {
                if (!seriesCollectionNew.contains(seriesCollectionOldSeries)) {
                    seriesCollectionOldSeries.setPerformer(null);
                    seriesCollectionOldSeries = em.merge(seriesCollectionOldSeries);
                }
            }
            for (Series seriesCollectionNewSeries : seriesCollectionNew) {
                if (!seriesCollectionOld.contains(seriesCollectionNewSeries)) {
                    Medicalstaff oldPerformerOfSeriesCollectionNewSeries = seriesCollectionNewSeries.getPerformer();
                    seriesCollectionNewSeries.setPerformer(medicalstaff);
                    seriesCollectionNewSeries = em.merge(seriesCollectionNewSeries);
                    if (oldPerformerOfSeriesCollectionNewSeries != null && !oldPerformerOfSeriesCollectionNewSeries.equals(medicalstaff)) {
                        oldPerformerOfSeriesCollectionNewSeries.getSeriesCollection().remove(seriesCollectionNewSeries);
                        oldPerformerOfSeriesCollectionNewSeries = em.merge(oldPerformerOfSeriesCollectionNewSeries);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medicalstaff.getIdMedicalStaff();
                if (findMedicalstaff(id) == null) {
                    throw new NonexistentEntityException("The medicalstaff with id " + id + " no longer exists.");
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
            Medicalstaff medicalstaff;
            try {
                medicalstaff = em.getReference(Medicalstaff.class, id);
                medicalstaff.getIdMedicalStaff();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medicalstaff with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Radiologist radiologistOrphanCheck = medicalstaff.getRadiologist();
            if (radiologistOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicalstaff (" + medicalstaff + ") cannot be destroyed since the Radiologist " + radiologistOrphanCheck + " in its radiologist field has a non-nullable medicalstaff1 field.");
            }
            Collection<Practitionerdicomidentifier> practitionerdicomidentifierCollectionOrphanCheck = medicalstaff.getPractitionerdicomidentifierCollection();
            for (Practitionerdicomidentifier practitionerdicomidentifierCollectionOrphanCheckPractitionerdicomidentifier : practitionerdicomidentifierCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medicalstaff (" + medicalstaff + ") cannot be destroyed since the Practitionerdicomidentifier " + practitionerdicomidentifierCollectionOrphanCheckPractitionerdicomidentifier + " in its practitionerdicomidentifierCollection field has a non-nullable practitioner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person person = medicalstaff.getPerson();
            if (person != null) {
                person.getMedicalstaffCollection().remove(medicalstaff);
                person = em.merge(person);
            }
            Collection<Imagingstudy> imagingstudyCollection = medicalstaff.getImagingstudyCollection();
            for (Imagingstudy imagingstudyCollectionImagingstudy : imagingstudyCollection) {
                imagingstudyCollectionImagingstudy.setInterpreter(null);
                imagingstudyCollectionImagingstudy = em.merge(imagingstudyCollectionImagingstudy);
            }
            Collection<Imagingstudy> imagingstudyCollection1 = medicalstaff.getImagingstudyCollection1();
            for (Imagingstudy imagingstudyCollection1Imagingstudy : imagingstudyCollection1) {
                imagingstudyCollection1Imagingstudy.setReferrer(null);
                imagingstudyCollection1Imagingstudy = em.merge(imagingstudyCollection1Imagingstudy);
            }
            Collection<Patient> patientCollection = medicalstaff.getPatientCollection();
            for (Patient patientCollectionPatient : patientCollection) {
                patientCollectionPatient.setGeneralPractitioner(null);
                patientCollectionPatient = em.merge(patientCollectionPatient);
            }
            Collection<Series> seriesCollection = medicalstaff.getSeriesCollection();
            for (Series seriesCollectionSeries : seriesCollection) {
                seriesCollectionSeries.setPerformer(null);
                seriesCollectionSeries = em.merge(seriesCollectionSeries);
            }
            em.remove(medicalstaff);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medicalstaff> findMedicalstaffEntities() {
        return findMedicalstaffEntities(true, -1, -1);
    }

    public List<Medicalstaff> findMedicalstaffEntities(int maxResults, int firstResult) {
        return findMedicalstaffEntities(false, maxResults, firstResult);
    }

    private List<Medicalstaff> findMedicalstaffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medicalstaff.class));
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

    public Medicalstaff findMedicalstaff(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medicalstaff.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicalstaffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medicalstaff> rt = cq.from(Medicalstaff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
