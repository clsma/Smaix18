/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAPSD", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODCIA", "IDEPGM", "NROPSM"})})
@NamedQueries({
    @NamedQuery(name = "Smapsd.findAll", query = "SELECT s FROM Smapsd s")})
public class Smapsd implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nropsd;
    private String nropsm;
    private String nompsd;
    private String tpopsd;
    private String rslpsd;
    private String tponrm;
    private String nronrm;
    private Date fchnrm;
    private String codorg;
    private String stdpsd;
    private Smapgm idepgm;
    private Smacia codcia;

    public Smapsd() {
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROPSD", nullable = false, length = 6)
    public String getNropsd() {
        return nropsd;
    }

    public void setNropsd(String nropsd) {
        this.nropsd = nropsd;
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
    @Column(name = "NOMPSD", nullable = false, length = 128)
    public String getNompsd() {
        return nompsd;
    }

    public void setNompsd(String nompsd) {
        this.nompsd = nompsd;
    }

    @Basic(optional = false)
    @Column(name = "TPOPSD", nullable = false, length = 3)
    public String getTpopsd() {
        return tpopsd;
    }

    public void setTpopsd(String tpopsd) {
        this.tpopsd = tpopsd;
    }

    @Column(name = "RSLPSD", length = 35)
    public String getRslpsd() {
        return rslpsd;
    }

    public void setRslpsd(String rslpsd) {
        this.rslpsd = rslpsd;
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

    @Column(name = "CODORG", length = 3)
    public String getCodorg() {
        return codorg;
    }

    public void setCodorg(String codorg) {
        this.codorg = codorg;
    }

    @Basic(optional = false)
    @Column(name = "STDPSD", nullable = false, length = 12)
    public String getStdpsd() {
        return stdpsd;
    }

    public void setStdpsd(String stdpsd) {
        this.stdpsd = stdpsd;
    }

    @JoinColumn(name = "IDEPGM", referencedColumnName = "IDEPGM", nullable = false)
    @ManyToOne(optional = false)
    public Smapgm getIdepgm() {
        return idepgm;
    }

    public void setIdepgm(Smapgm idepgm) {
        this.idepgm = idepgm;
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
