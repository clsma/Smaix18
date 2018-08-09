package mvc.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @descripcion Clase que sobreescbribe metodos para que el resultado de 
 * una consulta preserve el orden de los campos especificados
 * @nombre AliasToEntityOrderedMapResultTransformer
 * @copyrigth ClSMA Ltda
 * @author Efrain blanco
 * @fechaModificacion 22/08/2014
 */
public class AliasToEntityOrderedMapResultTransformer extends AliasedTupleSubsetResultTransformer {

    public static final AliasToEntityOrderedMapResultTransformer INSTANCE = new AliasToEntityOrderedMapResultTransformer();

    /**
     * Disallow instantiation of AliasToEntityOrderedMapResultTransformer .
     */
    private AliasToEntityOrderedMapResultTransformer() {
    }

    /**
     * {@inheritDoc}
     * @param tuple
     * @param aliases
     * @return 
     */
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        /* please note here LinkedHashMap is used so hopefully u ll get ordered key */
        Map result = new LinkedHashMap(tuple.length);
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                result.put(alias, tuple[i]);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * @param aliases
     * @param tupleLength
     * @return 
     */
    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
