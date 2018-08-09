/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.entity;

import java.io.Serializable;
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

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMAPAK")
@NamedQueries({
    @NamedQuery(name = "Smapak.findAll", query = "SELECT s FROM Smapak s")})
public class Smapak implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nropak;
    private String nrogrp;
    private Smaprf nroprf;
    private Integer kpcpak;
    private Date bgnpak;
    private Date endpak;
    private String tpogrp;
    private Integer rsvpak;
    private Integer prmpak;
    private Integer perpak;
    private Integer pycpak;
    private Integer mtrpak;
    private double pjapak;
    private double pjbpak;
    private double pjcpak;
    private double pjdpak;
    private double pjtpak;
    private double exepak;
    private Integer crdakd;
    private Integer smnpak;
    private Integer clrpak;
    private String tpomat;
    private double hrsprf;
    private String tpohrs;
    private Smapkp nropkp;
    private Smamat nromat;

    public Smapak() {
        this.nroprf = new Smaprf();
        this.nromat = new Smamat();
        this.crdakd = 0;
        this.nropkp = new Smapkp();
        this.pjapak = 0;
        this.pjbpak = 0;
        this.pjcpak = 0;
        this.pjdpak = 0;
        this.smnpak = 0;
        this.nrogrp = "";
        this.kpcpak = 0;
        this.tpogrp = " ";
        this.rsvpak = 0;
        this.prmpak = 0;
        this.perpak = 0;
        this.pycpak = 0;
        this.mtrpak = 0;
        this.clrpak = 0;
        this.tpomat = "MAT";
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROPAK", nullable = false, length = 10)
    public String getNropak() {
        return nropak;
    }

    public void setNropak(String nropak) {
        this.nropak = nropak;
    }

    @Basic(optional = false)
    @Column(name = "CODPAK", nullable = false, length = 2)
    public String getNrogrp() {
        return nrogrp;
    }

    public void setNrogrp(String nrogrp) {
        this.nrogrp = nrogrp;
    }

    @JoinColumn(name = "NROPRF", referencedColumnName = "NROPRF", nullable = false)
    @ManyToOne(optional = false)
    public Smaprf getNroprf() {
        return nroprf;
    }

    public void setNroprf(Smaprf nroprf) {
        this.nroprf = nroprf;
    }

    @Basic(optional = false)
    @Column(name = "KPCPAK", nullable = false)
    public Integer getKpcpak() {
        return kpcpak;
    }

    public void setKpcpak(Integer kpcpak) {
        this.kpcpak = kpcpak;
    }

    @Basic(optional = false)
    @Column(name = "BGNPAK", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBgnpak() {
        return bgnpak;
    }

    public void setBgnpak(Date bgnpak) {
        this.bgnpak = bgnpak;
    }

    @Basic(optional = false)
    @Column(name = "ENDPAK", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndpak() {
        return endpak;
    }

    public void setEndpak(Date endpak) {
        this.endpak = endpak;
    }

    @Basic(optional = false)
    @Column(name = "TPOPAK", nullable = false, length = 10)
    public String getTpogrp() {
        return tpogrp;
    }

    public void setTpogrp(String tpogrp) {
        this.tpogrp = tpogrp;
    }

    @Basic(optional = false)
    @Column(name = "RSVPAK", nullable = false)
    public Integer getRsvpak() {
        return rsvpak;
    }

    public void setRsvpak(Integer rsvpak) {
        this.rsvpak = rsvpak;
    }

    @Basic(optional = false)
    @Column(name = "PRMPAK", nullable = false)
    public Integer getPrmpak() {
        return prmpak;
    }

    public void setPrmpak(Integer prmpak) {
        this.prmpak = prmpak;
    }

    @Basic(optional = false)
    @Column(name = "PERPAK", nullable = false)
    public Integer getPerpak() {
        return perpak;
    }

    public void setPerpak(Integer perpak) {
        this.perpak = perpak;
    }

    @Basic(optional = false)
    @Column(name = "PYCPAK", nullable = false)
    public Integer getPycpak() {
        return pycpak;
    }

    public void setPycpak(Integer pycpak) {
        this.pycpak = pycpak;
    }

    @Basic(optional = false)
    @Column(name = "MTRPAK", nullable = false)
    public Integer getMtrpak() {
        return mtrpak;
    }

    public void setMtrpak(Integer mtrpak) {
        this.mtrpak = mtrpak;
    }

    @Basic(optional = false)
    @Column(name = "PJAPAK", nullable = false)
    public double getPjapak() {
        return pjapak;
    }

    public void setPjapak(double pjapak) {
        this.pjapak = pjapak;
    }

    @Basic(optional = false)
    @Column(name = "PJBPAK", nullable = false)
    public double getPjbpak() {
        return pjbpak;
    }

    public void setPjbpak(double pjbpak) {
        this.pjbpak = pjbpak;
    }

    @Basic(optional = false)
    @Column(name = "PJCPAK", nullable = false)
    public double getPjcpak() {
        return pjcpak;
    }

    public void setPjcpak(double pjcpak) {
        this.pjcpak = pjcpak;
    }

    @Basic(optional = false)
    @Column(name = "PJDPAK", nullable = false)
    public double getPjdpak() {
        return pjdpak;
    }

    public void setPjdpak(double pjdpak) {
        this.pjdpak = pjdpak;
    }

    @Basic(optional = false)
    @Column(name = "PJTPAK", nullable = false)
    public double getPjtpak() {
        return pjtpak;
    }

    public void setPjtpak(double pjtpak) {
        this.pjtpak = pjtpak;
    }

    @Basic(optional = false)
    @Column(name = "EXEPAK", nullable = false)
    public double getExepak() {
        return exepak;
    }

    public void setExepak(double exepak) {
        this.exepak = exepak;
    }

    @Basic(optional = false)
    @Column(name = "CRDAKD", nullable = false)
    public Integer getCrdakd() {
        return crdakd;
    }

    public void setCrdakd(Integer crdakd) {
        this.crdakd = crdakd;
    }

    /*   @Basic(optional = false)
     @Column(name = "HABPAK", nullable = false)
     public Integer getHabpak() {
     return habpak;
     }

     public void setHabpak(Integer habpak) {
     this.habpak = habpak;
     }*/

    /*   @Basic(optional = false)
     @Column(name = "LBLPAK", nullable = false)
     public Integer getLblpak() {
     return lblpak;
     }

     public void setLblpak(Integer lblpak) {
     this.lblpak = lblpak;
     }*/

    /*   @Basic(optional = false)
     @Column(name = "KNPPAK", nullable = false)
     public Integer getKnppak() {
     return knppak;
     }

     public void setKnppak(Integer knppak) {
     this.knppak = knppak;
     }*/

    /* @Basic(optional = false)
     @Column(name = "TEOPAK", nullable = false)
     public Integer getTeopak() {
     return teopak;
     }

     public void setTeopak(Integer teopak) {
     this.teopak = teopak;
     }*/

    /*  @Basic(optional = false)
     @Column(name = "PRAPAK", nullable = false)
     public Integer getPrapak() {
     return prapak;
     }

     public void setPrapak(Integer prapak) {
     this.prapak = prapak;
     }*/

    /*  @Basic(optional = false)
     @Column(name = "INVPAK", nullable = false)
     public Integer getInvpak() {
     return invpak;
     }

     public void setInvpak(Integer invpak) {
     this.invpak = invpak;
     }*/
    @Basic(optional = false)
    @Column(name = "SMNPAK", nullable = false)
    public Integer getSmnpak() {
        return smnpak;
    }

    public void setSmnpak(Integer smnpak) {
        this.smnpak = smnpak;
    }

    @Basic(optional = false)
    @Column(name = "CLRPAK", nullable = false)
    public Integer getClrpak() {
        return clrpak;
    }

    public void setClrpak(Integer clrpak) {
        this.clrpak = clrpak;
    }

    /*    @Basic(optional = false)
     @Column(name = "NGRMAT", nullable = false)
     public Integer getNgrmat() {
     return ngrmat;
     }

     public void setNgrmat(Integer ngrmat) {
     this.ngrmat = ngrmat;
     }*/

    /*   @Basic(optional = false)
     @Column(name = "MINPAK", nullable = false)
     public Integer getMinpak() {
     return minpak;
     }

     public void setMinpak(Integer minpak) {
     this.minpak = minpak;
     }*/

    /* @Basic(optional = false)
     @Column(name = "CSTPAK", nullable = false)
     public double getCstpak() {
     return cstpak;
     }

     public void setCstpak(double cstpak) {
     this.cstpak = cstpak;
     }*/
    @Basic(optional = false)
    @Column(name = "TPOMAT", nullable = false, length = 3)
    public String getTpomat() {
        return tpomat;
    }

    public void setTpomat(String tpomat) {
        this.tpomat = tpomat;
    }

    @Basic(optional = false)
    @Column(name = "HRSPRF", nullable = false)
    public double getHrsprf() {
        return hrsprf;
    }

    public void setHrsprf(double hrsprf) {
        this.hrsprf = hrsprf;
    }

    @Basic(optional = false)
    @Column(name = "TPOHRS", nullable = false, length = 15)
    public String getTpohrs() {
        return tpohrs;
    }

    public void setTpohrs(String tpohrs) {
        this.tpohrs = tpohrs;
    }

    @JoinColumn(name = "NROPKP", referencedColumnName = "NROPKP", nullable = false)
    @ManyToOne(optional = false)
    public Smapkp getNropkp() {
        return nropkp;
    }

    public void setNropkp(Smapkp nropkp) {
        this.nropkp = nropkp;
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
