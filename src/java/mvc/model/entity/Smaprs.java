package mvc.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "smaprs", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codcia", "nriprs"}),
    @UniqueConstraint(columnNames = {"codcia", "codprs"})})
@NamedQueries({
    @NamedQuery(name = "Smaprs.findAll", query = "SELECT s FROM Smaprs s")})
public class Smaprs implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nroprs;
    private String titprs;
    private String codprs;
    private String apeprs;
    private String nomprs;
    private String npqprs;
    private String nacprs;
    private Date fhnprs;
    private String ideprs;
    private String nriprs;
    private String epsprs;
    private String sngprs;
    private String civprs;
    private String sexprs;
    private String dirprs;
    private String telprs;
    private String celprs;
    private String emlprs;
    private String pswprs;
    private String expprs;
    private String eskprs;
    private String estprs;
    private String snpprs;
//    private Date fsnprs;
    private String nroxvt;
    private double psnprs;
    private Smaciu ideciu;
    private Smacia codcia;
    
    public Smaprs() {
        this.codprs = "";
        this.nriprs = "";
        this.apeprs = "";
        this.nomprs = "";
        this.sexprs = "";
    }

    public Smaprs(String nroprs) {
        this.nroprs = nroprs;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "nroprs", nullable = false, length = 10)
    public String getNroprs() {
        return nroprs;
    }

    public void setNroprs(String nroprs) {
        this.nroprs = nroprs;
    }

    @Basic(optional = false)
    @Column(name = "titprs", nullable = false, length = 3)
    public String getTitprs() {
        return titprs;
    }

    public void setTitprs(String titprs) {
        this.titprs = titprs;
    }

    @Basic(optional = false)
    @Column(name = "codprs", nullable = false, length = 10)
    public String getCodprs() {
        return codprs;
    }

    public void setCodprs(String codprs) {
        this.codprs = codprs;
    }

    @Basic(optional = false)
    @Column(name = "apeprs", nullable = false, length = 50)
    public String getApeprs() {
        return apeprs;
    }

    public void setApeprs(String apeprs) {
        this.apeprs = apeprs;
    }

    @Basic(optional = false)
    @Column(name = "nomprs", nullable = false, length = 50)
    public String getNomprs() {
        return nomprs;
    }

    public void setNomprs(String nomprs) {
        this.nomprs = nomprs;
    }

    @Basic(optional = false)
    @Column(name = "npqprs", nullable = false, length = 15)
    public String getNpqprs() {
        return npqprs;
    }

    public void setNpqprs(String npqprs) {
        this.npqprs = npqprs;
    }

    @Basic(optional = false)
    @Column(name = "nacprs", nullable = false, length = 6)
    public String getNacprs() {
        return nacprs;
    }

    public void setNacprs(String nacprs) {
        this.nacprs = nacprs;
    }

    @Basic(optional = false)
    @Column(name = "fhnprs", nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getFhnprs() {
        return fhnprs;
    }

    public void setFhnprs(Date fhnprs) {
        this.fhnprs = fhnprs;
    }

    @Basic(optional = false)
    @Column(name = "ideprs", nullable = false, length = 4)
    public String getIdeprs() {
        return ideprs;
    }

    public void setIdeprs(String ideprs) {
        this.ideprs = ideprs;
    }

    @Basic(optional = false)
    @Column(name = "nriprs", nullable = false, length = 15)
    public String getNriprs() {
        return nriprs;
    }

    public void setNriprs(String nriprs) {
        this.nriprs = nriprs;
    }

    @Basic(optional = false)
    @Column(name = "epsprs", nullable = false, length = 3)
    public String getEpsprs() {
        return epsprs;
    }

    public void setEpsprs(String epsprs) {
        this.epsprs = epsprs;
    }

    @Basic(optional = false)
    @Column(name = "sngprs", nullable = false, length = 3)
    public String getSngprs() {
        return sngprs;
    }

    public void setSngprs(String sngprs) {
        this.sngprs = sngprs;
    }

    @Basic(optional = false)
    @Column(name = "civprs", nullable = false, length = 3)
    public String getCivprs() {
        return civprs;
    }

    public void setCivprs(String civprs) {
        this.civprs = civprs;
    }

    @Basic(optional = false)
    @Column(name = "sexprs", nullable = false, length = 10)
    public String getSexprs() {
        return sexprs;
    }

    public void setSexprs(String sexprs) {
        this.sexprs = sexprs;
    }

    @Basic(optional = false)
    @Column(name = "dirprs", nullable = false, length = 80)
    public String getDirprs() {
        return dirprs;
    }

    public void setDirprs(String dirprs) {
        this.dirprs = dirprs;
    }

    @Basic(optional = false)
    @Column(name = "telprs", nullable = false, length = 80)
    public String getTelprs() {
        return telprs;
    }

    public void setTelprs(String telprs) {
        this.telprs = telprs;
    }

    @Basic(optional = false)
    @Column(name = "celprs", nullable = false, length = 80)
    public String getCelprs() {
        return celprs;
    }

    public void setCelprs(String celprs) {
        this.celprs = celprs;
    }

    @Basic(optional = false)
    @Column(name = "emlprs", nullable = false, length = 128)
    public String getEmlprs() {
        return emlprs;
    }

    public void setEmlprs(String emlprs) {
        this.emlprs = emlprs;
    }

    @Basic(optional = false)
    @Column(name = "pswprs", nullable = false, length = 128)
    public String getPswprs() {
        return pswprs;
    }

    public void setPswprs(String pswprs) {
        this.pswprs = pswprs;
    }

    @Basic(optional = false)
    @Column(name = "expprs", nullable = false, length = 6)
    public String getExpprs() {
        return expprs;
    }

    public void setExpprs(String expprs) {
        this.expprs = expprs;
    }

    @Basic(optional = false)
    @Column(name = "eskprs", nullable = false, length = 3)
    public String getEskprs() {
        return eskprs;
    }

    public void setEskprs(String eskprs) {
        this.eskprs = eskprs;
    }

    @Basic(optional = false)
    @Column(name = "estprs", nullable = false, length = 3)
    public String getEstprs() {
        return estprs;
    }

    public void setEstprs(String estprs) {
        this.estprs = estprs;
    }

    @Basic(optional = false)
    @Column(name = "snpprs", nullable = false, length = 15)
    public String getSnpprs() {
        return snpprs;
    }

    public void setSnpprs(String snpprs) {
        this.snpprs = snpprs;
    }

//    @Basic(optional = false)
//    @Column(name = "fsnprs", nullable = false)
//    @Temporal(TemporalType.DATE)
//    public Date getFsnprs() {
//        return fsnprs;
//    }
//
//    public void setFsnprs(Date fsnprs) {
//        this.fsnprs = fsnprs;
//    }

    @Basic(optional = false)
    @Column(name = "nroxvt", nullable = false, length = 5)
    public String getNroxvt() {
        return nroxvt;
    }

    public void setNroxvt(String nroxvt) {
        this.nroxvt = nroxvt;
    }

    @Basic(optional = false)
    @Column(name = "psnprs", nullable = false)
    public double getPsnprs() {
        return psnprs;
    }

    public void setPsnprs(double psnprs) {
        this.psnprs = psnprs;
    }

    @JoinColumn(name = "ideciu", referencedColumnName = "ideciu", nullable = false)
    @ManyToOne(optional = false)
    public Smaciu getIdeciu() {
        return ideciu;
    }

    public void setIdeciu(Smaciu ideciu) {
        this.ideciu = ideciu;
    }

    @JoinColumn(name = "codcia", referencedColumnName = "codcia", nullable = false)
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
        hash += (nroprs != null ? nroprs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smaprs)) {
            return false;
        }
        Smaprs other = (Smaprs) object;
        if ((this.nroprs == null && other.nroprs != null) || (this.nroprs != null && !this.nroprs.equals(other.nroprs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Smaprs[ nroprs=" + nroprs + " ]";
    }
}
