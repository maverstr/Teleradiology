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
import model.Person;
import model.Imagingstudy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Patient;
import model.Patientdicomidentifier;

/**
 *
 * @author INFO-H-400
 */
public class PatientJpaController implements Serializable {

    public PatientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patient patient) {
        if (patient.getImagingstudyCollection() == null) {
            patient.setImagingstudyCollection(new ArrayList<Imagingstudy>());
        }
        if (patient.getPatientdicomidentifierCollection() == null) {
            patient.setPatientdicomidentifierCollection(new ArrayList<Patientdicomidentifier>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medicalstaff generalPractitioner = patient.getGeneralPractitioner();
            if (generalPractitioner != null) {
                generalPractitioner = em.getReference(generalPractitioner.getClass(), generalPractitioner.getIdMedicalStaff());
                patient.setGeneralPractitioner(generalPractitioner);
            }
            Person person = patient.getPerson();
            if (person != null) {
                person = em.getReference(person.getClass(), person.getIdPerson());
                patient.setPerson(person);
            }
            Collection<Imagingstudy> attachedImagingstudyCollection = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollectionImagingstudyToAttach : patient.getImagingstudyCollection()) {
                imagingstudyCollectionImagingstudyToAttach = em.getReference(imagingstudyCollectionImagingstudyToAttach.getClass(), imagingstudyCollectionImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollection.add(imagingstudyCollectionImagingstudyToAttach);
            }
            patient.setImagingstudyCollection(attachedImagingstudyCollection);
            Collection<Patientdicomidentifier> attachedPatientdicomidentifierCollection = new ArrayList<Patientdicomidentifier>();
            for (Patientdicomidentifier patientdicomidentifierCollectionPatientdicomidentifierToAttach : patient.getPatientdicomidentifierCollection()) {
                patientdicomidentifierCollectionPatientdicomidentifierToAttach = em.getReference(patientdicomidentifierCollectionPatientdicomidentifierToAttach.getClass(), patientdicomidentifierCollectionPatientdicomidentifierToAttach.getIdPatientDicomIdentifier());
                attachedPatientdicomidentifierCollection.add(patientdicomidentifierCollectionPatientdicomidentifierToAttach);
            }
            patient.setPatientdicomidentifierCollection(attachedPatientdicomidentifierCollection);
            em.persist(patient);
            if (generalPractitioner != null) {
                generalPractitioner.getPatientCollection().add(patient);
                generalPractitioner = em.merge(generalPractitioner);
            }
            if (person != null) {
                person.getPatientCollection().add(patient);
                person = em.merge(person);
            }
            for (Imagingstudy imagingstudyCollectionImagingstudy : patient.getImagingstudyCollection()) {
                Patient oldPatientOfImagingstudyCollectionImagingstudy = imagingstudyCollectionImagingstudy.getPatient();
                imagingstudyCollectionImagingstudy.setPatient(patient);
                imagingstudyCollectionImagingstudy = em.merge(imagingstudyCollectionImagingstudy);
                if (oldPatientOfImagingstudyCollectionImagingstudy != null) {
                    oldPatientOfImagingstudyCollectionImagingstudy.getImagingstudyCollection().remove(imagingstudyCollectionImagingstudy);
                    oldPatientOfImagingstudyCollectionImagingstudy = em.merge(oldPatientOfImagingstudyCollectionImagingstudy);
                }
            }
            for (Patientdicomidentifier patientdicomidentifierCollectionPatientdicomidentifier : patient.getPatientdicomidentifierCollection()) {
                Patient oldPatientOfPatientdicomidentifierCollectionPatientdicomidentifier = patientdicomidentifierCollectionPatientdicomidentifier.getPatient();
                patientdicomidentifierCollectionPatientdicomidentifier.setPatient(patient);
                patientdicomidentifierCollectionPatientdicomidentifier = em.merge(patientdicomidentifierCollectionPatientdicomidentifier);
                if (oldPatientOfPatientdicomidentifierCollectionPatientdicomidentifier != null) {
                    oldPatientOfPatientdicomidentifierCollectionPatientdicomidentifier.getPatientdicomidentifierCollection().remove(patientdicomidentifierCollectionPatientdicomidentifier);
                    oldPatientOfPatientdicomidentifierCollectionPatientdicomidentifier = em.merge(oldPatientOfPatientdicomidentifierCollectionPatientdicomidentifier);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patient patient) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patient persistentPatient = em.find(Patient.class, patient.getIdPatient());
            Medicalstaff generalPractitionerOld = persistentPatient.getGeneralPractitioner();
            Medicalstaff generalPractitionerNew = patient.getGeneralPractitioner();
            Person personOld = persistentPatient.getPerson();
            Person personNew = patient.getPerson();
            Collection<Imagingstudy> imagingstudyCollectionOld = persistentPatient.getImagingstudyCollection();
            Collection<Imagingstudy> imagingstudyCollectionNew = patient.getImagingstudyCollection();
            Collection<Patientdicomidentifier> patientdicomidentifierCollectionOld = persistentPatient.getPatientdicomidentifierCollection();
            Collection<Patientdicomidentifier> patientdicomidentifierCollectionNew = patient.getPatientdicomidentifierCollection();
            List<String> illegalOrphanMessages = null;
            for (Imagingstudy imagingstudyCollectionOldImagingstudy : imagingstudyCollectionOld) {
                if (!imagingstudyCollectionNew.contains(imagingstudyCollectionOldImagingstudy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Imagingstudy " + imagingstudyCollectionOldImagingstudy + " since its patient field is not nullable.");
                }
            }
            for (Patientdicomidentifier patientdicomidentifierCollectionOldPatientdicomidentifier : patientdicomidentifierCollectionOld) {
                if (!patientdicomidentifierCollectionNew.contains(patientdicomidentifierCollectionOldPatientdicomidentifier)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patientdicomidentifier " + patientdicomidentifierCollectionOldPatientdicomidentifier + " since its patient field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (generalPractitionerNew != null) {
                generalPractitionerNew = em.getReference(generalPractitionerNew.getClass(), generalPractitionerNew.getIdMedicalStaff());
                patient.setGeneralPractitioner(generalPractitionerNew);
            }
            if (personNew != null) {
                personNew = em.getReference(personNew.getClass(), personNew.getIdPerson());
                patient.setPerson(personNew);
            }
            Collection<Imagingstudy> attachedImagingstudyCollectionNew = new ArrayList<Imagingstudy>();
            for (Imagingstudy imagingstudyCollectionNewImagingstudyToAttach : imagingstudyCollectionNew) {
                imagingstudyCollectionNewImagingstudyToAttach = em.getReference(imagingstudyCollectionNewImagingstudyToAttach.getClass(), imagingstudyCollectionNewImagingstudyToAttach.getIdImagingStudy());
                attachedImagingstudyCollectionNew.add(imagingstudyCollectionNewImagingstudyToAttach);
            }
            imagingstudyCollectionNew = attachedImagingstudyCollectionNew;
            patient.setImagingstudyCollection(imagingstudyCollectionNew);
            Collection<Patientdicomidentifier> attachedPatientdicomidentifierCollectionNew = new ArrayList<Patientdicomidentifier>();
            for (Patientdicomidentifier patientdicomidentifierCollectionNewPatientdicomidentifierToAttach : patientdicomidentifierCollectionNew) {
                patientdicomidentifierCollectionNewPatientdicomidentifierToAttach = em.getReference(patientdicomidentifierCollectionNewPatientdicomidentifierToAttach.getClass(), patientdicomidentifierCollectionNewPatientdicomidentifierToAttach.getIdPatientDicomIdentifier());
                attachedPatientdicomidentifierCollectionNew.add(patientdicomidentifierCollectionNewPatientdicomidentifierToAttach);
            }
            patientdicomidentifierCollectionNew = attachedPatientdicomidentifierCollectionNew;
            patient.setPatientdicomidentifierCollection(patientdicomidentifierCollectionNew);
            patient = em.merge(patient);
            if (generalPractitionerOld != null && !generalPractitionerOld.equals(generalPractitionerNew)) {
                generalPractitionerOld.getPatientCollection().remove(patient);
                generalPractitionerOld = em.merge(generalPractitionerOld);
            }
            if (generalPractitionerNew != null && !generalPractitionerNew.equals(generalPractitionerOld)) {
                generalPractitionerNew.getPatientCollection().add(patient);
                generalPractitionerNew = em.merge(generalPractitionerNew);
            }
            if (personOld != null && !personOld.equals(personNew)) {
                personOld.getPatientCollection().remove(patient);
                personOld = em.merge(personOld);
            }
            if (personNew != null && !personNew.equals(personOld)) {
                personNew.getPatientCollection().add(patient);
                personNew = em.merge(personNew);
            }
            for (Imagingstudy imagingstudyCollectionNewImagingstudy : imagingstudyCollectionNew) {
                if (!imagingstudyCollectionOld.contains(imagingstudyCollectionNewImagingstudy)) {
                    Patient oldPatientOfImagingstudyCollectionNewImagingstudy = imagingstudyCollectionNewImagingstudy.getPatient();
                    imagingstudyCollectionNewImagingstudy.setPatient(patient);
                    imagingstudyCollectionNewImagingstudy = em.merge(imagingstudyCollectionNewImagingstudy);
                    if (oldPatientOfImagingstudyCollectionNewImagingstudy != null && !oldPatientOfImagingstudyCollectionNewImagingstudy.equals(patient)) {
                        oldPatientOfImagingstudyCollectionNewImagingstudy.getImagingstudyCollection().remove(imagingstudyCollectionNewImagingstudy);
                        oldPatientOfImagingstudyCollectionNewImagingstudy = em.merge(oldPatientOfImagingstudyCollectionNewImagingstudy);
                    }
                }
            }
            for (Patientdicomidentifier patientdicomidentifierCollectionNewPatientdicomidentifier : patientdicomidentifierCollectionNew) {
                if (!patientdicomidentifierCollectionOld.contains(patientdicomidentifierCollectionNewPatientdicomidentifier)) {
                    Patient oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier = patientdicomidentifierCollectionNewPatientdicomidentifier.getPatient();
                    patientdicomidentifierCollectionNewPatientdicomidentifier.setPatient(patient);
                    patientdicomidentifierCollectionNewPatientdicomidentifier = em.merge(patientdicomidentifierCollectionNewPatientdicomidentifier);
                    if (oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier != null && !oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier.equals(patient)) {
                        oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier.getPatientdicomidentifierCollection().remove(patientdicomidentifierCollectionNewPatientdicomidentifier);
                        oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier = em.merge(oldPatientOfPatientdicomidentifierCollectionNewPatientdicomidentifier);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = patient.getIdPatient();
                if (findPatient(id) == null) {
                    throw new NonexistentEntityException("The patient with id " + id + " no longer exists.");
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
            Patient patient;
            try {
                patient = em.getReference(Patient.class, id);
                patient.getIdPatient();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patient with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Imagingstudy> imagingstudyCollectionOrphanCheck = patient.getImagingstudyCollection();
            for (Imagingstudy imagingstudyCollectionOrphanCheckImagingstudy : imagingstudyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Patient (" + patient + ") cannot be destroyed since the Imagingstudy " + imagingstudyCollectionOrphanCheckImagingstudy + " in its imagingstudyCollection field has a non-nullable patient field.");
            }
            Collection<Patientdicomidentifier> patientdicomidentifierCollectionOrphanCheck = patient.getPatientdicomidentifierCollection();
            for (Patientdicomidentifier patientdicomidentifierCollectionOrphanCheckPatientdicomidentifier : patientdicomidentifierCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Patient (" + patient + ") cannot be destroyed since the Patientdicomidentifier " + patientdicomidentifierCollectionOrphanCheckPatientdicomidentifier + " in its patientdicomidentifierCollection field has a non-nullable patient field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Medicalstaff generalPractitioner = patient.getGeneralPractitioner();
            if (generalPractitioner != null) {
                generalPractitioner.getPatientCollection().remove(patient);
                generalPractitioner = em.merge(generalPractitioner);
            }
            Person person = patient.getPerson();
            if (person != null) {
                person.getPatientCollection().remove(patient);
                person = em.merge(person);
            }
            em.remove(patient);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patient> findPatientEntities() {
        return findPatientEntities(true, -1, -1);
    }

    public List<Patient> findPatientEntities(int maxResults, int firstResult) {
        return findPatientEntities(false, maxResults, firstResult);
    }

    private List<Patient> findPatientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patient.class));
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

    public Patient findPatient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patient.class, id);
        } finally {
            em.close();
        }
    }

    public Patient findPatientByName(String name) {
        EntityManager em = getEntityManager();
        try {

            //return em.find(Patient.class, name);
            
            //Patient patient = (Patient) em.createNamedQuery("Patient.findByIdPatient").setParameter("idPatient", 4).getSingleResult();
            Patient patient = (Patient) em.createNamedQuery("Patient.findByName").setParameter("nameGiven", name).getSingleResult(); 
            return patient;
        } catch(NoResultException e) {
            System.out.println("no patient found");
            return null;
        }
        finally{
            em.close();
        }
    }

    public int getPatientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patient> rt = cq.from(Patient.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
