/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.hibernate.transform.BasicTransformerAdapter;

/**
 *
 * @author C540 I5
 */
public class MyResultTransformer extends BasicTransformerAdapter {

    public final static MyResultTransformer INSTANCE;

    static {
        INSTANCE = new MyResultTransformer();
    }

    private MyResultTransformer() {

    }
    private static final long serialVersionUID = 1L;

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < aliases.length; i++) {
            Object t = tuple[i];
            if (t != null && t instanceof Clob) {
                Clob c = (Clob) tuple[i];
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    IOUtils.copy(c.getAsciiStream(), bos);
                    t = new String(bos.toByteArray(), Charset.forName("UTF-8"));
                } catch (SQLException e) {
                    Util.logError(e);
                } catch (IOException ex) {
                    Util.logError(ex);
                }
            }
            map.put(aliases[i], t);
        }
        return map;
    }

}
