/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author clsma
 */
@Entity
@Table(name = "smaare", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codcia", "codare"})})
@NamedQueries({
    @NamedQuery(name = "Smaare.findAll", query = "SELECT s FROM Smaare s")})
public class Smaare implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nroare", nullable = false, length = 5)
    private String nroare;
    @Basic(optional = false)
    @Column(name = "codare", nullable = false, length = 2)
    private String codare;
    @Basic(optional = false)
    @Column(name = "nomare", nullable = false, length = 45)
    private String nomare;
    @Basic(optional = false)
    @Column(name = "npqare", nullable = false, length = 15)
    private String npqare;
    @Basic(optional = false)
    @Column(name = "clrare", nullable = false)
    private double clrare;
    @Basic(optional = false)
    @Column(name = "sqlbkr", nullable = false, length = 2147483647)
    private String sqlbkr;
    @JoinColumn(name = "codcia", referencedColumnName = "codcia", nullable = false)
    @ManyToOne(optional = false)
    private Smacia codcia;
    
    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nroare")
    private List<Smamat> smamatList;
    
    public Smaare() {
    }

    public Smaare(String nroare) {
        this.nroare = nroare;
    }

    public Smaare(String nroare, String codare, String nomare, String npqare, double clrare, String sqlbkr) {
        this.nroare = nroare;
        this.codare = codare;
        this.nomare = nomare;
        this.npqare = npqare;
        this.clrare = clrare;
        this.sqlbkr = sqlbkr;
    }

    public String getNroare() {
        return nroare;
    }

    public void setNroare(String nroare) {
        this.nroare = nroare;
    }

    public String getCodare() {
        return codare;
    }

    public void setCodare(String codare) {
        this.codare = codare;
    }

    public String getNomare() {
        return nomare;
    }

    public void setNomare(String nomare) {
        this.nomare = nomare;
    }

    public String getNpqare() {
        return npqare;
    }

    public void setNpqare(String npqare) {
        this.npqare = npqare;
    }

    public double getClrare() {
        return clrare;
    }

    public void setClrare(double clrare) {
        this.clrare = clrare;
    }

    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }

    public List<Smamat> getSmamatList() {
        return smamatList;
    }

    public void setSmamatList(List<Smamat> smamatList) {
        this.smamatList = smamatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroare != null ? nroare.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smaare)) {
            return false;
        }
        Smaare other = (Smaare) object;
        if ((this.nroare == null && other.nroare != null) || (this.nroare != null && !this.nroare.equals(other.nroare))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.academicas.Smaare[ nroare=" + nroare + " ]";
    }
}
