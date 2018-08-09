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
@Table(name = "SMAAUL")
@NamedQueries({
    @NamedQuery(name = "Smaaul.findAll", query = "SELECT s FROM Smaaul s")})
public class Smaaul implements Serializable {

    private static final long serialVersionUID = 1L;
    private String codaul;
    private String codedf;
    private double psoaul;
    private String blkaul;
    private String nroaul;
    private String nomaul;
    private String tipaul;
    private double maxaul;
    private double achaul;
    private double lrgaul;
    private double areaul;
    private double atraul;
    private double sllaul;
    private double airaul;
    private double vtlaul;
    private double msaaul;
    private double vlsaul;
    private double vlaaul;
    private double vlmaul;
    private double vlvaul;
    private String smtpsm;
    private String nrocrs;
    private String codcrs;
    private String codsch;
    private String stdaul;
    private double topobj;
    private double lftobj;
    private String phtobj;
    private double clraul;
    private String sqlbkr;
    private Smasde nrosde;
    private Smacia codcia;

    public Smaaul() {
    }

    public Smaaul(String codaul) {
        this.codaul = codaul;
    }

    public Smaaul(String codaul, String codedf, double psoaul, String blkaul, String nroaul, String nomaul, String tipaul, double maxaul, double achaul, double lrgaul, double areaul, double atraul, double sllaul, double airaul, double vtlaul, double msaaul, double vlsaul, double vlaaul, double vlmaul, double vlvaul, String smtpsm, String nrocrs, String codcrs, String codsch, String stdaul, double topobj, double lftobj, String phtobj, double clraul, String sqlbkr) {
        this.codaul = codaul;
        this.codedf = codedf;
        this.psoaul = psoaul;
        this.blkaul = blkaul;
        this.nroaul = nroaul;
        this.nomaul = nomaul;
        this.tipaul = tipaul;
        this.maxaul = maxaul;
        this.achaul = achaul;
        this.lrgaul = lrgaul;
        this.areaul = areaul;
        this.atraul = atraul;
        this.sllaul = sllaul;
        this.airaul = airaul;
        this.vtlaul = vtlaul;
        this.msaaul = msaaul;
        this.vlsaul = vlsaul;
        this.vlaaul = vlaaul;
        this.vlmaul = vlmaul;
        this.vlvaul = vlvaul;
        this.smtpsm = smtpsm;
        this.nrocrs = nrocrs;
        this.codcrs = codcrs;
        this.codsch = codsch;
        this.stdaul = stdaul;
        this.topobj = topobj;
        this.lftobj = lftobj;
        this.phtobj = phtobj;
        this.clraul = clraul;
        this.sqlbkr = sqlbkr;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CODAUL", nullable = false, length = 6)
    public String getCodaul() {
        return codaul;
    }

    public void setCodaul(String codaul) {
        this.codaul = codaul;
    }

    @Basic(optional = false)
    @Column(name = "CODEDF", nullable = false, length = 2)
    public String getCodedf() {
        return codedf;
    }

    public void setCodedf(String codedf) {
        this.codedf = codedf;
    }

    @Basic(optional = false)
    @Column(name = "PSOAUL", nullable = false)
    public double getPsoaul() {
        return psoaul;
    }

    public void setPsoaul(double psoaul) {
        this.psoaul = psoaul;
    }

    @Basic(optional = false)
    @Column(name = "BLKAUL", nullable = false, length = 1)
    public String getBlkaul() {
        return blkaul;
    }

    public void setBlkaul(String blkaul) {
        this.blkaul = blkaul;
    }

    @Basic(optional = false)
    @Column(name = "NROAUL", nullable = false, length = 3)
    public String getNroaul() {
        return nroaul;
    }

    public void setNroaul(String nroaul) {
        this.nroaul = nroaul;
    }

    @Basic(optional = false)
    @Column(name = "NOMAUL", nullable = false, length = 25)
    public String getNomaul() {
        return nomaul;
    }

    public void setNomaul(String nomaul) {
        this.nomaul = nomaul;
    }

    @Basic(optional = false)
    @Column(name = "TIPAUL", nullable = false, length = 20)
    public String getTipaul() {
        return tipaul;
    }

    public void setTipaul(String tipaul) {
        this.tipaul = tipaul;
    }

    @Basic(optional = false)
    @Column(name = "MAXAUL", nullable = false)
    public double getMaxaul() {
        return maxaul;
    }

    public void setMaxaul(double maxaul) {
        this.maxaul = maxaul;
    }

    @Basic(optional = false)
    @Column(name = "ACHAUL", nullable = false)
    public double getAchaul() {
        return achaul;
    }

    public void setAchaul(double achaul) {
        this.achaul = achaul;
    }

    @Basic(optional = false)
    @Column(name = "LRGAUL", nullable = false)
    public double getLrgaul() {
        return lrgaul;
    }

    public void setLrgaul(double lrgaul) {
        this.lrgaul = lrgaul;
    }

    @Basic(optional = false)
    @Column(name = "AREAUL", nullable = false)
    public double getAreaul() {
        return areaul;
    }

    public void setAreaul(double areaul) {
        this.areaul = areaul;
    }

    @Basic(optional = false)
    @Column(name = "ATRAUL", nullable = false)
    public double getAtraul() {
        return atraul;
    }

    public void setAtraul(double atraul) {
        this.atraul = atraul;
    }

    @Basic(optional = false)
    @Column(name = "SLLAUL", nullable = false)
    public double getSllaul() {
        return sllaul;
    }

    public void setSllaul(double sllaul) {
        this.sllaul = sllaul;
    }

    @Basic(optional = false)
    @Column(name = "AIRAUL", nullable = false)
    public double getAiraul() {
        return airaul;
    }

    public void setAiraul(double airaul) {
        this.airaul = airaul;
    }

    @Basic(optional = false)
    @Column(name = "VTLAUL", nullable = false)
    public double getVtlaul() {
        return vtlaul;
    }

    public void setVtlaul(double vtlaul) {
        this.vtlaul = vtlaul;
    }

    @Basic(optional = false)
    @Column(name = "MSAAUL", nullable = false)
    public double getMsaaul() {
        return msaaul;
    }

    public void setMsaaul(double msaaul) {
        this.msaaul = msaaul;
    }

    @Basic(optional = false)
    @Column(name = "VLSAUL", nullable = false)
    public double getVlsaul() {
        return vlsaul;
    }

    public void setVlsaul(double vlsaul) {
        this.vlsaul = vlsaul;
    }

    @Basic(optional = false)
    @Column(name = "VLAAUL", nullable = false)
    public double getVlaaul() {
        return vlaaul;
    }

    public void setVlaaul(double vlaaul) {
        this.vlaaul = vlaaul;
    }

    @Basic(optional = false)
    @Column(name = "VLMAUL", nullable = false)
    public double getVlmaul() {
        return vlmaul;
    }

    public void setVlmaul(double vlmaul) {
        this.vlmaul = vlmaul;
    }

    @Basic(optional = false)
    @Column(name = "VLVAUL", nullable = false)
    public double getVlvaul() {
        return vlvaul;
    }

    public void setVlvaul(double vlvaul) {
        this.vlvaul = vlvaul;
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
    @Column(name = "NROCRS", nullable = false, length = 2)
    public String getNrocrs() {
        return nrocrs;
    }

    public void setNrocrs(String nrocrs) {
        this.nrocrs = nrocrs;
    }

    @Basic(optional = false)
    @Column(name = "CODCRS", nullable = false, length = 15)
    public String getCodcrs() {
        return codcrs;
    }

    public void setCodcrs(String codcrs) {
        this.codcrs = codcrs;
    }

    @Basic(optional = false)
    @Column(name = "CODSCH", nullable = false, length = 4)
    public String getCodsch() {
        return codsch;
    }

    public void setCodsch(String codsch) {
        this.codsch = codsch;
    }

    @Basic(optional = false)
    @Column(name = "STDAUL", nullable = false, length = 15)
    public String getStdaul() {
        return stdaul;
    }

    public void setStdaul(String stdaul) {
        this.stdaul = stdaul;
    }

    @Basic(optional = false)
    @Column(name = "TOPOBJ", nullable = false)
    public double getTopobj() {
        return topobj;
    }

    public void setTopobj(double topobj) {
        this.topobj = topobj;
    }

    @Basic(optional = false)
    @Column(name = "LFTOBJ", nullable = false)
    public double getLftobj() {
        return lftobj;
    }

    public void setLftobj(double lftobj) {
        this.lftobj = lftobj;
    }

    @Basic(optional = false)
    @Column(name = "PHTOBJ", nullable = false, length = 45)
    public String getPhtobj() {
        return phtobj;
    }

    public void setPhtobj(String phtobj) {
        this.phtobj = phtobj;
    }

    @Basic(optional = false)
    @Column(name = "CLRAUL", nullable = false)
    public double getClraul() {
        return clraul;
    }

    public void setClraul(double clraul) {
        this.clraul = clraul;
    }

    @Basic(optional = false)
    @Column(name = "SQLBKR", nullable = false, length = 4000)
    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    @JoinColumn(name = "NROSDE", referencedColumnName = "NROSDE", nullable = false)
    @ManyToOne(optional = false)
    public Smasde getNrosde() {
        return nrosde;
    }

    public void setNrosde(Smasde nrosde) {
        this.nrosde = nrosde;
    }

    @JoinColumn(name = "CODCIA", referencedColumnName = "CODCIA", nullable = false)
    @ManyToOne(optional = false)
    public Smacia getCodcia() {
        return codcia;
    }

    public void setCodcia(Smacia codcia) {
        this.codcia = codcia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codaul != null ? codaul.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smaaul)) {
            return false;
        }
        Smaaul other = (Smaaul) object;
        if ((this.codaul == null && other.codaul != null) || (this.codaul != null && !this.codaul.equals(other.codaul))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Smaaul[ codaul=" + codaul + " ]";
    }

}
