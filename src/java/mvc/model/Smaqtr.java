package mvc.model;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;

public class Smaqtr {

    private String codcia;
    private String nroqtr;
    private String nomqtr;
    private String intqtr;
    private Date fchbgn;
    private Date fchend;
    private String horbgn;
    private String horend;
    private int mixqst;
    private int mixans;
    private int numtry;
    private String metgrd;
    private String shwgrd;
    private String shwans;
    private String alwchk;
    private double maxgrd;
    private Date fchmke;
    private Date fchedt;
    private int tmeqtr;
    private String codprs;
    private String tpoprs;
    private String tpoqtr;
    private String dgrqtr;
    private String tpcqtr;
    private String sqlbkr;

    public String getTpcqtr() {
        return tpcqtr;
    }

    public void setTpcqtr(String tpcqtr) {
        this.tpcqtr = tpcqtr;
    }

    public String getCodcia() {
        return (this.codcia);
    }

    public String getNroqtr() {
        return (this.nroqtr);
    }

    public String getNomqtr() {
        return (this.nomqtr);
    }

    public String getIntqtr() {
        return (this.intqtr);
    }

    public Date getFchbgn() {
        return (this.fchbgn);
    }

    public Date getFchend() {
        return (this.fchend);
    }

    public String getHorbgn() {
        return (this.horbgn);
    }

    public String getHorend() {
        return (this.horend);
    }

    public int getMixqst() {
        return (this.mixqst);
    }

    public int getMixans() {
        return (this.mixans);
    }

    public int getNumtry() {
        return (this.numtry);
    }

    public String getMetgrd() {
        return (this.metgrd);
    }

    public String getShwgrd() {
        return (this.shwgrd);
    }

    public String getShwans() {
        return (this.shwans);
    }

    public String getAlwchk() {
        return (this.alwchk);
    }

    public double getMaxgrd() {
        return (this.maxgrd);
    }

    public Date getFchmke() {
        return (this.fchmke);
    }

    public Date getFchedt() {
        return (this.fchedt);
    }

    public int getTmeqtr() {
        return (this.tmeqtr);
    }

    public String getCodprs() {
        return (this.codprs);
    }

    public String getTpoprs() {
        return (this.tpoprs);
    }

    public String getTpoqtr() {
        return (this.tpoqtr);
    }

    public String getDgrqtr() {
        return (this.dgrqtr);
    }

    public String getSqlbkr() {
        return (this.sqlbkr);
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    public void setNroqtr(String nroqtr) {
        this.nroqtr = nroqtr;
    }

    public void setNomqtr(String nomqtr) {
        this.nomqtr = nomqtr;
    }

    public void setIntqtr(String intqtr) {
        this.intqtr = intqtr;
    }

    public void setFchbgn(Date fchbgn) {
        this.fchbgn = fchbgn;
    }

    public void setFchend(Date fchend) {
        this.fchend = fchend;
    }

    public void setHorbgn(String horbgn) {
        this.horbgn = horbgn;
    }

    public void setHorend(String horend) {
        this.horend = horend;
    }

    public void setMixqst(int mixqst) {
        this.mixqst = mixqst;
    }

    public void setMixans(int mixans) {
        this.mixans = mixans;
    }

    public void setNumtry(int numtry) {
        this.numtry = numtry;
    }

    public void setMetgrd(String metgrd) {
        this.metgrd = metgrd;
    }

    public void setShwgrd(String shwgrd) {
        this.shwgrd = shwgrd;
    }

    public void setShwans(String shwans) {
        this.shwans = shwans;
    }

    public void setAlwchk(String alwchk) {
        this.alwchk = alwchk;
    }

    public void setMaxgrd(double maxgrd) {
        this.maxgrd = maxgrd;
    }

    public void setFchmke(Date fchmke) {
        this.fchmke = fchmke;
    }

    public void setFchedt(Date fchedt) {
        this.fchedt = fchedt;
    }

    public void setTmeqtr(int tmeqtr) {
        this.tmeqtr = tmeqtr;
    }

    public void setCodprs(String codprs) {
        this.codprs = codprs;
    }

    public void setTpoprs(String tpoprs) {
        this.tpoprs = tpoprs;
    }

    public void setTpoqtr(String tpoqtr) {
        this.tpoqtr = tpoqtr;
    }

    public void setDgrqtr(String dgrqtr) {
        this.dgrqtr = dgrqtr;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public void loadFromHash(Object objectMap) throws ParseException {
        Hashtable mapData = (Hashtable)objectMap;
        this.codcia = Util.validStr(mapData, "CODCIA");
        this.alwchk = Util.validStr(mapData, "ALWCHK");
        this.codprs = Util.validStr(mapData, "CODPRS");
        this.dgrqtr = Util.validStr(mapData, "DGRQTR");
        this.fchbgn = Util.validDate(mapData, "FCHBGN");
        this.fchedt = Util.validDate(mapData, "FCHEDT");
        this.fchend = Util.validDate(mapData, "FCHEND");
        this.fchmke = Util.validDate(mapData, "FCHMKE");
        this.horbgn = Util.validStr(mapData, "HORBGN");
        this.horend = Util.validStr(mapData, "HOREND");
        this.intqtr = Util.validStr(mapData, "INTQTR");
        this.maxgrd = Util.validDouble(mapData, "MAXGRD");
        this.metgrd = Util.validStr(mapData, "METGRD");
        this.mixans = Util.validInt(mapData, "MIXANS");
        this.mixqst = Util.validInt(mapData, "MIXQST");
        this.nomqtr = Util.validStr(mapData, "NOMQTR");
        this.nroqtr = Util.validStr(mapData, "NROQTR");
        this.numtry = Util.validInt(mapData, "NUMTRY");
        this.shwans = Util.validStr(mapData, "SHWANS");
        this.shwgrd = Util.validStr(mapData, "SHWGRD");
        this.tmeqtr = Util.validInt(mapData, "TMEQTR");
        this.tpoprs = Util.validStr(mapData, "TPOPRS");
        this.tpoqtr = Util.validStr(mapData, "TPOQTR");
        this.tpcqtr = Util.validStr(mapData, "TPCQTR");
    }
}
