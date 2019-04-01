/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INFO-H-400
 */
@Entity
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByIdPerson", query = "SELECT p FROM Person p WHERE p.idPerson = :idPerson")
    , @NamedQuery(name = "Person.findByNameFamily", query = "SELECT p FROM Person p WHERE p.nameFamily = :nameFamily")
    , @NamedQuery(name = "Person.findByNameGiven", query = "SELECT p FROM Person p WHERE p.nameGiven = :nameGiven")
    , @NamedQuery(name = "Person.findByGender", query = "SELECT p FROM Person p WHERE p.gender = :gender")
    , @NamedQuery(name = "Person.findByBirthdate", query = "SELECT p FROM Person p WHERE p.birthdate = :birthdate")
    , @NamedQuery(name = "Person.findByNationalNumber", query = "SELECT p FROM Person p WHERE p.nationalNumber = :nationalNumber")
    , @NamedQuery(name = "Person.findByActive", query = "SELECT p FROM Person p WHERE p.active = :active")
    , @NamedQuery(name = "Person.findByHospitalid", query = "SELECT p FROM Person p WHERE p.hospitalid = :hospitalid")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPerson")
    private Integer idPerson;
    @Basic(optional = false)
    @Column(name = "name_family")
    private String nameFamily;
    @Column(name = "name_given")
    private String nameGiven;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Column(name = "national_number")
    private String nationalNumber;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "hospitalid")
    private String hospitalid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<Patient> patientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Collection<Medicalstaff> medicalstaffCollection;
    @OneToMany(mappedBy = "person")
    private Collection<User> userCollection;

    public Person() {
    }

    public Person(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public Person(Integer idPerson, String nameFamily, boolean active, String hospitalid) {
        this.idPerson = idPerson;
        this.nameFamily = nameFamily;
        this.active = active;
        this.hospitalid = hospitalid;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public String getNameGiven() {
        return nameGiven;
    }

    public void setNameGiven(String nameGiven) {
        this.nameGiven = nameGiven;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection() {
        return patientCollection;
    }

    public void setPatientCollection(Collection<Patient> patientCollection) {
        this.patientCollection = patientCollection;
    }

    @XmlTransient
    public Collection<Medicalstaff> getMedicalstaffCollection() {
        return medicalstaffCollection;
    }

    public void setMedicalstaffCollection(Collection<Medicalstaff> medicalstaffCollection) {
        this.medicalstaffCollection = medicalstaffCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerson != null ? idPerson.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.idPerson == null && other.idPerson != null) || (this.idPerson != null && !this.idPerson.equals(other.idPerson))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Person[ idPerson=" + idPerson + " ]";
    }
    
}
