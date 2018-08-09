/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.generator;

import java.io.Serializable;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGeneratorHelper;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.tool.hbm2x.StringUtils;

/**
 *
 * @author efrain
 */
 public class StringSequenceGeneratorSix extends SequenceGenerator {

        @Override
        public Serializable generate(SessionImplementor session, Object obj) {
            return StringUtils.leftPad(super.generate(session, obj).toString(), 6, "0");
        }

        @Override
        protected IntegralDataTypeHolder buildHolder() {
            return new IdentifierGeneratorHelper.BigDecimalHolder();
        }
    }
