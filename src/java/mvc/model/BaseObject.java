package mvc.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @descripcion Clase que da formato a los beans
 * @autor Erick Castillo Caballero
 * @fecha_creacion 15/07/2014
 * @fecha_modificacion 15/07/2014
 * @throws IOException
 * @throws SQLException
 */ 
public class BaseObject implements Serializable {

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
