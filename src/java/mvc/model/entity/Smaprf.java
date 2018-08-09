package mvc.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAPRF", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCIA", "CODPRF"})})
@NamedQueries({
    @NamedQuery(name = "Smaprf.findAll", query = "SELECT s FROM Smaprf s")})
public class Smaprf implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nroprf;
    private String codprf;
    private String ktgprf;
    private String klcprf;
    private Smacia codcia;
    private Smaprs nroprs;
    
    public Smaprf() {
        this.nroprs = new Smaprs();
        this.codprf = "";
    }

    public Smaprf(String nroprf) {
        this.nroprf = nroprf;
    }

    public Smaprf(String nroprf, String codprf, String ktgprf, String klcprf) {
        this.nroprf = nroprf;
        this.codprf = codprf;
        this.ktgprf = ktgprf;
        this.klcprf = klcprf;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROPRF", nullable = false, length = 10)
    public String getNroprf() {
        return nroprf;
    }

    public void setNroprf(String nroprf) {
        this.nroprf = nroprf;
    }

    @Basic(optional = false)
    @Column(name = "CODPRF", nullable = false, length = 10)
    public String getCodprf() {
        return codprf;
    }

    public void setCodprf(String codprf) {
        this.codprf = codprf;
    }

    @Basic(optional = false)
    @Column(name = "KTGPRF", nullable = false, length = 3)
    public String getKtgprf() {
        return ktgprf;
    }

    public void setKtgprf(String ktgprf) {
        this.ktgprf = ktgprf;
    }

    @Basic(optional = false)
    @Column(name = "KLCPRF", nullable = false, length = 3)
    public String getKlcprf() {
        return klcprf;
    }

    public void setKlcprf(String klcprf) {
        this.klcprf = klcprf;
    }

    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    public Smacia getCodcia() {
        return codcia;
    }
    
    @JoinColumn(name = "NROPRS", referencedColumnName = "NROPRS", nullable = false)
    @ManyToOne(optional = false)
    public Smaprs getNroprs() {
        return nroprs;
    }

    public void setNroprs(Smaprs nroprs) {
        this.nroprs = nroprs;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }
}
