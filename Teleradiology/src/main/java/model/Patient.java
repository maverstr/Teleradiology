/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INFO-H-400
 */
@Entity
@Table(name = "patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
    , @NamedQuery(name = "Patient.findByIdPatient", query = "SELECT p FROM Patient p WHERE p.idPatient = :idPatient")
    , @NamedQuery(name = "Patient.findByActive", query = "SELECT p FROM Patient p WHERE p.active = :active")
    , @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.person.nameGiven = :nameGiven")})

public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPatient")
    private Integer idPatient;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Collection<Imagingstudy> imagingstudyCollection;
    @JoinColumn(name = "general_practitioner", referencedColumnName = "idMedicalStaff")
    @ManyToOne
    private Medicalstaff generalPractitioner;
    @JoinColumn(name = "person", referencedColumnName = "idPerson")
    @ManyToOne(optional = false)
    private Person person;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Collection<Patientdicomidentifier> patientdicomidentifierCollection;

    public Patient() {
    }

    public Patient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public Patient(Integer idPatient, boolean active) {
        this.idPatient = idPatient;
        this.active = active;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Collection<Imagingstudy> getImagingstudyCollection() {
        return imagingstudyCollection;
    }


    public void setImagingstudyCollection(Collection<Imagingstudy> imagingstudyCollection) {
        this.imagingstudyCollection = imagingstudyCollection;
    }

    public Medicalstaff getGeneralPractitioner() {
        return generalPractitioner;
    }

    public void setGeneralPractitioner(Medicalstaff generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlTransient
    public Collection<Patientdicomidentifier> getPatientdicomidentifierCollection() {
        return patientdicomidentifierCollection;
    }

    public void setPatientdicomidentifierCollection(Collection<Patientdicomidentifier> patientdicomidentifierCollection) {
        this.patientdicomidentifierCollection = patientdicomidentifierCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatient != null ? idPatient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.idPatient == null && other.idPatient != null) || (this.idPatient != null && !this.idPatient.equals(other.idPatient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patient[ idPatient=" + idPatient + " ]";
    }
    
}
