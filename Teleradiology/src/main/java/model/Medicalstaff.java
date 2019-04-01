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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INFO-H-400
 */
@Entity
@Table(name = "medicalstaff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicalstaff.findAll", query = "SELECT m FROM Medicalstaff m")
    , @NamedQuery(name = "Medicalstaff.findByIdMedicalStaff", query = "SELECT m FROM Medicalstaff m WHERE m.idMedicalStaff = :idMedicalStaff")
    , @NamedQuery(name = "Medicalstaff.findByActive", query = "SELECT m FROM Medicalstaff m WHERE m.active = :active")
    , @NamedQuery(name = "Medicalstaff.findByQualificationCode", query = "SELECT m FROM Medicalstaff m WHERE m.qualificationCode = :qualificationCode")})
public class Medicalstaff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMedicalStaff")
    private Integer idMedicalStaff;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "qualification_code")
    private String qualificationCode;
    @OneToMany(mappedBy = "interpreter")
    private Collection<Imagingstudy> imagingstudyCollection;
    @OneToMany(mappedBy = "referrer")
    private Collection<Imagingstudy> imagingstudyCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practitioner")
    private Collection<Practitionerdicomidentifier> practitionerdicomidentifierCollection;
    @OneToMany(mappedBy = "generalPractitioner")
    private Collection<Patient> patientCollection;
    @OneToMany(mappedBy = "performer")
    private Collection<Series> seriesCollection;
    @JoinColumn(name = "person", referencedColumnName = "idPerson")
    @ManyToOne(optional = false)
    private Person person;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "medicalstaff1")
    private Radiologist radiologist;

    public Medicalstaff() {
    }

    public Medicalstaff(Integer idMedicalStaff) {
        this.idMedicalStaff = idMedicalStaff;
    }

    public Medicalstaff(Integer idMedicalStaff, boolean active, String qualificationCode) {
        this.idMedicalStaff = idMedicalStaff;
        this.active = active;
        this.qualificationCode = qualificationCode;
    }

    public Integer getIdMedicalStaff() {
        return idMedicalStaff;
    }

    public void setIdMedicalStaff(Integer idMedicalStaff) {
        this.idMedicalStaff = idMedicalStaff;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode;
    }

    @XmlTransient
    public Collection<Imagingstudy> getImagingstudyCollection() {
        return imagingstudyCollection;
    }

    public void setImagingstudyCollection(Collection<Imagingstudy> imagingstudyCollection) {
        this.imagingstudyCollection = imagingstudyCollection;
    }

    @XmlTransient
    public Collection<Imagingstudy> getImagingstudyCollection1() {
        return imagingstudyCollection1;
    }

    public void setImagingstudyCollection1(Collection<Imagingstudy> imagingstudyCollection1) {
        this.imagingstudyCollection1 = imagingstudyCollection1;
    }

    @XmlTransient
    public Collection<Practitionerdicomidentifier> getPractitionerdicomidentifierCollection() {
        return practitionerdicomidentifierCollection;
    }

    public void setPractitionerdicomidentifierCollection(Collection<Practitionerdicomidentifier> practitionerdicomidentifierCollection) {
        this.practitionerdicomidentifierCollection = practitionerdicomidentifierCollection;
    }

    @XmlTransient
    public Collection<Patient> getPatientCollection() {
        return patientCollection;
    }

    public void setPatientCollection(Collection<Patient> patientCollection) {
        this.patientCollection = patientCollection;
    }

    @XmlTransient
    public Collection<Series> getSeriesCollection() {
        return seriesCollection;
    }

    public void setSeriesCollection(Collection<Series> seriesCollection) {
        this.seriesCollection = seriesCollection;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Radiologist getRadiologist() {
        return radiologist;
    }

    public void setRadiologist(Radiologist radiologist) {
        this.radiologist = radiologist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMedicalStaff != null ? idMedicalStaff.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicalstaff)) {
            return false;
        }
        Medicalstaff other = (Medicalstaff) object;
        if ((this.idMedicalStaff == null && other.idMedicalStaff != null) || (this.idMedicalStaff != null && !this.idMedicalStaff.equals(other.idMedicalStaff))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Medicalstaff[ idMedicalStaff=" + idMedicalStaff + " ]";
    }
    
}
