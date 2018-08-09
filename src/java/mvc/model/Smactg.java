package mvc.model;

import java.util.Hashtable;

public class Smactg {

    private String codcia;
    private String nroctg;
    private String codprs;
    private String tpoprs;
    private String codctg;
    private String tpoctg;
    private String nomctg;
    private String dspctg;
    private int numcrs;
    private String sqlbkr;

    public String getCodcia() {
        return (this.codcia);
    }

    public String getNroctg() {
        return (this.nroctg);
    }

    public String getCodprs() {
        return (this.codprs);
    }

    public String getTpoprs() {
        return (this.tpoprs);
    }

    public String getCodctg() {
        return (this.codctg);
    }

    public String getTpoctg() {
        return (this.tpoctg);
    }

    public String getNomctg() {
        return (this.nomctg);
    }

    public String getDspctg() {
        return (this.dspctg);
    }

    public int getNumcrs() {
        return (this.numcrs);
    }

    public String getSqlbkr() {
        return (this.sqlbkr);
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    public void setNroctg(String nroctg) {
        this.nroctg = nroctg;
    }

    public void setCodprs(String codprs) {
        this.codprs = codprs;
    }

    public void setTpoprs(String tpoprs) {
        this.tpoprs = tpoprs;
    }

    public void setCodctg(String codctg) {
        this.codctg = codctg;
    }

    public void setTpoctg(String tpoctg) {
        this.tpoctg = tpoctg;
    }

    public void setNomctg(String nomctg) {
        this.nomctg = nomctg;
    }

    public void setDspctg(String dspctg) {
        this.dspctg = dspctg;
    }

    public void setNumcrs(int numcrs) {
        this.numcrs = numcrs;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public void loadFromHash(Object objectMap) {
        Hashtable mapData = (Hashtable)objectMap;
        this.codcia = Util.validStr(mapData, "CODCIA");
        this.codctg = Util.validStr(mapData, "CODCTG");
        this.codprs = Util.validStr(mapData, "CODPRS");
        this.dspctg = Util.validStr(mapData, "DSPCTG");
        this.nomctg = Util.validStr(mapData, "NOMCTG");
        this.nroctg = Util.validStr(mapData, "NROCTG");
        this.numcrs = Util.validInt(mapData, "NUMCRS");
        this.tpoctg = Util.validStr(mapData, "TPOCTG");
        this.tpoprs = Util.validStr(mapData, "TPOPRS");
    }
}
