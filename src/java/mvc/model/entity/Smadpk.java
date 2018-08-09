package mvc.model.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMADPK")
@NamedQueries({
    @NamedQuery(name = "Smadpk.findAll", query = "SELECT s FROM Smadpk s")})
public class Smadpk implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nrodpk;
    private String nropkp;
    private String smtpsm;      
    private BigInteger espdpk;
    private BigInteger soldpk;
    private String stddpk;
    private Smapgm idepgm;
    private Smamat nromat;

    public Smadpk() {
    }

    public Smadpk(String nrodpk) {
        this.nrodpk = nrodpk;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NRODPK", nullable = false, length = 10)
    public String getNrodpk() {
        return nrodpk;
    }

    public void setNrodpk(String nrodpk) {
        this.nrodpk = nrodpk;
    }

    @Basic(optional = false)
    @Column(name = "NROPKP", nullable = false, length = 10)
    public String getNropkp() {
        return nropkp;
    }

    public void setNropkp(String nropkp) {
        this.nropkp = nropkp;
    }

    @Basic(optional = false)
    @Column(name = "SMTPSM", nullable = false, length = 2)
    public String getSmtpsm() {
        return smtpsm;
    }

    public void setSmtpsm(String smtpsm) {
        this.smtpsm = smtpsm;
    }

    @Basic(optional = false)
    @Column(name = "ESPDPK", nullable = false)
    public BigInteger getEspdpk() {
        return espdpk;
    }

    public void setEspdpk(BigInteger espdpk) {
        this.espdpk = espdpk;
    }

    @Basic(optional = false)
    @Column(name = "SOLDPK", nullable = false)
    public BigInteger getSoldpk() {
        return soldpk;
    }

    public void setSoldpk(BigInteger soldpk) {
        this.soldpk = soldpk;
    }

    @Basic(optional = false)
    @Column(name = "STDDPK", nullable = false, length = 13)
    public String getStddpk() {
        return stddpk;
    }

    public void setStddpk(String stddpk) {
        this.stddpk = stddpk;
    }

    @JoinColumn(name = "IDEPGM", referencedColumnName = "IDEPGM", nullable = false)
    @ManyToOne(optional = false)
    public Smapgm getIdepgm() {
        return idepgm;
    }

    public void setIdepgm(Smapgm idepgm) {
        this.idepgm = idepgm;
    }

    @JoinColumn(name = "NROMAT", referencedColumnName = "NROMAT", nullable = false)
    @ManyToOne(optional = false)
    public Smamat getNromat() {
        return nromat;
    }

    public void setNromat(Smamat nromat) {
        this.nromat = nromat;
    }

}
