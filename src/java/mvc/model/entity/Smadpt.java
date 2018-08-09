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
import javax.persistence.Transient;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "smadpt")
@NamedQueries({
    @NamedQuery(name = "Smadpt.findAll", query = "SELECT s FROM Smadpt s")})
public class Smadpt implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idedpt;
    private String nomdpt;
    private Smapai idepai;


    public Smadpt() {
    }

    public Smadpt(String idedpt) {
        this.idedpt = idedpt;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "idedpt", nullable = false, length = 6)
    public String getIdedpt() {
        return idedpt;
    }

    public void setIdedpt(String idedpt) {
        this.idedpt = idedpt;
    }

    @Basic(optional = false)
    @Column(name = "nomdpt", nullable = false, length = 100)
    public String getNomdpt() {
        return nomdpt;
    }

    public void setNomdpt(String nomdpt) {
        this.nomdpt = nomdpt;
    }

    @JoinColumn(name = "idepai", referencedColumnName = "idepai", nullable = false)
    @ManyToOne(optional = false)
    public Smapai getIdepai() {
        return idepai;
    }

    public void setIdepai(Smapai idepai) {
        this.idepai = idepai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idedpt != null ? idedpt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smadpt)) {
            return false;
        }
        Smadpt other = (Smadpt) object;
        if ((this.idedpt == null && other.idedpt != null) || (this.idedpt != null && !this.idedpt.equals(other.idedpt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.generales.Smadpt[ idedpt=" + idedpt + " ]";
    }
}
