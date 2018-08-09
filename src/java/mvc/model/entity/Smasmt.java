/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author clsma
 */
@Entity
@Table(name = "SMASMT", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NROPKP", "NROPSS"})})
@NamedQueries({
    @NamedQuery(name = "Smasmt.findAll", query = "SELECT s FROM Smasmt s"),
    @NamedQuery(name = "Smasmt.findByIdesmt", query = "SELECT s FROM Smasmt s WHERE s.idesmt = :idesmt"),
    @NamedQuery(name = "Smasmt.findByNrocst", query = "SELECT s FROM Smasmt s WHERE s.nrocst = :nrocst"),
    @NamedQuery(name = "Smasmt.findByAsgcrs", query = "SELECT s FROM Smasmt s WHERE s.asgcrs = :asgcrs"),
    @NamedQuery(name = "Smasmt.findByStdsmt", query = "SELECT s FROM Smasmt s WHERE s.stdsmt = :stdsmt")})
public class Smasmt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDESMT", nullable = false, length = 10)
    private String idesmt;
    @Basic(optional = false)
    @Column(name = "NROCST", nullable = false, length = 7)
    private String nrocst;
    @Basic(optional = false)
    @Column(name = "ASGCRS", nullable = false)
    private BigInteger asgcrs;
    @Basic(optional = false)
    @Column(name = "STDSMT", nullable = false, length = 12)
    private String stdsmt;
    @Basic(optional = false)
    @Column(name = "NROPSS", nullable = false, length = 12)
    private String nropss;
    @Basic(optional = false)
    @Column(name = "NROPKP", nullable = false, length = 12)
    private String nropkp;
    

    public Smasmt() {
    }

    public Smasmt(String idesmt) {
        this.idesmt = idesmt;
    }

    public Smasmt(String idesmt, String nrocst, BigInteger asgcrs, String stdsmt) {
        this.idesmt = idesmt;
        this.nrocst = nrocst;
        this.asgcrs = asgcrs;
        this.stdsmt = stdsmt;
    }

    public String getNropss() {
        return nropss;
    }

    public void setNropss(String nropss) {
        this.nropss = nropss;
    }

    public String getNropkp() {
        return nropkp;
    }

    public void setNropkp(String nropkp) {
        this.nropkp = nropkp;
    }
    
    
    

    public String getIdesmt() {
        return idesmt;
    }

    public void setIdesmt(String idesmt) {
        this.idesmt = idesmt;
    }

    public String getNrocst() {
        return nrocst;
    }

    public void setNrocst(String nrocst) {
        this.nrocst = nrocst;
    }

    public BigInteger getAsgcrs() {
        return asgcrs;
    }

    public void setAsgcrs(BigInteger asgcrs) {
        this.asgcrs = asgcrs;
    }

    public String getStdsmt() {
        return stdsmt;
    }

    public void setStdsmt(String stdsmt) {
        this.stdsmt = stdsmt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idesmt != null ? idesmt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smasmt)) {
            return false;
        }
        Smasmt other = (Smasmt) object;
        if ((this.idesmt == null && other.idesmt != null) || (this.idesmt != null && !this.idesmt.equals(other.idesmt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mvc.model.entity.Smasmt[ idesmt=" + idesmt + " ]";
    }
    
}
