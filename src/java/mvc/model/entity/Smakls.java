/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAKLS")
public class Smakls implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codkls;
    private String nomkls;
    private String agnprs;
    private String prdprs;
    private String tpokls;
    private Date fchkls;
    private String stdkls;
    private String sqlbkr;
    private Smacia codcia;

    public Smakls() {
    }

    public Smakls(String codkls) {
        this.codkls = codkls;
    }

    public Smakls(String codkls, String nomkls, String agnprs, String prdprs, String tpokls, Date fchkls, String stdkls, String sqlbkr) {
        this.codkls = codkls;
        this.nomkls = nomkls;
        this.agnprs = agnprs;
        this.prdprs = prdprs;
        this.tpokls = tpokls;
        this.fchkls = fchkls;
        this.stdkls = stdkls;
        this.sqlbkr = sqlbkr;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CODKLS", nullable = false, length = 10)
    public String getCodkls() {
        return codkls;
    }

    public void setCodkls(String codkls) {
        this.codkls = codkls;
    }

    @Basic(optional = false)
    @Column(name = "NOMKLS", nullable = false, length = 60)
    public String getNomkls() {
        return nomkls;
    }

    public void setNomkls(String nomkls) {
        this.nomkls = nomkls;
    }

    @Basic(optional = false)
    @Column(name = "AGNPRS", nullable = false, length = 4)
    public String getAgnprs() {
        return agnprs;
    }

    public void setAgnprs(String agnprs) {
        this.agnprs = agnprs;
    }

    @Basic(optional = false)
    @Column(name = "PRDPRS", nullable = false, length = 1)
    public String getPrdprs() {
        return prdprs;
    }

    public void setPrdprs(String prdprs) {
        this.prdprs = prdprs;
    }

    @Basic(optional = false)
    @Column(name = "TPOKLS", nullable = false, length = 15)
    public String getTpokls() {
        return tpokls;
    }

    public void setTpokls(String tpokls) {
        this.tpokls = tpokls;
    }

    @Basic(optional = false)
    @Column(name = "FCHKLS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFchkls() {
        return fchkls;
    }

    public void setFchkls(Date fchkls) {
        this.fchkls = fchkls;
    }

    @Basic(optional = false)
    @Column(name = "STDKLS", nullable = false, length = 13)
    public String getStdkls() {
        return stdkls;
    }

    public void setStdkls(String stdkls) {
        this.stdkls = stdkls;
    }

    @Basic(optional = false)
    @Column(name = "SQLBKR", nullable = false, length = 4000)
    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codkls != null ? codkls.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smakls)) {
            return false;
        }
        Smakls other = (Smakls) object;
        if ((this.codkls == null && other.codkls != null) || (this.codkls != null && !this.codkls.equals(other.codkls))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.academicas.Smakls[ codkls=" + codkls + " ]";
    }

}
