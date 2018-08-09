package mvc.model.entity;

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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAMDL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCIA", "AGNPRS", "PRDPRS", "IDEPGM", "NROPSM", "NROMAT", "CODMDL"})})
@NamedQueries({
    @NamedQuery(name = "Smamdl.findAll", query = "SELECT s FROM Smamdl s")})
public class Smamdl implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idemdl;
    private String agnprs;
    private String prdprs;
    private String nropsm;
    private String stdmdl;
    private int crdakd;
    private double ptjmdl;
    private Smapgm idepgm;
    private Smamat nromat;
    private Smamat codmdl;
    private Smacia codcia;

    public Smamdl() {
    }

    @Id
    @Basic(optional = false)
    @Column(name = "IDEMDL", nullable = false, length = 10)
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = { @Parameter(name = "sequence", value = "SEQMDL") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    public String getIdemdl() {
        return idemdl;
    }

    public void setIdemdl(String idemdl) {
        this.idemdl = idemdl;
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
    @Column(name = "NROPSM", nullable = false, length = 4)
    public String getNropsm() {
        return nropsm;
    }

    public void setNropsm(String nropsm) {
        this.nropsm = nropsm;
    }

    @Basic(optional = false)
    @Column(name = "STDMDL", nullable = false, length = 12)
    public String getStdmdl() {
        return stdmdl;
    }

    public void setStdmdl(String stdmdl) {
        this.stdmdl = stdmdl;
    }

    @Basic(optional = false)
    @Column(name = "CRDAKD", nullable = false)
    public int getCrdakd() {
        return crdakd;
    }

    public void setCrdakd(int crdakd) {
        this.crdakd = crdakd;
    }

    @Basic(optional = false)
    @Column(name = "PTJMDL", nullable = false)
    public double getPtjmdl() {
        return ptjmdl;
    }

    public void setPtjmdl(double ptjmdl) {
        this.ptjmdl = ptjmdl;
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

    @JoinColumn(name = "CODMDL", referencedColumnName = "NROMAT", nullable = false)
    @ManyToOne(optional = false)
    public Smamat getCodmdl() {
        return codmdl;
    }

    public void setCodmdl(Smamat codmdl) {
        this.codmdl = codmdl;
    }

    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }
}
