/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "SMAPSM")
@NamedQueries({
    @NamedQuery(name = "Smapsm.findAll", query = "SELECT s FROM Smapsm s")})
public class Smapsm implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codpsm;
    private String smtpsm;
    private String cscpsm;
    private String tipaul;
    private String stdakd;
    private short teopsm;
    private short prapsm;
    private short invpsm;
    private short crdpsm;
    private short jrqpsm;
    private short ntspsm;
    private short habpsm;
    private short lblpsm;
    private short knppsm;
    private short smnpsm;
    private short rtcpsm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private BigDecimal minpsm;
    private BigDecimal maxpsm;
    private BigDecimal apppsm;
    private String tpopsm;
    private Short ngrpsm;
    private BigDecimal pjapsm;
    private BigDecimal pjbpsm;
    private BigDecimal pjcpsm;
    private BigDecimal pjdpsm;
    private Smapsd nropsd;
    private Smamat nromat;

    public Smapsm() {
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CODPSM", nullable = false, length = 10)
    public String getCodpsm() {
        return codpsm;
    }

    public void setCodpsm(String codpsm) {
        this.codpsm = codpsm;
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
    @Column(name = "CSCPSM", nullable = false, length = 2)
    public String getCscpsm() {
        return cscpsm;
    }

    public void setCscpsm(String cscpsm) {
        this.cscpsm = cscpsm;
    }

    @Basic(optional = false)
    @Column(name = "TIPAUL", nullable = false, length = 11)
    public String getTipaul() {
        return tipaul;
    }

    public void setTipaul(String tipaul) {
        this.tipaul = tipaul;
    }

    @Basic(optional = false)
    @Column(name = "STDAKD", nullable = false, length = 15)
    public String getStdakd() {
        return stdakd;
    }

    public void setStdakd(String stdakd) {
        this.stdakd = stdakd;
    }

    @Basic(optional = false)
    @Column(name = "TEOPSM", nullable = false)
    public short getTeopsm() {
        return teopsm;
    }

    public void setTeopsm(short teopsm) {
        this.teopsm = teopsm;
    }

    @Basic(optional = false)
    @Column(name = "PRAPSM", nullable = false)
    public short getPrapsm() {
        return prapsm;
    }

    public void setPrapsm(short prapsm) {
        this.prapsm = prapsm;
    }

    @Basic(optional = false)
    @Column(name = "INVPSM", nullable = false)
    public short getInvpsm() {
        return invpsm;
    }

    public void setInvpsm(short invpsm) {
        this.invpsm = invpsm;
    }

    @Basic(optional = false)
    @Column(name = "CRDPSM", nullable = false)
    public short getCrdpsm() {
        return crdpsm;
    }

    public void setCrdpsm(short crdpsm) {
        this.crdpsm = crdpsm;
    }

    @Basic(optional = false)
    @Column(name = "JRQPSM", nullable = false)
    public short getJrqpsm() {
        return jrqpsm;
    }

    public void setJrqpsm(short jrqpsm) {
        this.jrqpsm = jrqpsm;
    }

    @Basic(optional = false)
    @Column(name = "NTSPSM", nullable = false)
    public short getNtspsm() {
        return ntspsm;
    }

    public void setNtspsm(short ntspsm) {
        this.ntspsm = ntspsm;
    }

    @Basic(optional = false)
    @Column(name = "HABPSM", nullable = false)
    public short getHabpsm() {
        return habpsm;
    }

    public void setHabpsm(short habpsm) {
        this.habpsm = habpsm;
    }

    @Basic(optional = false)
    @Column(name = "LBLPSM", nullable = false)
    public short getLblpsm() {
        return lblpsm;
    }

    public void setLblpsm(short lblpsm) {
        this.lblpsm = lblpsm;
    }

    @Basic(optional = false)
    @Column(name = "KNPPSM", nullable = false)
    public short getKnppsm() {
        return knppsm;
    }

    public void setKnppsm(short knppsm) {
        this.knppsm = knppsm;
    }

    @Basic(optional = false)
    @Column(name = "SMNPSM", nullable = false)
    public short getSmnpsm() {
        return smnpsm;
    }

    public void setSmnpsm(short smnpsm) {
        this.smnpsm = smnpsm;
    }

    @Basic(optional = false)
    @Column(name = "RTCPSM", nullable = false)
    public short getRtcpsm() {
        return rtcpsm;
    }

    public void setRtcpsm(short rtcpsm) {
        this.rtcpsm = rtcpsm;
    }

    @Basic(optional = false)
    @Column(name = "MINPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getMinpsm() {
        return minpsm;
    }

    public void setMinpsm(BigDecimal minpsm) {
        this.minpsm = minpsm;
    }

    @Basic(optional = false)
    @Column(name = "MAXPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getMaxpsm() {
        return maxpsm;
    }

    public void setMaxpsm(BigDecimal maxpsm) {
        this.maxpsm = maxpsm;
    }

    @Basic(optional = false)
    @Column(name = "APPPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getApppsm() {
        return apppsm;
    }

    public void setApppsm(BigDecimal apppsm) {
        this.apppsm = apppsm;
    }

    @Basic(optional = false)
    @Column(name = "TPOPSM", nullable = false, length = 3)
    public String getTpopsm() {
        return tpopsm;
    }

    public void setTpopsm(String tpopsm) {
        this.tpopsm = tpopsm;
    }

    @Column(name = "NGRPSM")
    public Short getNgrpsm() {
        return ngrpsm;
    }

    public void setNgrpsm(Short ngrpsm) {
        this.ngrpsm = ngrpsm;
    }

    @Basic(optional = false)
    @Column(name = "PJAPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getPjapsm() {
        return pjapsm;
    }

    public void setPjapsm(BigDecimal pjapsm) {
        this.pjapsm = pjapsm;
    }

    @Basic(optional = false)
    @Column(name = "PJBPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getPjbpsm() {
        return pjbpsm;
    }

    public void setPjbpsm(BigDecimal pjbpsm) {
        this.pjbpsm = pjbpsm;
    }

    @Basic(optional = false)
    @Column(name = "PJCPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getPjcpsm() {
        return pjcpsm;
    }

    public void setPjcpsm(BigDecimal pjcpsm) {
        this.pjcpsm = pjcpsm;
    }

    @Basic(optional = false)
    @Column(name = "PJDPSM", nullable = false, precision = 7, scale = 3)
    public BigDecimal getPjdpsm() {
        return pjdpsm;
    }

    public void setPjdpsm(BigDecimal pjdpsm) {
        this.pjdpsm = pjdpsm;
    }

    @JoinColumn(name = "NROPSD", referencedColumnName = "NROPSD", nullable = false)
    @ManyToOne(optional = false)
    public Smapsd getNropsd() {
        return nropsd;
    }

    public void setNropsd(Smapsd nropsd) {
        this.nropsd = nropsd;
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
