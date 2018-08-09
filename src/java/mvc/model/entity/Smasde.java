/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.entity;

import java.io.Serializable;
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

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMASDE")
@NamedQueries({
    @NamedQuery(name = "Smasde.findAll", query = "SELECT s FROM Smasde s")})
public class Smasde implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nrosde;
    private String codsde;
    private String nomsde;
    private String npqsde;
    private String tposde;
    private String dirsde;
    private String telsde;
    private String codciu;
    private String emlsde;
    private double vlrsde;
    private String znasde;
    private String clbsde;
    private String krasde;
    private String krbsde;
    private String sqlbkr;
    private Smacia codcia;

    public Smasde() {
    }

    public Smasde(String nrosde) {
        this.nrosde = nrosde;
    }

    public Smasde(String nrosde, String codsde, String nomsde, String npqsde, String tposde, String dirsde, String telsde, String codciu, String emlsde, double vlrsde, String znasde, String clbsde, String krasde, String krbsde, String sqlbkr) {
        this.nrosde = nrosde;
        this.codsde = codsde;
        this.nomsde = nomsde;
        this.npqsde = npqsde;
        this.tposde = tposde;
        this.dirsde = dirsde;
        this.telsde = telsde;
        this.codciu = codciu;
        this.emlsde = emlsde;
        this.vlrsde = vlrsde;
        this.znasde = znasde;
        this.clbsde = clbsde;
        this.krasde = krasde;
        this.krbsde = krbsde;
        this.sqlbkr = sqlbkr;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROSDE", nullable = false, length = 3)
    public String getNrosde() {
        return nrosde;
    }

    public void setNrosde(String nrosde) {
        this.nrosde = nrosde;
    }

    @Basic(optional = false)
    @Column(name = "CODSDE", nullable = false, length = 10)
    public String getCodsde() {
        return codsde;
    }

    public void setCodsde(String codsde) {
        this.codsde = codsde;
    }

    @Basic(optional = false)
    @Column(name = "NOMSDE", nullable = false, length = 80)
    public String getNomsde() {
        return nomsde;
    }

    public void setNomsde(String nomsde) {
        this.nomsde = nomsde;
    }

    @Basic(optional = false)
    @Column(name = "NPQSDE", nullable = false, length = 30)
    public String getNpqsde() {
        return npqsde;
    }

    public void setNpqsde(String npqsde) {
        this.npqsde = npqsde;
    }

    @Basic(optional = false)
    @Column(name = "TPOSDE", nullable = false, length = 3)
    public String getTposde() {
        return tposde;
    }

    public void setTposde(String tposde) {
        this.tposde = tposde;
    }

    @Basic(optional = false)
    @Column(name = "DIRSDE", nullable = false, length = 80)
    public String getDirsde() {
        return dirsde;
    }

    public void setDirsde(String dirsde) {
        this.dirsde = dirsde;
    }

    @Basic(optional = false)
    @Column(name = "TELSDE", nullable = false, length = 30)
    public String getTelsde() {
        return telsde;
    }

    public void setTelsde(String telsde) {
        this.telsde = telsde;
    }

    @Basic(optional = false)
    @Column(name = "CODCIU", nullable = false, length = 6)
    public String getCodciu() {
        return codciu;
    }

    public void setCodciu(String codciu) {
        this.codciu = codciu;
    }

    @Basic(optional = false)
    @Column(name = "EMLSDE", nullable = false, length = 65)
    public String getEmlsde() {
        return emlsde;
    }

    public void setEmlsde(String emlsde) {
        this.emlsde = emlsde;
    }

    @Basic(optional = false)
    @Column(name = "VLRSDE", nullable = false)
    public double getVlrsde() {
        return vlrsde;
    }

    public void setVlrsde(double vlrsde) {
        this.vlrsde = vlrsde;
    }

    @Basic(optional = false)
    @Column(name = "ZNASDE", nullable = false, length = 3)
    public String getZnasde() {
        return znasde;
    }

    public void setZnasde(String znasde) {
        this.znasde = znasde;
    }

    @Basic(optional = false)
    @Column(name = "CLBSDE", nullable = false, length = 10)
    public String getClbsde() {
        return clbsde;
    }

    public void setClbsde(String clbsde) {
        this.clbsde = clbsde;
    }

    @Basic(optional = false)
    @Column(name = "KRASDE", nullable = false, length = 10)
    public String getKrasde() {
        return krasde;
    }

    public void setKrasde(String krasde) {
        this.krasde = krasde;
    }

    @Basic(optional = false)
    @Column(name = "KRBSDE", nullable = false, length = 10)
    public String getKrbsde() {
        return krbsde;
    }

    public void setKrbsde(String krbsde) {
        this.krbsde = krbsde;
    }

    @Basic(optional = false)
    @Column(name = "SQLBKR", nullable = false, length = 4000)
    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
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
        hash += (nrosde != null ? nrosde.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smasde)) {
            return false;
        }
        Smasde other = (Smasde) object;
        if ((this.nrosde == null && other.nrosde != null) || (this.nrosde != null && !this.nrosde.equals(other.nrosde))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Smasde[ nrosde=" + nrosde + " ]";
    }

}
