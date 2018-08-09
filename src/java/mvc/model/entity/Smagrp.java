 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author clsma
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smagrp.findAll", query = "SELECT s FROM Smagrp s")})
public class Smagrp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String idegrp;
    @Basic(optional = false)
    private String nrogrp;
    @Basic(optional = false)
    private short kpcgrp;
    @Basic(optional = false)
    private short rsvgrp;
    @Basic(optional = false)
    private short mtrgrp;
    @Basic(optional = false)
    private short prmgrp;
    @Basic(optional = false)
    private short pergrp;
    @Basic(optional = false)
    private short pycgrp;
    private String clrgrp;
    @Basic(optional = false)
    private String tpomat;
    @Basic(optional = false)
    private String stdgrp;
    @Basic(optional = false)
    private String tpogrp;
    @Basic(optional = false)
    private String codpsm;
    @Basic(optional = false)
    private String nropak;
    @Basic(optional = false)
    private String idecrs;

    public Smagrp() {
    }

    public Smagrp(String idegrp) {
        this.idegrp = idegrp;
    }

    public Smagrp(String idegrp, String nrogrp, short kpcgrp, short rsvgrp, short mtrgrp, short prmgrp, short pergrp, short pycgrp, String tpomat, String stdgrp, String tpogrp, String codpsm, String nropak, String idecrs) {
        this.idegrp = idegrp;
        this.nrogrp = nrogrp;
        this.kpcgrp = kpcgrp;
        this.rsvgrp = rsvgrp;
        this.mtrgrp = mtrgrp;
        this.prmgrp = prmgrp;
        this.pergrp = pergrp;
        this.pycgrp = pycgrp;
        this.tpomat = tpomat;
        this.stdgrp = stdgrp;
        this.tpogrp = tpogrp;
        this.codpsm = codpsm;
        this.nropak = nropak;
        this.idecrs = idecrs;
    }

    public String getIdegrp() {
        return idegrp;
    }

    public void setIdegrp(String idegrp) {
        this.idegrp = idegrp;
    }

    public String getNrogrp() {
        return nrogrp;
    }

    public void setNrogrp(String nrogrp) {
        this.nrogrp = nrogrp;
    }

    public short getKpcgrp() {
        return kpcgrp;
    }

    public void setKpcgrp(short kpcgrp) {
        this.kpcgrp = kpcgrp;
    }

    public short getRsvgrp() {
        return rsvgrp;
    }

    public void setRsvgrp(short rsvgrp) {
        this.rsvgrp = rsvgrp;
    }

    public short getMtrgrp() {
        return mtrgrp;
    }

    public void setMtrgrp(short mtrgrp) {
        this.mtrgrp = mtrgrp;
    }

    public short getPrmgrp() {
        return prmgrp;
    }

    public void setPrmgrp(short prmgrp) {
        this.prmgrp = prmgrp;
    }

    public short getPergrp() {
        return pergrp;
    }

    public void setPergrp(short pergrp) {
        this.pergrp = pergrp;
    }

    public short getPycgrp() {
        return pycgrp;
    }

    public void setPycgrp(short pycgrp) {
        this.pycgrp = pycgrp;
    }

    public String getClrgrp() {
        return clrgrp;
    }

    public void setClrgrp(String clrgrp) {
        this.clrgrp = clrgrp;
    }

    public String getTpomat() {
        return tpomat;
    }

    public void setTpomat(String tpomat) {
        this.tpomat = tpomat;
    }

    public String getStdgrp() {
        return stdgrp;
    }

    public void setStdgrp(String stdgrp) {
        this.stdgrp = stdgrp;
    }

    public String getTpogrp() {
        return tpogrp;
    }

    public void setTpogrp(String tpogrp) {
        this.tpogrp = tpogrp;
    }

    public String getCodpsm() {
        return codpsm;
    }

    public void setCodpsm(String codpsm) {
        this.codpsm = codpsm;
    }

    public String getNropak() {
        return nropak;
    }

    public void setNropak(String nropak) {
        this.nropak = nropak;
    }

    public String getIdecrs() {
        return idecrs;
    }

    public void setIdecrs(String idecrs) {
        this.idecrs = idecrs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idegrp != null ? idegrp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smagrp)) {
            return false;
        }
        Smagrp other = (Smagrp) object;
        if ((this.idegrp == null && other.idegrp != null) || (this.idegrp != null && !this.idegrp.equals(other.idegrp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mvc.model.entity.Smagrp[ idegrp=" + idegrp + " ]";
    }
    
}
