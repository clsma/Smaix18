/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author efrain
 */
@Entity
@Table(name = "SMAPGM", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCIA", "NROPGM"})})
@NamedQueries({
    @NamedQuery(name = "Smapgm.findAll", query = "SELECT s FROM Smapgm s")})
public class Smapgm implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idepgm;
    private String codcia;
    private String nropgm;
    private String codpgm;
    private String nompgm;
    private String stdpgm;
    private String npqpgm;
    private String nropsm;
    private String ideprg;
    private String jndpgm;
    private String telpgm;
    private String extpgm;
    private Date fchpgm;
    private BigInteger smtpgm;
    private String krdprs;
    private String tpopgm;
    private String strprs;
    private String rslpgm;
    private String nrocst;
    private String codrgs;
    private String tponrm;
    private String nronrm;
    private Date fchnrm;
    private String codorg;
    private String stgpgm;
    private String prdpgm;
    private BigInteger kpcpgm;
    private BigInteger crdakd;
    private String snepgm;
    private String nrosic;
    private Smascn idescn;

    public Smapgm() {
    }

    public Smapgm(String idepgm) {
        this.idepgm = idepgm;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "IDEPGM", nullable = false, length = 6)
    public String getIdepgm() {
        return idepgm;
    }

    public void setIdepgm(String idepgm) {
        this.idepgm = idepgm;
    }

    @Basic(optional = false)
    @Column(name = "CODCIA", nullable = false, length = 3)
    public String getCodcia() {
        return codcia;
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    @Basic(optional = false)
    @Column(name = "NROPGM", nullable = false, length = 3)
    public String getNropgm() {
        return nropgm;
    }

    public void setNropgm(String nropgm) {
        this.nropgm = nropgm;
    }

    @Basic(optional = false)
    @Column(name = "CODPGM", nullable = false, length = 3)
    public String getCodpgm() {
        return codpgm;
    }

    public void setCodpgm(String codpgm) {
        this.codpgm = codpgm;
    }

    @Basic(optional = false)
    @Column(name = "NOMPGM", nullable = false, length = 100)
    public String getNompgm() {
        return nompgm;
    }

    public void setNompgm(String nompgm) {
        this.nompgm = nompgm;
    }

    @Basic(optional = false)
    @Column(name = "STDPGM", nullable = false, length = 13)
    public String getStdpgm() {
        return stdpgm;
    }

    public void setStdpgm(String stdpgm) {
        this.stdpgm = stdpgm;
    }

    @Basic(optional = false)
    @Column(name = "NPQPGM", nullable = false, length = 100)
    public String getNpqpgm() {
        return npqpgm;
    }

    public void setNpqpgm(String npqpgm) {
        this.npqpgm = npqpgm;
    }

    @Column(name = "NROPSM", length = 4)
    public String getNropsm() {
        return nropsm;
    }

    public void setNropsm(String nropsm) {
        this.nropsm = nropsm;
    }

    @Basic(optional = false)
    @Column(name = "IDEPRG", nullable = false, length = 5)
    public String getIdeprg() {
        return ideprg;
    }

    public void setIdeprg(String ideprg) {
        this.ideprg = ideprg;
    }

    @Basic(optional = false)
    @Column(name = "JNDPGM", nullable = false, length = 1)
    public String getJndpgm() {
        return jndpgm;
    }

    public void setJndpgm(String jndpgm) {
        this.jndpgm = jndpgm;
    }

    @Column(name = "TELPGM", length = 30)
    public String getTelpgm() {
        return telpgm;
    }

    public void setTelpgm(String telpgm) {
        this.telpgm = telpgm;
    }

    @Column(name = "EXTPGM", length = 10)
    public String getExtpgm() {
        return extpgm;
    }

    public void setExtpgm(String extpgm) {
        this.extpgm = extpgm;
    }

    @Column(name = "FCHPGM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFchpgm() {
        return fchpgm;
    }

    public void setFchpgm(Date fchpgm) {
        this.fchpgm = fchpgm;
    }

    @Column(name = "SMTPGM")
    public BigInteger getSmtpgm() {
        return smtpgm;
    }

    public void setSmtpgm(BigInteger smtpgm) {
        this.smtpgm = smtpgm;
    }

    @Column(name = "KRDPRS", length = 10)
    public String getKrdprs() {
        return krdprs;
    }

    public void setKrdprs(String krdprs) {
        this.krdprs = krdprs;
    }

    @Column(name = "TPOPGM", length = 15)
    public String getTpopgm() {
        return tpopgm;
    }

    public void setTpopgm(String tpopgm) {
        this.tpopgm = tpopgm;
    }

    @Column(name = "STRPRS", length = 10)
    public String getStrprs() {
        return strprs;
    }

    public void setStrprs(String strprs) {
        this.strprs = strprs;
    }

    @Column(name = "RSLPGM", length = 35)
    public String getRslpgm() {
        return rslpgm;
    }

    public void setRslpgm(String rslpgm) {
        this.rslpgm = rslpgm;
    }

    @Column(name = "NROCST", length = 7)
    public String getNrocst() {
        return nrocst;
    }

    public void setNrocst(String nrocst) {
        this.nrocst = nrocst;
    }

    @Column(name = "CODRGS", length = 6)
    public String getCodrgs() {
        return codrgs;
    }

    public void setCodrgs(String codrgs) {
        this.codrgs = codrgs;
    }

    @Column(name = "TPONRM", length = 3)
    public String getTponrm() {
        return tponrm;
    }

    public void setTponrm(String tponrm) {
        this.tponrm = tponrm;
    }

    @Column(name = "NRONRM", length = 10)
    public String getNronrm() {
        return nronrm;
    }

    public void setNronrm(String nronrm) {
        this.nronrm = nronrm;
    }

    @Column(name = "FCHNRM")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFchnrm() {
        return fchnrm;
    }

    public void setFchnrm(Date fchnrm) {
        this.fchnrm = fchnrm;
    }

    @Column(name = "CODORG", length = 10)
    public String getCodorg() {
        return codorg;
    }

    public void setCodorg(String codorg) {
        this.codorg = codorg;
    }

    @Column(name = "STGPGM", length = 15)
    public String getStgpgm() {
        return stgpgm;
    }

    public void setStgpgm(String stgpgm) {
        this.stgpgm = stgpgm;
    }

    @Column(name = "PRDPGM", length = 3)
    public String getPrdpgm() {
        return prdpgm;
    }

    public void setPrdpgm(String prdpgm) {
        this.prdpgm = prdpgm;
    }

    @Column(name = "KPCPGM")
    public BigInteger getKpcpgm() {
        return kpcpgm;
    }

    public void setKpcpgm(BigInteger kpcpgm) {
        this.kpcpgm = kpcpgm;
    }

    @Column(name = "CRDAKD")
    public BigInteger getCrdakd() {
        return crdakd;
    }

    public void setCrdakd(BigInteger crdakd) {
        this.crdakd = crdakd;
    }

    @Column(name = "SNEPGM", length = 6)
    public String getSnepgm() {
        return snepgm;
    }

    public void setSnepgm(String snepgm) {
        this.snepgm = snepgm;
    }

    @Column(name = "NROSIC", length = 10)
    public String getNrosic() {
        return nrosic;
    }

    public void setNrosic(String nrosic) {
        this.nrosic = nrosic;
    }

    @JoinColumn(name = "IDESCN", referencedColumnName = "IDESCN")
    @ManyToOne
    public Smascn getIdescn() {
        return idescn;
    }

    public void setIdescn(Smascn idescn) {
        this.idescn = idescn;
    }
}
