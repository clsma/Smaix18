/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author clsma
 */
@Entity
@Table(name = "smamat")
@NamedQueries({
    @NamedQuery(name = "Smamat.findAll", query = "SELECT s FROM Smamat s")})
public class Smamat implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nromat;
    private String codmat;
    private String nommat;
    private String npqmat;
    private int crdmat;
    private double pjamat;
    private double pjbmat;
    private double pjcmat;
    private double pjdmat;
    private int teomat;
    private int pramat;
    private int invmat;
    private String tipaul;
    private String tpomat;
    private String klcmat;
    private int ntsmat;
    private int habmat;
    private int lblmat;
    private int knpmat;
    private int intmat;
    private int smnmat;
    private int rtcmat;
    private double vtrmat;
    private String clrmat;
    private int ngrmat;
    private String codmdl;
    private Smaare nroare;
    private int typmat;
    private int mcrmat;

    public Smamat() {
        nommat = "";
    }

    public Smamat(String nromat) {
        this.nromat = nromat;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "nromat", nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = {
        @Parameter(name = "sequence", value = "seqmat")})
    public String getNromat() {
        return nromat;
    }

    public void setNromat(String nromat) {
        this.nromat = nromat;
    }

    @Basic(optional = false)
    @Column(name = "codmat", nullable = false, length = 7)
    public String getCodmat() {
        return codmat;
    }

    public void setCodmat(String codmat) {
        this.codmat = codmat;
    }

    @Basic(optional = false)
    @Column(name = "nommat", nullable = false, length = 100)
    public String getNommat() {
        return nommat;
    }

    public void setNommat(String nommat) {
        this.nommat = nommat;
    }

    @Basic(optional = false)
    @Column(name = "npqmat", nullable = false, length = 15)
    public String getNpqmat() {
        return npqmat;
    }

    public void setNpqmat(String npqmat) {
        this.npqmat = npqmat;
    }

    @Basic(optional = false)
    @Column(name = "crdmat", nullable = false)
    public int getCrdmat() {
        return crdmat;
    }

    public void setCrdmat(int crdmat) {
        this.crdmat = crdmat;
    }

    @Basic(optional = false)
    @Column(name = "pjamat", nullable = false)
    public double getPjamat() {
        return pjamat;
    }

    public void setPjamat(double pjamat) {
        this.pjamat = pjamat;
    }

    @Basic(optional = false)
    @Column(name = "pjbmat", nullable = false)
    public double getPjbmat() {
        return pjbmat;
    }

    public void setPjbmat(double pjbmat) {
        this.pjbmat = pjbmat;
    }

    @Basic(optional = false)
    @Column(name = "pjcmat", nullable = false)
    public double getPjcmat() {
        return pjcmat;
    }

    public void setPjcmat(double pjcmat) {
        this.pjcmat = pjcmat;
    }

    @Basic(optional = false)
    @Column(name = "pjdmat", nullable = false)
    public double getPjdmat() {
        return pjdmat;
    }

    public void setPjdmat(double pjdmat) {
        this.pjdmat = pjdmat;
    }

    @Basic(optional = false)
    @Column(name = "teomat", nullable = false)
    public int getTeomat() {
        return teomat;
    }

    public void setTeomat(int teomat) {
        this.teomat = teomat;
    }

    @Basic(optional = false)
    @Column(name = "pramat", nullable = false)
    public int getPramat() {
        return pramat;
    }

    public void setPramat(int pramat) {
        this.pramat = pramat;
    }

    @Basic(optional = false)
    @Column(name = "invmat", nullable = false)
    public int getInvmat() {
        return invmat;
    }

    public void setInvmat(int invmat) {
        this.invmat = invmat;
    }

    @Basic(optional = false)
    @Column(name = "tipaul", nullable = false, length = 30)
    public String getTipaul() {
        return tipaul;
    }

    public void setTipaul(String tipaul) {
        this.tipaul = tipaul;
    }

    @Basic(optional = false)
    @Column(name = "tpomat", nullable = false, length = 3)
    public String getTpomat() {
        return tpomat;
    }

    public void setTpomat(String tpomat) {
        this.tpomat = tpomat;
    }

    @Basic(optional = false)
    @Column(name = "klcmat", nullable = false, length = 12)
    public String getKlcmat() {
        return klcmat;
    }

    public void setKlcmat(String klcmat) {
        this.klcmat = klcmat;
    }

    @Basic(optional = false)
    @Column(name = "ntsmat", nullable = false)
    public int getNtsmat() {
        return ntsmat;
    }

    public void setNtsmat(int ntsmat) {
        this.ntsmat = ntsmat;
    }

    @Basic(optional = false)
    @Column(name = "habmat", nullable = false)
    public int getHabmat() {
        return habmat;
    }

    public void setHabmat(int habmat) {
        this.habmat = habmat;
    }

    @Basic(optional = false)
    @Column(name = "lblmat", nullable = false)
    public int getLblmat() {
        return lblmat;
    }

    public void setLblmat(int lblmat) {
        this.lblmat = lblmat;
    }

    @Basic(optional = false)
    @Column(name = "knpmat", nullable = false)
    public int getKnpmat() {
        return knpmat;
    }

    public void setKnpmat(int knpmat) {
        this.knpmat = knpmat;
    }

    @Basic(optional = false)
    @Column(name = "intmat", nullable = false)
    public int getIntmat() {
        return intmat;
    }

    public void setIntmat(int intmat) {
        this.intmat = intmat;
    }

    @Basic(optional = false)
    @Column(name = "smnmat", nullable = false)
    public int getSmnmat() {
        return smnmat;
    }

    public void setSmnmat(int smnmat) {
        this.smnmat = smnmat;
    }

    @Basic(optional = false)
    @Column(name = "rtcmat", nullable = false)
    public int getRtcmat() {
        return rtcmat;
    }

    public void setRtcmat(int rtcmat) {
        this.rtcmat = rtcmat;
    }

    @Basic(optional = false)
    @Column(name = "vtrmat", nullable = false)
    public double getVtrmat() {
        return vtrmat;
    }

    public void setVtrmat(double vtrmat) {
        this.vtrmat = vtrmat;
    }

    @Basic(optional = false)
    @Column(name = "clrmat", nullable = false)
    public String getClrmat() {
        return clrmat;
    }

    public void setClrmat(String clrmat) {
        this.clrmat = clrmat;
    }

    @Basic(optional = false)
    @Column(name = "ngrmat", nullable = false)
    public int getNgrmat() {
        return ngrmat;
    }

    public void setNgrmat(int ngrmat) {
        this.ngrmat = ngrmat;
    }

    @Basic(optional = false)
    @Column(name = "codmdl", nullable = false, length = 7)
    public String getCodmdl() {
        return codmdl;
    }

    public void setCodmdl(String codmdl) {
        this.codmdl = codmdl;
    }

    @JoinColumn(name = "nroare", referencedColumnName = "nroare", nullable = false)
    @ManyToOne(optional = false)
    public Smaare getNroare() {
        return nroare;
    }

    public void setNroare(Smaare nroare) {
        this.nroare = nroare;
    }

    @Basic(optional = false)
    @Column(name = "typmat", nullable = false)
    public int getTypmat() {
        return typmat;
    }

    public void setTypmat(int typmat) {
        this.typmat = typmat;
    }

    @Basic(optional = false)
    @Column(name = "mcrmat", nullable = false)
    public int getMcrmat() {
        return mcrmat;
    }

    public void setMcrmat(int mcrmat) {
        this.mcrmat = mcrmat;
    }

}
