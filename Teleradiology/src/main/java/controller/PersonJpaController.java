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
import model.Patient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Medicalstaff;
import model.Person;
import model.User;

/**
 *
 * @author INFO-H-400
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) {
        if (person.getPatientCollection() == null) {
            person.setPatientCollection(new ArrayList<Patient>());
        }
        if (person.getMedicalstaffCollection() == null) {
            person.setMedicalstaffCollection(new ArrayList<Medicalstaff>());
        }
        if (person.getUserCollection() == null) {
            person.setUserCollection(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Patient> attachedPatientCollection = new ArrayList<Patient>();
            for (Patient patientCollectionPatientToAttach : person.getPatientCollection()) {
                patientCollectionPatientToAttach = em.getReference(patientCollectionPatientToAttach.getClass(), patientCollectionPatientToAttach.getIdPatient());
                attachedPatientCollection.add(patientCollectionPatientToAttach);
            }
            person.setPatientCollection(attachedPatientCollection);
            Collection<Medicalstaff> attachedMedicalstaffCollection = new ArrayList<Medicalstaff>();
            for (Medicalstaff medicalstaffCollectionMedicalstaffToAttach : person.getMedicalstaffCollection()) {
                medicalstaffCollectionMedicalstaffToAttach = em.getReference(medicalstaffCollectionMedicalstaffToAttach.getClass(), medicalstaffCollectionMedicalstaffToAttach.getIdMedicalStaff());
                attachedMedicalstaffCollection.add(medicalstaffCollectionMedicalstaffToAttach);
            }
            person.setMedicalstaffCollection(attachedMedicalstaffCollection);
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : person.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getIdUser());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            person.setUserCollection(attachedUserCollection);
            em.persist(person);
            for (Patient patientCollectionPatient : person.getPatientCollection()) {
                Person oldPersonOfPatientCollectionPatient = patientCollectionPatient.getPerson();
                patientCollectionPatient.setPerson(person);
                patientCollectionPatient = em.merge(patientCollectionPatient);
                if (oldPersonOfPatientCollectionPatient != null) {
                    oldPersonOfPatientCollectionPatient.getPatientCollection().remove(patientCollectionPatient);
                    oldPersonOfPatientCollectionPatient = em.merge(oldPersonOfPatientCollectionPatient);
                }
            }
            for (Medicalstaff medicalstaffCollectionMedicalstaff : person.getMedicalstaffCollection()) {
                Person oldPersonOfMedicalstaffCollectionMedicalstaff = medicalstaffCollectionMedicalstaff.getPerson();
                medicalstaffCollectionMedicalstaff.setPerson(person);
                medicalstaffCollectionMedicalstaff = em.merge(medicalstaffCollectionMedicalstaff);
                if (oldPersonOfMedicalstaffCollectionMedicalstaff != null) {
                    oldPersonOfMedicalstaffCollectionMedicalstaff.getMedicalstaffCollection().remove(medicalstaffCollectionMedicalstaff);
                    oldPersonOfMedicalstaffCollectionMedicalstaff = em.merge(oldPersonOfMedicalstaffCollectionMedicalstaff);
                }
            }
            for (User userCollectionUser : person.getUserCollection()) {
                Person oldPersonOfUserCollectionUser = userCollectionUser.getPerson();
                userCollectionUser.setPerson(person);
                userCollectionUser = em.merge(userCollectionUser);
                if (oldPersonOfUserCollectionUser != null) {
                    oldPersonOfUserCollectionUser.getUserCollection().remove(userCollectionUser);
                    oldPersonOfUserCollectionUser = em.merge(oldPersonOfUserCollectionUser);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person persistentPerson = em.find(Person.class, person.getIdPerson());
            Collection<Patient> patientCollectionOld = persistentPerson.getPatientCollection();
            Collection<Patient> patientCollectionNew = person.getPatientCollection();
            Collection<Medicalstaff> medicalstaffCollectionOld = persistentPerson.getMedicalstaffCollection();
            Collection<Medicalstaff> medicalstaffCollectionNew = person.getMedicalstaffCollection();
            Collection<User> userCollectionOld = persistentPerson.getUserCollection();
            Collection<User> userCollectionNew = person.getUserCollection();
            List<String> illegalOrphanMessages = null;
            for (Patient patientCollectionOldPatient : patientCollectionOld) {
                if (!patientCollectionNew.contains(patientCollectionOldPatient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patient " + patientCollectionOldPatient + " since its person field is not nullable.");
                }
            }
            for (Medicalstaff medicalstaffCollectionOldMedicalstaff : medicalstaffCollectionOld) {
                if (!medicalstaffCollectionNew.contains(medicalstaffCollectionOldMedicalstaff)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Medicalstaff " + medicalstaffCollectionOldMedicalstaff + " since its person field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Patient> attachedPatientCollectionNew = new ArrayList<Patient>();
            for (Patient patientCollectionNewPatientToAttach : patientCollectionNew) {
                patientCollectionNewPatientToAttach = em.getReference(patientCollectionNewPatientToAttach.getClass(), patientCollectionNewPatientToAttach.getIdPatient());
                attachedPatientCollectionNew.add(patientCollectionNewPatientToAttach);
            }
            patientCollectionNew = attachedPatientCollectionNew;
            person.setPatientCollection(patientCollectionNew);
            Collection<Medicalstaff> attachedMedicalstaffCollectionNew = new ArrayList<Medicalstaff>();
            for (Medicalstaff medicalstaffCollectionNewMedicalstaffToAttach : medicalstaffCollectionNew) {
                medicalstaffCollectionNewMedicalstaffToAttach = em.getReference(medicalstaffCollectionNewMedicalstaffToAttach.getClass(), medicalstaffCollectionNewMedicalstaffToAttach.getIdMedicalStaff());
                attachedMedicalstaffCollectionNew.add(medicalstaffCollectionNewMedicalstaffToAttach);
            }
            medicalstaffCollectionNew = attachedMedicalstaffCollectionNew;
            person.setMedicalstaffCollection(medicalstaffCollectionNew);
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getIdUser());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            person.setUserCollection(userCollectionNew);
            person = em.merge(person);
            for (Patient patientCollectionNewPatient : patientCollectionNew) {
                if (!patientCollectionOld.contains(patientCollectionNewPatient)) {
                    Person oldPersonOfPatientCollectionNewPatient = patientCollectionNewPatient.getPerson();
                    patientCollectionNewPatient.setPerson(person);
                    patientCollectionNewPatient = em.merge(patientCollectionNewPatient);
                    if (oldPersonOfPatientCollectionNewPatient != null && !oldPersonOfPatientCollectionNewPatient.equals(person)) {
                        oldPersonOfPatientCollectionNewPatient.getPatientCollection().remove(patientCollectionNewPatient);
                        oldPersonOfPatientCollectionNewPatient = em.merge(oldPersonOfPatientCollectionNewPatient);
                    }
                }
            }
            for (Medicalstaff medicalstaffCollectionNewMedicalstaff : medicalstaffCollectionNew) {
                if (!medicalstaffCollectionOld.contains(medicalstaffCollectionNewMedicalstaff)) {
                    Person oldPersonOfMedicalstaffCollectionNewMedicalstaff = medicalstaffCollectionNewMedicalstaff.getPerson();
                    medicalstaffCollectionNewMedicalstaff.setPerson(person);
                    medicalstaffCollectionNewMedicalstaff = em.merge(medicalstaffCollectionNewMedicalstaff);
                    if (oldPersonOfMedicalstaffCollectionNewMedicalstaff != null && !oldPersonOfMedicalstaffCollectionNewMedicalstaff.equals(person)) {
                        oldPersonOfMedicalstaffCollectionNewMedicalstaff.getMedicalstaffCollection().remove(medicalstaffCollectionNewMedicalstaff);
                        oldPersonOfMedicalstaffCollectionNewMedicalstaff = em.merge(oldPersonOfMedicalstaffCollectionNewMedicalstaff);
                    }
                }
            }
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    userCollectionOldUser.setPerson(null);
                    userCollectionOldUser = em.merge(userCollectionOldUser);
                }
            }
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    Person oldPersonOfUserCollectionNewUser = userCollectionNewUser.getPerson();
                    userCollectionNewUser.setPerson(person);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                    if (oldPersonOfUserCollectionNewUser != null && !oldPersonOfUserCollectionNewUser.equals(person)) {
                        oldPersonOfUserCollectionNewUser.getUserCollection().remove(userCollectionNewUser);
                        oldPersonOfUserCollectionNewUser = em.merge(oldPersonOfUserCollectionNewUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = person.getIdPerson();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
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
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getIdPerson();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Patient> patientCollectionOrphanCheck = person.getPatientCollection();
            for (Patient patientCollectionOrphanCheckPatient : patientCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Patient " + patientCollectionOrphanCheckPatient + " in its patientCollection field has a non-nullable person field.");
            }
            Collection<Medicalstaff> medicalstaffCollectionOrphanCheck = person.getMedicalstaffCollection();
            for (Medicalstaff medicalstaffCollectionOrphanCheckMedicalstaff : medicalstaffCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Person (" + person + ") cannot be destroyed since the Medicalstaff " + medicalstaffCollectionOrphanCheckMedicalstaff + " in its medicalstaffCollection field has a non-nullable person field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<User> userCollection = person.getUserCollection();
            for (User userCollectionUser : userCollection) {
                userCollectionUser.setPerson(null);
                userCollectionUser = em.merge(userCollectionUser);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
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

    public Person findPerson(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
