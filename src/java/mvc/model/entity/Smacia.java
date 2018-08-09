package mvc.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author clsma
 */
@Entity
@Table(name = "smacia")
@NamedQueries({
    @NamedQuery(name = "Smacia.findAll", query = "SELECT s FROM Smacia s")})
public class Smacia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codcia", nullable = false, length = 3)
    private String codcia;
    @Basic(optional = false)
    @Column(name = "nomcia", nullable = false, length = 75)
    private String nomcia;
    @Basic(optional = false)
    @Column(name = "npqcia", nullable = false, length = 20)
    private String npqcia;
    @Basic(optional = false)
    @Column(name = "telcia", nullable = false, length = 40)
    private String telcia;
    @Basic(optional = false)
    @Column(name = "dircia", nullable = false, length = 45)
    private String dircia;
    @Basic(optional = false)
    @Column(name = "faxcia", nullable = false, length = 10)
    private String faxcia;
    @Basic(optional = false)
    @Column(name = "a_acia", nullable = false, length = 10)
    private String aAcia;
    @Basic(optional = false)
    @Column(name = "nitcia", nullable = false, length = 12)
    private String nitcia;
    @Basic(optional = false)
    @Column(name = "rpscia", nullable = false, length = 10)
    private String rpscia;
    @Basic(optional = false)
    @Column(name = "grtcia", nullable = false, length = 10)
    private String grtcia;
    @Basic(optional = false)
    @Column(name = "scrcia", nullable = false, length = 10)
    private String scrcia;
    @Basic(optional = false)
    @Column(name = "fchcia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fchcia;
    @Basic(optional = false)
    @Column(name = "lnccia", nullable = false, length = 20)
    private String lnccia;
    @Basic(optional = false)
    @Column(name = "clicia", nullable = false, length = 10)
    private String clicia;
    @Basic(optional = false)
    @Column(name = "nrocia", nullable = false, length = 135)
    private String nrocia;
    @Basic(optional = false)
    @Column(name = "codprs", nullable = false, length = 10)
    private String codprs;
    @Basic(optional = false)
    @Column(name = "prscia", nullable = false, length = 35)
    private String prscia;
    @Basic(optional = false)
    @Column(name = "rsdcia", nullable = false, length = 50)
    private String rsdcia;
    @Basic(optional = false)
    @Column(name = "emlcia", nullable = false, length = 128)
    private String emlcia;
    @Basic(optional = false)
    @Column(name = "webcia", nullable = false, length = 254)
    private String webcia;
    @Basic(optional = false)
    @Column(name = "codciu", nullable = false, length = 6)
    private String codciu;
    @Basic(optional = false)
    @Column(name = "grncia", nullable = false)
    private int grncia;
    @Basic(optional = false)
    @Column(name = "vgccia", nullable = false)
    private int vgccia;
    @Basic(optional = false)
    @Column(name = "autcia", nullable = false)
    private int autcia;
    @Basic(optional = false)
    @Column(name = "rtacia", nullable = false)
    private int rtacia;
    @Basic(optional = false)
    @Column(name = "icacia", nullable = false, length = 2147483647)
    private String icacia;
    @Basic(optional = false)
    @Column(name = "ivacia", nullable = false)
    private int ivacia;
    @Basic(optional = false)
    @Column(name = "gtacia", nullable = false)
    private int gtacia;
    @Basic(optional = false)
    @Column(name = "stmcia", nullable = false)
    private int stmcia;
    @Basic(optional = false)
    @Column(name = "pswcia", nullable = false, length = 27)
    private String pswcia;
    @Basic(optional = false)
    @Column(name = "stdcia", nullable = false, length = 10)
    private String stdcia;
    @Basic(optional = false)
    @Column(name = "ctdcia", nullable = false, length = 10)
    private String ctdcia;
    @Basic(optional = false)
    @Column(name = "rvscia", nullable = false, length = 10)
    private String rvscia;
    @Basic(optional = false)
    @Column(name = "ctbcia", nullable = false)
    private int ctbcia;
    @Basic(optional = false)
    @Column(name = "rgmcia", nullable = false, length = 3)
    private String rgmcia;
    @Basic(optional = false)
    @Column(name = "rtfcia", nullable = false, length = 3)
    private String rtfcia;
    @Basic(optional = false)
    @Column(name = "tikcia", nullable = false, length = 3)
    private String tikcia;
    @Basic(optional = false)
    @Column(name = "utlpqk", nullable = false, length = 15)
    private String utlpqk;
    @Basic(optional = false)
    @Column(name = "uaqpqk", nullable = false, length = 15)
    private String uaqpqk;
    @Basic(optional = false)
    @Column(name = "pygpqk", nullable = false, length = 15)
    private String pygpqk;
    @Basic(optional = false)
    @Column(name = "kjapqk", nullable = false, length = 15)
    private String kjapqk;
    @Basic(optional = false)
    @Column(name = "ctipqk", nullable = false, length = 15)
    private String ctipqk;
    @Basic(optional = false)
    @Column(name = "cxmpqk", nullable = false, length = 15)
    private String cxmpqk;
       @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcia")
    private List<Smaare> smaareList;
    
    public Smacia() {
    }

    public Smacia(String codcia, String nomcia, String npqcia, String telcia, String dircia, String faxcia, String aAcia, String nitcia, String rpscia, String grtcia, String scrcia, Date fchcia, String lnccia, String clicia, String nrocia, String codprs, String prscia, String rsdcia, String emlcia, String webcia, String codciu, int grncia, int vgccia, int autcia, int rtacia, String icacia, int ivacia, int gtacia, int stmcia, String pswcia, String stdcia, String ctdcia, String rvscia, int ctbcia, String rgmcia, String rtfcia, String tikcia, String utlpqk, String uaqpqk, String pygpqk, String kjapqk, String ctipqk, String cxmpqk) {
        this.codcia = codcia;
        this.nomcia = nomcia;
        this.npqcia = npqcia;
        this.telcia = telcia;
        this.dircia = dircia;
        this.faxcia = faxcia;
        this.aAcia = aAcia;
        this.nitcia = nitcia;
        this.rpscia = rpscia;
        this.grtcia = grtcia;
        this.scrcia = scrcia;
        this.fchcia = fchcia;
        this.lnccia = lnccia;
        this.clicia = clicia;
        this.nrocia = nrocia;
        this.codprs = codprs;
        this.prscia = prscia;
        this.rsdcia = rsdcia;
        this.emlcia = emlcia;
        this.webcia = webcia;
        this.codciu = codciu;
        this.grncia = grncia;
        this.vgccia = vgccia;
        this.autcia = autcia;
        this.rtacia = rtacia;
        this.icacia = icacia;
        this.ivacia = ivacia;
        this.gtacia = gtacia;
        this.stmcia = stmcia;
        this.pswcia = pswcia;
        this.stdcia = stdcia;
        this.ctdcia = ctdcia;
        this.rvscia = rvscia;
        this.ctbcia = ctbcia;
        this.rgmcia = rgmcia;
        this.rtfcia = rtfcia;
        this.tikcia = tikcia;
        this.utlpqk = utlpqk;
        this.uaqpqk = uaqpqk;
        this.pygpqk = pygpqk;
        this.kjapqk = kjapqk;
        this.ctipqk = ctipqk;
        this.cxmpqk = cxmpqk;
       
    }

    public String getCodcia() {
        return codcia;
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    public String getNomcia() {
        return nomcia;
    }

    public void setNomcia(String nomcia) {
        this.nomcia = nomcia;
    }

    public String getNpqcia() {
        return npqcia;
    }

    public void setNpqcia(String npqcia) {
        this.npqcia = npqcia;
    }

    public String getTelcia() {
        return telcia;
    }

    public void setTelcia(String telcia) {
        this.telcia = telcia;
    }

    public String getDircia() {
        return dircia;
    }

    public void setDircia(String dircia) {
        this.dircia = dircia;
    }

    public String getFaxcia() {
        return faxcia;
    }

    public void setFaxcia(String faxcia) {
        this.faxcia = faxcia;
    }

    public String getAAcia() {
        return aAcia;
    }

    public void setAAcia(String aAcia) {
        this.aAcia = aAcia;
    }

    public String getNitcia() {
        return nitcia;
    }

    public void setNitcia(String nitcia) {
        this.nitcia = nitcia;
    }

    public String getRpscia() {
        return rpscia;
    }

    public void setRpscia(String rpscia) {
        this.rpscia = rpscia;
    }

    public String getGrtcia() {
        return grtcia;
    }

    public void setGrtcia(String grtcia) {
        this.grtcia = grtcia;
    }

    public String getScrcia() {
        return scrcia;
    }

    public void setScrcia(String scrcia) {
        this.scrcia = scrcia;
    }

    public Date getFchcia() {
        return fchcia;
    }

    public void setFchcia(Date fchcia) {
        this.fchcia = fchcia;
    }

    public String getLnccia() {
        return lnccia;
    }

    public void setLnccia(String lnccia) {
        this.lnccia = lnccia;
    }

    public String getClicia() {
        return clicia;
    }

    public void setClicia(String clicia) {
        this.clicia = clicia;
    }

    public String getNrocia() {
        return nrocia;
    }

    public void setNrocia(String nrocia) {
        this.nrocia = nrocia;
    }

    public String getCodprs() {
        return codprs;
    }

    public void setCodprs(String codprs) {
        this.codprs = codprs;
    }

    public String getPrscia() {
        return prscia;
    }

    public void setPrscia(String prscia) {
        this.prscia = prscia;
    }

    public String getRsdcia() {
        return rsdcia;
    }

    public void setRsdcia(String rsdcia) {
        this.rsdcia = rsdcia;
    }

    public String getEmlcia() {
        return emlcia;
    }

    public void setEmlcia(String emlcia) {
        this.emlcia = emlcia;
    }

    public String getWebcia() {
        return webcia;
    }

    public void setWebcia(String webcia) {
        this.webcia = webcia;
    }

    public String getCodciu() {
        return codciu;
    }

    public void setCodciu(String codciu) {
        this.codciu = codciu;
    }

    public int getGrncia() {
        return grncia;
    }

    public void setGrncia(int grncia) {
        this.grncia = grncia;
    }

    public int getVgccia() {
        return vgccia;
    }

    public void setVgccia(int vgccia) {
        this.vgccia = vgccia;
    }

    public int getAutcia() {
        return autcia;
    }

    public void setAutcia(int autcia) {
        this.autcia = autcia;
    }

    public int getRtacia() {
        return rtacia;
    }

    public void setRtacia(int rtacia) {
        this.rtacia = rtacia;
    }

    public String getIcacia() {
        return icacia;
    }

    public void setIcacia(String icacia) {
        this.icacia = icacia;
    }

    public int getIvacia() {
        return ivacia;
    }

    public void setIvacia(int ivacia) {
        this.ivacia = ivacia;
    }

    public int getGtacia() {
        return gtacia;
    }

    public void setGtacia(int gtacia) {
        this.gtacia = gtacia;
    }

    public int getStmcia() {
        return stmcia;
    }

    public void setStmcia(int stmcia) {
        this.stmcia = stmcia;
    }

    public String getPswcia() {
        return pswcia;
    }

    public void setPswcia(String pswcia) {
        this.pswcia = pswcia;
    }

    public String getStdcia() {
        return stdcia;
    }

    public void setStdcia(String stdcia) {
        this.stdcia = stdcia;
    }

    public String getCtdcia() {
        return ctdcia;
    }

    public void setCtdcia(String ctdcia) {
        this.ctdcia = ctdcia;
    }

    public String getRvscia() {
        return rvscia;
    }

    public void setRvscia(String rvscia) {
        this.rvscia = rvscia;
    }

    public int getCtbcia() {
        return ctbcia;
    }

    public void setCtbcia(int ctbcia) {
        this.ctbcia = ctbcia;
    }

    public String getRgmcia() {
        return rgmcia;
    }

    public void setRgmcia(String rgmcia) {
        this.rgmcia = rgmcia;
    }

    public String getRtfcia() {
        return rtfcia;
    }

    public void setRtfcia(String rtfcia) {
        this.rtfcia = rtfcia;
    }

    public String getTikcia() {
        return tikcia;
    }

    public void setTikcia(String tikcia) {
        this.tikcia = tikcia;
    }

    public String getUtlpqk() {
        return utlpqk;
    }

    public void setUtlpqk(String utlpqk) {
        this.utlpqk = utlpqk;
    }

    public String getUaqpqk() {
        return uaqpqk;
    }

    public void setUaqpqk(String uaqpqk) {
        this.uaqpqk = uaqpqk;
    }

    public String getPygpqk() {
        return pygpqk;
    }

    public void setPygpqk(String pygpqk) {
        this.pygpqk = pygpqk;
    }

    public String getKjapqk() {
        return kjapqk;
    }

    public void setKjapqk(String kjapqk) {
        this.kjapqk = kjapqk;
    }

    public String getCtipqk() {
        return ctipqk;
    }

    public void setCtipqk(String ctipqk) {
        this.ctipqk = ctipqk;
    }

    public String getCxmpqk() {
        return cxmpqk;
    }

    public void setCxmpqk(String cxmpqk) {
        this.cxmpqk = cxmpqk;
    }

  
    public List<Smaare> getSmaareList() {
        return smaareList;
    }

    public void setSmaareList(List<Smaare> smaareList) {
        this.smaareList = smaareList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcia != null ? codcia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smacia)) {
            return false;
        }
        Smacia other = (Smacia) object;
        if ((this.codcia == null && other.codcia != null) || (this.codcia != null && !this.codcia.equals(other.codcia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.academicas.Smacia[ codcia=" + codcia + " ]";
    }
}
