package mvc.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author efrain
 */
@Entity
@Table(name = "SMACIU", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codciu"})})
@NamedQueries({
    @NamedQuery(name = "Smaciu.findAll", query = "SELECT s FROM Smaciu s")})
public class Smaciu implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ideciu;
    private String codciu;
    private String nomciu;
    private String npqciu;
    private String stdciu;
    private Smadpt idedpt;

    public Smaciu() {
    }

    public Smaciu(String ideciu) {
        this.ideciu = ideciu;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "ideciu", nullable = false, length = 6)
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorSix", parameters = { @Parameter(name = "sequence", value = "seqciu") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    public String getIdeciu() {
        return ideciu;
    }

    public void setIdeciu(String ideciu) {
        this.ideciu = ideciu;
    }

    @Basic(optional = false)
    @Column(name = "codciu", nullable = false, length = 5)
    public String getCodciu() {
        return codciu;
    }

    public void setCodciu(String codciu) {
        this.codciu = codciu;
    }

    @Basic(optional = false)
    @Column(name = "nomciu", nullable = false, length = 40)
    public String getNomciu() {
        return nomciu;
    }

    public void setNomciu(String nomciu) {
        this.nomciu = nomciu;
    }

    @Basic(optional = false)
    @Column(name = "npqciu", nullable = false, length = 40)
    public String getNpqciu() {
        return npqciu;
    }

    public void setNpqciu(String npqciu) {
        this.npqciu = npqciu;
    }

    @Basic(optional = false)
    @Column(name = "stdciu", nullable = false, length = 13)
    public String getStdciu() {
        return stdciu;
    }

    public void setStdciu(String stdciu) {
        this.stdciu = stdciu;
    }

    @JoinColumn(name = "idedpt", referencedColumnName = "idedpt", nullable = false)
    @ManyToOne(optional = false)
    public Smadpt getIdedpt() {
        return idedpt;
    }

    public void setIdedpt(Smadpt idedpt) {
        this.idedpt = idedpt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideciu != null ? ideciu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Smaciu)) {
            return false;
        }
        Smaciu other = (Smaciu) object;
        if ((this.ideciu == null && other.ideciu != null) || (this.ideciu != null && !this.ideciu.equals(other.ideciu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.clsma.entity.basicas.generales.Smaciu[ ideciu=" + ideciu + " ]";
    }

}
