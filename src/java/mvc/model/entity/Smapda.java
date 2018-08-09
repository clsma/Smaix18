package mvc.model.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "SMAPDA")
@NamedQueries({
    @NamedQuery(name = "Smapda.findAll", query = "SELECT s FROM Smapda s")})
public class Smapda implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nropda;
    private String tpopda;
    private Date bgnpda;
    private Date endpda;
    private BigInteger prdpda;
    private String schpda;
    private String datpda;
    private String stdpda;
    private Smapak nropak;
    private Smacia codcia;

    public Smapda() {
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROPDA", nullable = false, length = 10)
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = { @Parameter(name = "sequence", value = "SEQPDA") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    public String getNropda() {
        return nropda;
    }

    public void setNropda(String nropda) {
        this.nropda = nropda;
    }

    @Basic(optional = false)
    @Column(name = "TPOPDA", nullable = false, length = 3)
    public String getTpopda() {
        return tpopda;
    }

    public void setTpopda(String tpopda) {
        this.tpopda = tpopda;
    }

    @Basic(optional = false)
    @Column(name = "BGNPDA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBgnpda() {
        return bgnpda;
    }

    public void setBgnpda(Date bgnpda) {
        this.bgnpda = bgnpda;
    }

    @Basic(optional = false)
    @Column(name = "ENDPDA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndpda() {
        return endpda;
    }

    public void setEndpda(Date endpda) {
        this.endpda = endpda;
    }

    @Basic(optional = false)
    @Column(name = "PRDPDA", nullable = false)
    public BigInteger getPrdpda() {
        return prdpda;
    }

    public void setPrdpda(BigInteger prdpda) {
        this.prdpda = prdpda;
    }

    @Basic(optional = false)
    @Column(name = "SCHPDA", nullable = false, length = 4000)
    public String getSchpda() {
        return schpda;
    }

    public void setSchpda(String schpda) {
        this.schpda = schpda;
    }

    @Basic(optional = false)
    @Column(name = "DATPDA", nullable = false, length = 80)
    public String getDatpda() {
        return datpda;
    }

    public void setDatpda(String datpda) {
        this.datpda = datpda;
    }

    @Basic(optional = false)
    @Column(name = "STDPDA", nullable = false, length = 13)
    public String getStdpda() {
        return stdpda;
    }

    public void setStdpda(String stdpda) {
        this.stdpda = stdpda;
    }

    @JoinColumn(name = "NROPAK", referencedColumnName = "NROPAK", nullable = false)
    @ManyToOne(optional = false)
    public Smapak getNropak() {
        return nropak;
    }

    public void setNropak(Smapak nropak) {
        this.nropak = nropak;
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
