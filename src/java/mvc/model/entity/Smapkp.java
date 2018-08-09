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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author clsma
 */
@Entity
@Table(name = "SMAPKP", catalog = "", schema = "SMA_IX14", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCIA", "CODPKP", "AGNPRS", "PRDPRS"})})
@NamedQueries({
    @NamedQuery(name = "Smapkp.findAll", query = "SELECT s FROM Smapkp s")
   })
public class Smapkp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CODCIA", nullable = false, length = 3)
    private String codcia;
    @Id
    @Basic(optional = false)
    @Column(name = "NROPKP", nullable = false, length = 10)
    private String nropkp;
    @Basic(optional = false)
    @Column(name = "AGNPRS", nullable = false, length = 4)
    private String agnprs;
    @Basic(optional = false)
    @Column(name = "PRDPRS", nullable = false, length = 1)
    private String prdprs;
    @Basic(optional = false)
    @Column(name = "NOMPKP", nullable = false, length = 150)
    private String nompkp;
    @Column(name = "FCIPKP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fcipkp;
    @Column(name = "FCVPKP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fcvpkp;
    @Basic(optional = false)
    @Column(name = "STDPKP", nullable = false, length = 10)
    private String stdpkp;
    @Basic(optional = false)
    @Column(name = "CODPKP", nullable = false, length = 6)
    private String codpkp;
    @Basic(optional = false)
    @Column(name = "NROUSR", nullable = false, length = 10)
    private String nrousr;
    @Basic(optional = false)
    @Column(name = "TPOPKP", nullable = false, length = 3)
    private String tpopkp;

    public Smapkp() {
    }

    public Smapkp(String nropkp) {
        this.nropkp = nropkp;
    }

    public Smapkp(String nropkp, String codcia, String agnprs, String prdprs, String nompkp, String stdpkp, String codpkp, String nrousr, String tpopkp) {
        this.nropkp = nropkp;
        this.codcia = codcia;
        this.agnprs = agnprs;
        this.prdprs = prdprs;
        this.nompkp = nompkp;
        this.stdpkp = stdpkp;
        this.codpkp = codpkp;
        this.nrousr = nrousr;
        this.tpopkp = tpopkp;
    }

    public String getCodcia() {
        return codcia;
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    public String getNropkp() {
        return nropkp;
    }

    public void setNropkp(String nropkp) {
        this.nropkp = nropkp;
    }

    public String getAgnprs() {
        return agnprs;
    }

    public void setAgnprs(String agnprs) {
        this.agnprs = agnprs;
    }

    public String getPrdprs() {
        return prdprs;
    }

    public void setPrdprs(String prdprs) {
        this.prdprs = prdprs;
    }

    public String getNompkp() {
        return nompkp;
    }

    public void setNompkp(String nompkp) {
        this.nompkp = nompkp;
    }

    public Date getFcipkp() {
        return fcipkp;
    }

    public void setFcipkp(Date fcipkp) {
        this.fcipkp = fcipkp;
    }

    public Date getFcvpkp() {
        return fcvpkp;
    }

    public void setFcvpkp(Date fcvpkp) {
        this.fcvpkp = fcvpkp;
    }

    public String getStdpkp() {
        return stdpkp;
    }

    public void setStdpkp(String stdpkp) {
        this.stdpkp = stdpkp;
    }

    public String getCodpkp() {
        return codpkp;
    }

    public void setCodpkp(String codpkp) {
        this.codpkp = codpkp;
    }

    public String getNrousr() {
        return nrousr;
    }

    public void setNrousr(String nrousr) {
        this.nrousr = nrousr;
    }

    public String getTpopkp() {
        return tpopkp;
    }

    public void setTpopkp(String tpopkp) {
        this.tpopkp = tpopkp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nropkp != null ? nropkp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smapkp)) {
            return false;
        }
        Smapkp other = (Smapkp) object;
        if ((this.nropkp == null && other.nropkp != null) || (this.nropkp != null && !this.nropkp.equals(other.nropkp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mvc.model.entity.Smapkp[ nropkp=" + nropkp + " ]";
    }
    
}
