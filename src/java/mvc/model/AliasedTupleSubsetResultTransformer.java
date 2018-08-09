package mvc.model;

import org.hibernate.transform.BasicTransformerAdapter;

/**
 * @nombre AliasedTupleSubsetResultTransformer
 * @copyrigth ClSMA Ltda
 * @author Efrain blanco
 * @fechaModificacion 22/08/2014
 */
public abstract class AliasedTupleSubsetResultTransformer
        extends BasicTransformerAdapter
        implements TupleSubsetResultTransformer {

    @Override
    public boolean[] includeInTransform(String[] aliases, int tupleLength) {
        if (aliases == null) {
            throw new IllegalArgumentException("aliases cannot be null");
        }
        if (aliases.length != tupleLength) {
            throw new IllegalArgumentException(
                    "aliases and tupleLength must have the same length; "
                    + "aliases.length=" + aliases.length + "tupleLength=" + tupleLength
            );
        }
        boolean[] includeInTransform = new boolean[tupleLength];
        for (int i = 0; i < aliases.length; i++) {
            if (aliases[ i] != null) {
                includeInTransform[ i] = true;
            }
        }
        return includeInTransform;
    }
}
