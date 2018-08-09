package mvc.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

/**
 *
 * @author efrain
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static Session sesion;
    public static Statistics stat;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration().configure();
            SessionFactory ssf = conf.buildSessionFactory();
            stat = ssf.getStatistics();
            stat.setStatisticsEnabled(true);
            return ssf;
        } catch (Throwable ex) {
            System.err.println("ERROR: no se ha podido cargar el controlador JDBC de PL." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static Session getNewSession() {
        sesion = getSessionFactory().openSession();
        return sesion;
    }

    public static void closeCurrentSession() {
        if (sesion != null) {
            sesion.clear();
            sesion.close();
        }
    }

    public static Transaction getNewTransaction(Session sesion) {
        return sesion.getTransaction();
    }

}
