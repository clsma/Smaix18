/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.generator;

import java.io.Serializable;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGeneratorHelper.BigDecimalHolder;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.tool.hbm2x.StringUtils;

public class StringSequenceGeneratorTen extends SequenceGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object obj) {
        return StringUtils.leftPad(super.generate(session, obj).toString(), 10, "0");
    }

    @Override
    protected IntegralDataTypeHolder buildHolder() {
        return new BigDecimalHolder();
    }
}
