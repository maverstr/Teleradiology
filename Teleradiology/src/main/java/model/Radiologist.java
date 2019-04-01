/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INFO-H-400
 */
@Entity
@Table(name = "radiologist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radiologist.findAll", query = "SELECT r FROM Radiologist r")
    , @NamedQuery(name = "Radiologist.findById", query = "SELECT r FROM Radiologist r WHERE r.id = :id")
    , @NamedQuery(name = "Radiologist.findByMedicalstaff", query = "SELECT r FROM Radiologist r WHERE r.medicalstaff = :medicalstaff")})
public class Radiologist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "medicalstaff")
    private String medicalstaff;
    @JoinColumn(name = "id", referencedColumnName = "idMedicalStaff", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Medicalstaff medicalstaff1;

    public Radiologist() {
    }

    public Radiologist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicalstaff() {
        return medicalstaff;
    }

    public void setMedicalstaff(String medicalstaff) {
        this.medicalstaff = medicalstaff;
    }

    public Medicalstaff getMedicalstaff1() {
        return medicalstaff1;
    }

    public void setMedicalstaff1(Medicalstaff medicalstaff1) {
        this.medicalstaff1 = medicalstaff1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Radiologist)) {
            return false;
        }
        Radiologist other = (Radiologist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Radiologist[ id=" + id + " ]";
    }
    
}
