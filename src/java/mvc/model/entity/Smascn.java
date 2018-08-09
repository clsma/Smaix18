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
@Table(name = "SMASCN")
@NamedQueries({
    @NamedQuery(name = "Smascn.findAll", query = "SELECT s FROM Smascn s")})
public class Smascn implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idescn;
    private String nomscn;
    private String npqscn;
    private String tposcn;
    private String stdscn;
    private long clrscn;
    private Smaciu ideciu;
    private Smacia codcia;

    public Smascn() {
    }

    public Smascn(String idescn) {
        this.idescn = idescn;
    }

    public Smascn(String idescn, String nomscn, String npqscn, String tposcn, String stdscn, long clrscn) {
        this.idescn = idescn;
        this.nomscn = nomscn;
        this.npqscn = npqscn;
        this.tposcn = tposcn;
        this.stdscn = stdscn;
        this.clrscn = clrscn;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "IDESCN", nullable = false, length = 5)
    public String getIdescn() {
        return idescn;
    }

    public void setIdescn(String idescn) {
        this.idescn = idescn;
    }

    @Basic(optional = false)
    @Column(name = "NOMSCN", nullable = false, length = 128)
    public String getNomscn() {
        return nomscn;
    }

    public void setNomscn(String nomscn) {
        this.nomscn = nomscn;
    }

    @Basic(optional = false)
    @Column(name = "NPQSCN", nullable = false, length = 20)
    public String getNpqscn() {
        return npqscn;
    }

    public void setNpqscn(String npqscn) {
        this.npqscn = npqscn;
    }

    @Basic(optional = false)
    @Column(name = "TPOSCN", nullable = false, length = 3)
    public String getTposcn() {
        return tposcn;
    }

    public void setTposcn(String tposcn) {
        this.tposcn = tposcn;
    }

    @Basic(optional = false)
    @Column(name = "STDSCN", nullable = false, length = 13)
    public String getStdscn() {
        return stdscn;
    }

    public void setStdscn(String stdscn) {
        this.stdscn = stdscn;
    }

    @Basic(optional = false)
    @Column(name = "CLRSCN", nullable = false)
    public long getClrscn() {
        return clrscn;
    }

    public void setClrscn(long clrscn) {
        this.clrscn = clrscn;
    }

    @JoinColumn(name = "IDECIU", referencedColumnName = "IDECIU", nullable = false)
    @ManyToOne(optional = false)
    public Smaciu getIdeciu() {
        return ideciu;
    }

    public void setIdeciu(Smaciu ideciu) {
        this.ideciu = ideciu;
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
