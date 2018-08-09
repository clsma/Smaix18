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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMACRS", catalog = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCRS"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Smacrs.findAll", query = "SELECT s FROM Smacrs s")})
public class Smacrs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "AGNPRS", nullable = false, length = 4)
    private String agnprs;
    @Basic(optional = false)
    @Column(name = "PRDPRS", nullable = false, length = 1)
    private String prdprs;
    @Basic(optional = false)
    @Column(name = "NROPSM", nullable = false, length = 4)
    private String nropsm;
    @Basic(optional = false)
    @Column(name = "SMTPSM", nullable = false, length = 2)
    private String smtpsm;
    @Basic(optional = false)
    @Column(name = "NROCRS", nullable = false, length = 2)
    private String nrocrs;
    @Basic(optional = false)
    @Column(name = "CODCRS", nullable = false, length = 10)
    private String codcrs;
    @Basic(optional = false)
    @Column(name = "PRMCRS", nullable = false)
    private BigInteger prmcrs;
    @Basic(optional = false)
    @Column(name = "MDFCRS", nullable = false, length = 2)
    private String mdfcrs;
    @Basic(optional = false)
    @Column(name = "BGNCRS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bgncrs;
    @Basic(optional = false)
    @Column(name = "ENDCRS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endcrs;
    @Basic(optional = false)
    @Column(name = "MTRCRS", nullable = false)
    private BigInteger mtrcrs;
    @Basic(optional = false)
    @Column(name = "KPCCRS", nullable = false)
    private BigInteger kpccrs;
    @Basic(optional = false)
    @Column(name = "TPOCRS", nullable = false, length = 10)
    private String tpocrs;
    @Column(name = "CODHRF", length = 4)
    private String codhrf;
    @Column(name = "SMNCRS")
    private BigInteger smncrs;
    @Basic(optional = false)
    @Column(name = "NOMCRS", nullable = false, length = 100)
    private String nomcrs;
    @Basic(optional = false)
    @Column(name = "NROSDE", nullable = false, length = 3)
    private String nrosde;
    @Basic(optional = false)
    @Column(name = "CODEDF", nullable = false, length = 1)
    private String codedf;
    @Basic(optional = false)
    @Column(name = "NROAUL", nullable = false, length = 3)
    private String nroaul;
    @Basic(optional = false)
    @Column(name = "CODKLS", nullable = false, length = 6)
    private String codkls;
    @Basic(optional = false)
    @Column(name = "FCHCRS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchcrs;
    @Basic(optional = false)
    @Column(name = "STDCRS", nullable = false, length = 20)
    private String stdcrs;
    @JoinColumn(name = "IDESMT", referencedColumnName = "IDESMT", nullable = false)
    @ManyToOne(optional = false)
    private Smasmt idesmt;
    @Column(name = "NROPSD", length = 6)
    private String nropsd;
    @Id
    @Basic(optional = false)
    @Column(name = "IDECRS", nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    private String idecrs;
    @Basic(optional = false)
    @Column(name = "SQLBKR", nullable = false, length = 4000)
    private String sqlbkr;
    @JoinColumn(name = "CODSMT", referencedColumnName = "CODSMT", nullable = false)
    @ManyToOne(optional = false)
    private Smasmt codsmt;
    @JoinColumn(name = "IDEPGM", referencedColumnName = "IDEPGM", nullable = false)
    @ManyToOne(optional = false)
    private Smapgm idepgm;
    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    private Smacia codcia;

    public Smacrs() {
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

    public String getNropsm() {
        return nropsm;
    }

    public void setNropsm(String nropsm) {
        this.nropsm = nropsm;
    }

    public String getSmtpsm() {
        return smtpsm;
    }

    public void setSmtpsm(String smtpsm) {
        this.smtpsm = smtpsm;
    }

    public String getNrocrs() {
        return nrocrs;
    }

    public void setNrocrs(String nrocrs) {
        this.nrocrs = nrocrs;
    }

    public String getCodcrs() {
        return codcrs;
    }

    public void setCodcrs(String codcrs) {
        this.codcrs = codcrs;
    }

    public BigInteger getPrmcrs() {
        return prmcrs;
    }

    public void setPrmcrs(BigInteger prmcrs) {
        this.prmcrs = prmcrs;
    }

    public String getMdfcrs() {
        return mdfcrs;
    }

    public void setMdfcrs(String mdfcrs) {
        this.mdfcrs = mdfcrs;
    }

    public Date getBgncrs() {
        return bgncrs;
    }

    public void setBgncrs(Date bgncrs) {
        this.bgncrs = bgncrs;
    }

    public Date getEndcrs() {
        return endcrs;
    }

    public void setEndcrs(Date endcrs) {
        this.endcrs = endcrs;
    }

    public BigInteger getMtrcrs() {
        return mtrcrs;
    }

    public void setMtrcrs(BigInteger mtrcrs) {
        this.mtrcrs = mtrcrs;
    }

    public BigInteger getKpccrs() {
        return kpccrs;
    }

    public void setKpccrs(BigInteger kpccrs) {
        this.kpccrs = kpccrs;
    }

    public String getTpocrs() {
        return tpocrs;
    }

    public void setTpocrs(String tpocrs) {
        this.tpocrs = tpocrs;
    }

    public String getCodhrf() {
        return codhrf;
    }

    public void setCodhrf(String codhrf) {
        this.codhrf = codhrf;
    }

    public BigInteger getSmncrs() {
        return smncrs;
    }

    public void setSmncrs(BigInteger smncrs) {
        this.smncrs = smncrs;
    }

    public String getNomcrs() {
        return nomcrs;
    }

    public void setNomcrs(String nomcrs) {
        this.nomcrs = nomcrs;
    }

    public String getNrosde() {
        return nrosde;
    }

    public void setNrosde(String nrosde) {
        this.nrosde = nrosde;
    }

    public String getCodedf() {
        return codedf;
    }

    public void setCodedf(String codedf) {
        this.codedf = codedf;
    }

    public String getNroaul() {
        return nroaul;
    }

    public void setNroaul(String nroaul) {
        this.nroaul = nroaul;
    }

    public String getCodkls() {
        return codkls;
    }

    public void setCodkls(String codkls) {
        this.codkls = codkls;
    }

    public Date getFchcrs() {
        return fchcrs;
    }

    public void setFchcrs(Date fchcrs) {
        this.fchcrs = fchcrs;
    }

    public String getStdcrs() {
        return stdcrs;
    }

    public void setStdcrs(String stdcrs) {
        this.stdcrs = stdcrs;
    }

    public String getNropsd() {
        return nropsd;
    }

    public void setNropsd(String nropsd) {
        this.nropsd = nropsd;
    }

    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = {
        @Parameter(name = "sequence", value = "SEQCRS")})
    public String getIdecrs() {
        return idecrs;
    }

    public void setIdecrs(String idecrs) {
        this.idecrs = idecrs;
    }

    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public Smasmt getCodsmt() {
        return codsmt;
    }

    public void setCodsmt(Smasmt codsmt) {
        this.codsmt = codsmt;
    }

    public Smapgm getIdepgm() {
        return idepgm;
    }

    public void setIdepgm(Smapgm idepgm) {
        this.idepgm = idepgm;
    }

    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }
}
