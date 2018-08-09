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
 * @author efrain
 */
@Entity
@Table(name = "SMAPAP")
@NamedQueries({
    @NamedQuery(name = "Smapap.findAll", query = "SELECT s FROM Smapap s")})
public class Smapap implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nropap;
    private String smtpsm;
    private Smapkp nropkp;

    public Smapap() {
    }

    public Smapap(String nropap) {
        this.nropap = nropap;
    }

    public Smapap(String nropap, String smtpsm) {
        this.nropap = nropap;
        this.smtpsm = smtpsm;
    }

    @Id
    @Basic(optional = false)
    @Column(name = "NROPAP", nullable = false, length = 10)
    @GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "mvc.model.generator.StringSequenceGeneratorTen", parameters = { @Parameter(name = "sequence", value = "SEQPAP") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
    public String getNropap() {
        return nropap;
    }

    public void setNropap(String nropap) {
        this.nropap = nropap;
    }

    @Basic(optional = false)
    @Column(name = "SMTPSM", nullable = false, length = 2)
    public String getSmtpsm() {
        return smtpsm;
    }

    public void setSmtpsm(String smtpsm) {
        this.smtpsm = smtpsm;
    }

    @JoinColumn(name = "NROPKP", referencedColumnName = "NROPKP", nullable = false)
    @ManyToOne(optional = false)
    public Smapkp getNropkp() {
        return nropkp;
    }

    public void setNropkp(Smapkp nropkp) {
        this.nropkp = nropkp;
    }

}
