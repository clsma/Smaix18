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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAHRP")
@NamedQueries({
    @NamedQuery(name = "Smahrp.findAll", query = "SELECT s FROM Smahrp s")})
public class Smahrp implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nrohrp;
    private Smaprs nroprs;
    private String agnprs;
    private String prdprs;
    private String diahrp;
    private String horhrp;
    private String tpohrp;
    private String stdhrp;
    private Smacia codcia;

    public Smahrp() {
    }

    public Smahrp(String nrohrp) {
        this.nrohrp = nrohrp;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROHRP", nullable = false, length = 10)
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = { @Parameter(name = "sequence", value = "SEQHRP") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    public String getNrohrp() {
        return nrohrp;
    }

    public void setNrohrp(String nrohrp) {
        this.nrohrp = nrohrp;
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
    @Column(name = "DIAHRP", nullable = false, length = 6)
    public String getDiahrp() {
        return diahrp;
    }

    public void setDiahrp(String diahrp) {
        this.diahrp = diahrp;
    }

    @Basic(optional = false)
    @Column(name = "HORHRP", nullable = false, length = 20)
    public String getHorhrp() {
        return horhrp;
    }

    public void setHorhrp(String horhrp) {
        this.horhrp = horhrp;
    }

    @Basic(optional = false)
    @Column(name = "TPOHRP", nullable = false)
    public String getTpohrp() {
        return tpohrp;
    }

    public void setTpohrp(String tpohrp) {
        this.tpohrp = tpohrp;
    }

    @Basic(optional = false)
    @Column(name = "STDHRP", nullable = false, length = 13)
    public String getStdhrp() {
        return stdhrp;
    }

    public void setStdhrp(String stdhrp) {
        this.stdhrp = stdhrp;
    }

    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }
    
    @JoinColumn(name = "NROPRS", referencedColumnName = "NROPRS", nullable = false)
    @ManyToOne(optional = false)
    public Smaprs getNroprs() {
        return nroprs;
    }

    public void setNroprs(Smaprs nroprs) {
        this.nroprs = nroprs;
    }
}
