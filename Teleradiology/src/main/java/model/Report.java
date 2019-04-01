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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INFO-H-400
 */
@Entity
@Table(name = "report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByIdreport", query = "SELECT r FROM Report r WHERE r.idreport = :idreport")
    , @NamedQuery(name = "Report.findByContent", query = "SELECT r FROM Report r WHERE r.content = :content")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreport")
    private Integer idreport;
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "idseries", referencedColumnName = "idSeries")
    @ManyToOne(optional = false)
    private Series idseries;

    public Report() {
    }

    public Report(Integer idreport) {
        this.idreport = idreport;
    }

    public Integer getIdreport() {
        return idreport;
    }

    public void setIdreport(Integer idreport) {
        this.idreport = idreport;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Series getIdseries() {
        return idseries;
    }

    public void setIdseries(Series idseries) {
        this.idseries = idseries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreport != null ? idreport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.idreport == null && other.idreport != null) || (this.idreport != null && !this.idreport.equals(other.idreport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Report[ idreport=" + idreport + " ]";
    }
    
}
