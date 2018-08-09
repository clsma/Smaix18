package mvc.model.entity;

import java.io.Serializable;
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
import javax.persistence.Transient;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "smapai")
@NamedQueries({
    @NamedQuery(name = "Smapai.findAll", query = "SELECT s FROM Smapai s")})
public class Smapai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idepai", nullable = false, length = 4)
    private String idepai;
    @Basic(optional = false)
    @Column(name = "codpai", nullable = false, length = 6)
    private String codpai;
    @Basic(optional = false)
    @Column(name = "isapai", nullable = false, length = 2)
    private String isapai;
    @Basic(optional = false)
    @Column(name = "isbpai", nullable = false, length = 3)
    private String isbpai;
    @Basic(optional = false)
    @Column(name = "nompai", nullable = false, length = 100)
    private String nompai;
    @Basic(optional = false)
    @Column(name = "sqlbkr", nullable = false, length = 2147483647)
    private String sqlbkr;
    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idepai")
    private List<Smaciu> smaciuList;
    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idepai")
    private List<Smadpt> smadptList;

    public Smapai() {
    }

    public Smapai(String idepai) {
        this.idepai = idepai;
    }

    public Smapai(String idepai, String codpai, String isapai, String isbpai, String nompai, String sqlbkr) {
        this.idepai = idepai;
        this.codpai = codpai;
        this.isapai = isapai;
        this.isbpai = isbpai;
        this.nompai = nompai;
        this.sqlbkr = sqlbkr;
    }

    public String getIdepai() {
        return idepai;
    }

    public void setIdepai(String idepai) {
        this.idepai = idepai;
    }

    public String getCodpai() {
        return codpai;
    }

    public void setCodpai(String codpai) {
        this.codpai = codpai;
    }

    public String getIsapai() {
        return isapai;
    }

    public void setIsapai(String isapai) {
        this.isapai = isapai;
    }

    public String getIsbpai() {
        return isbpai;
    }

    public void setIsbpai(String isbpai) {
        this.isbpai = isbpai;
    }

    public String getNompai() {
        return nompai;
    }

    public void setNompai(String nompai) {
        this.nompai = nompai;
    }

    public String getSqlbkr() {
        return sqlbkr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public List<Smaciu> getSmaciuList() {
        return smaciuList;
    }

    public void setSmaciuList(List<Smaciu> smaciuList) {
        this.smaciuList = smaciuList;
    }

    public List<Smadpt> getSmadptList() {
        return smadptList;
    }

    public void setSmadptList(List<Smadpt> smadptList) {
        this.smadptList = smadptList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idepai != null ? idepai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smapai)) {
            return false;
        }
        Smapai other = (Smapai) object;
        if ((this.idepai == null && other.idepai != null) || (this.idepai != null && !this.idepai.equals(other.idepai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.generales.Smapai[ idepai=" + idepai + " ]";
    }
}
