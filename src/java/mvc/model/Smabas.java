package mvc.model;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Smabas {

    private String nroids;
    private String cbysys;
    private Date consys;
    private String ubysys;
    private Date uonsys;
    private String codcia;
    private String titprs;
    private String codprs;
    private String apeprs;
    private String nomprs;
    private String npqprs;
    private String nacprs;
    private Date fhnprs;
    private String ideprs;
    private String nriprs;
    private String epsprs;
    private String sngprs;
    private String civprs;
    private String sexprs;
    private String dirprs;
    private String telprs;
    private String celprs;
    private String codciu;
    private String emlprs;
    private String pswprs;
    private String expprs;
    private String eskprs;
    private String estprs;
    private String snpprs;
    private Date fsnprs;
    private String nroxvt;
    private double psnprs;
    private String stdbas;
    private String milbas;
    private String nrmbas;
    private int ndmbas;
    private int ctgbas;
    private String modicf;
    private double ptjicf;
    private String kgpbas;
    private int abpbas;
    private int aepbas;
    private String codklg;
    private int absbas;
    private int aesbas;
    private String difbas;
    private String tefbas;
    private String klcbas;
    private String ktgbas;
    private String bkabas;
    private String nropgm;
    private String akdbas;
    private String nropsm;
    private String sdtbas;
    private String mtrbas;
    private String bibbas;
    private String ngrbas;
    private String bntbas;
    private String pagbas;
    private String grdbas;
    private double ptjeke;
    private String smtpsm;
    private String pswbas;
    private int crrakd;
    private double prmbas;
    private double prqbas;
    private String snpbas;
    private String codspc;
    private String agnprs;
    private String prdprs;
    private String codcst;
    private String cpsbas;
    private String nrocrs;
    private String agnngr;
    private String prdngr;
    private double ngsbas;
    private String strbas;
    private String codcrs;
    private String etnbas;
    private String plnbas;
    private String disbas;
    private int sbnbas;
    private String nrojob;
    private String vivbas;
    private String dudviv;
    private int phrbas;
    private double psnklg;
    private String sdebas;
    private int pkgbas;
    private int rgnbas;
    private double plqbas;
    private String nropte;
    private String codrgl;
    private String nepbas;
    private String nembas;
    private String ocpbas;
    private String ocmbas;
    private String sqlbkr;

    public String getNroids() {
        return (this.nroids);
    }

    public String getCbysys() {
        return (this.cbysys);
    }

    public Date getConsys() {
        return (this.consys);
    }

    public String getUbysys() {
        return (this.ubysys);
    }

    public Date getUonsys() {
        return (this.uonsys);
    }

    public String getCodcia() {
        return (this.codcia);
    }

    public String getTitprs() {
        return (this.titprs);
    }

    public String getCodprs() {
        return (this.codprs);
    }

    public String getApeprs() {
        return (this.apeprs);
    }

    public String getNomprs() {
        return (this.nomprs);
    }

    public String getNpqprs() {
        return (this.npqprs);
    }

    public String getNacprs() {
        return (this.nacprs);
    }

    public Date getFhnprs() {
        return (this.fhnprs);
    }

    public String getIdeprs() {
        return (this.ideprs);
    }

    public String getNriprs() {
        return (this.nriprs);
    }

    public String getEpsprs() {
        return (this.epsprs);
    }

    public String getSngprs() {
        return (this.sngprs);
    }

    public String getCivprs() {
        return (this.civprs);
    }

    public String getSexprs() {
        return (this.sexprs);
    }

    public String getDirprs() {
        return (this.dirprs);
    }

    public String getTelprs() {
        return (this.telprs);
    }

    public String getCelprs() {
        return (this.celprs);
    }

    public String getCodciu() {
        return (this.codciu);
    }

    public String getEmlprs() {
        return (this.emlprs);
    }

    public String getPswprs() {
        return (this.pswprs);
    }

    public String getExpprs() {
        return (this.expprs);
    }

    public String getEskprs() {
        return (this.eskprs);
    }

    public String getEstprs() {
        return (this.estprs);
    }

    public String getSnpprs() {
        return (this.snpprs);
    }

    public Date getFsnprs() {
        return (this.fsnprs);
    }

    public String getNroxvt() {
        return (this.nroxvt);
    }

    public double getPsnprs() {
        return (this.psnprs);
    }

    public String getStdbas() {
        return (this.stdbas);
    }

    public String getMilbas() {
        return (this.milbas);
    }

    public String getNrmbas() {
        return (this.nrmbas);
    }

    public int getNdmbas() {
        return (this.ndmbas);
    }

    public int getCtgbas() {
        return (this.ctgbas);
    }

    public String getModicf() {
        return (this.modicf);
    }

    public double getPtjicf() {
        return (this.ptjicf);
    }

    public String getKgpbas() {
        return (this.kgpbas);
    }

    public int getAbpbas() {
        return (this.abpbas);
    }

    public int getAepbas() {
        return (this.aepbas);
    }

    public String getCodklg() {
        return (this.codklg);
    }

    public int getAbsbas() {
        return (this.absbas);
    }

    public int getAesbas() {
        return (this.aesbas);
    }

    public String getDifbas() {
        return (this.difbas);
    }

    public String getTefbas() {
        return (this.tefbas);
    }

    public String getKlcbas() {
        return (this.klcbas);
    }

    public String getKtgbas() {
        return (this.ktgbas);
    }

    public String getBkabas() {
        return (this.bkabas);
    }

    public String getNropgm() {
        return (this.nropgm);
    }

    public String getAkdbas() {
        return (this.akdbas);
    }

    public String getNropsm() {
        return (this.nropsm);
    }

    public String getSdtbas() {
        return (this.sdtbas);
    }

    public String getMtrbas() {
        return (this.mtrbas);
    }

    public String getBibbas() {
        return (this.bibbas);
    }

    public String getNgrbas() {
        return (this.ngrbas);
    }

    public String getBntbas() {
        return (this.bntbas);
    }

    public String getPagbas() {
        return (this.pagbas);
    }

    public String getGrdbas() {
        return (this.grdbas);
    }

    public double getPtjeke() {
        return (this.ptjeke);
    }

    public String getSmtpsm() {
        return (this.smtpsm);
    }

    public String getPswbas() {
        return (this.pswbas);
    }

    public int getCrrakd() {
        return (this.crrakd);
    }

    public double getPrmbas() {
        return (this.prmbas);
    }

    public double getPrqbas() {
        return (this.prqbas);
    }

    public String getSnpbas() {
        return (this.snpbas);
    }

    public String getCodspc() {
        return (this.codspc);
    }

    public String getAgnprs() {
        return (this.agnprs);
    }

    public String getPrdprs() {
        return (this.prdprs);
    }

    public String getCodcst() {
        return (this.codcst);
    }

    public String getCpsbas() {
        return (this.cpsbas);
    }

    public String getNrocrs() {
        return (this.nrocrs);
    }

    public String getAgnngr() {
        return (this.agnngr);
    }

    public String getPrdngr() {
        return (this.prdngr);
    }

    public double getNgsbas() {
        return (this.ngsbas);
    }

    public String getStrbas() {
        return (this.strbas);
    }

    public String getCodcrs() {
        return (this.codcrs);
    }

    public String getEtnbas() {
        return (this.etnbas);
    }

    public String getPlnbas() {
        return (this.plnbas);
    }

    public String getDisbas() {
        return (this.disbas);
    }

    public int getSbnbas() {
        return (this.sbnbas);
    }

    public String getNrojob() {
        return (this.nrojob);
    }

    public String getVivbas() {
        return (this.vivbas);
    }

    public String getDudviv() {
        return (this.dudviv);
    }

    public int getPhrbas() {
        return (this.phrbas);
    }

    public double getPsnklg() {
        return (this.psnklg);
    }

    public String getSdebas() {
        return (this.sdebas);
    }

    public int getPkgbas() {
        return (this.pkgbas);
    }

    public int getRgnbas() {
        return (this.rgnbas);
    }

    public double getPlqbas() {
        return (this.plqbas);
    }

    public String getNropte() {
        return (this.nropte);
    }

    public String getCodrgl() {
        return (this.codrgl);
    }

    public String getNepbas() {
        return (this.nepbas);
    }

    public String getNembas() {
        return (this.nembas);
    }

    public String getOcpbas() {
        return (this.ocpbas);
    }

    public String getOcmbas() {
        return (this.ocmbas);
    }

    public String getSqlbkr() {
        return (this.sqlbkr);
    }

    public void setNroids(String nroids) {
        this.nroids = nroids;
    }

    public void setCbysys(String cbysys) {
        this.cbysys = cbysys;
    }

    public void setConsys(Date consys) {
        this.consys = consys;
    }

    public void setUbysys(String ubysys) {
        this.ubysys = ubysys;
    }

    public void setUonsys(Date uonsys) {
        this.uonsys = uonsys;
    }

    public void setCodcia(String codcia) {
        this.codcia = codcia;
    }

    public void setTitprs(String titprs) {
        this.titprs = titprs;
    }

    public void setCodprs(String codprs) {
        this.codprs = codprs;
    }

    public void setApeprs(String apeprs) {
        this.apeprs = apeprs;
    }

    public void setNomprs(String nomprs) {
        this.nomprs = nomprs;
    }

    public void setNpqprs(String npqprs) {
        this.npqprs = npqprs;
    }

    public void setNacprs(String nacprs) {
        this.nacprs = nacprs;
    }

    public void setFhnprs(Date fhnprs) {
        this.fhnprs = fhnprs;
    }

    public void setIdeprs(String ideprs) {
        this.ideprs = ideprs;
    }

    public void setNriprs(String nriprs) {
        this.nriprs = nriprs;
    }

    public void setEpsprs(String epsprs) {
        this.epsprs = epsprs;
    }

    public void setSngprs(String sngprs) {
        this.sngprs = sngprs;
    }

    public void setCivprs(String civprs) {
        this.civprs = civprs;
    }

    public void setSexprs(String sexprs) {
        this.sexprs = sexprs;
    }

    public void setDirprs(String dirprs) {
        this.dirprs = dirprs;
    }

    public void setTelprs(String telprs) {
        this.telprs = telprs;
    }

    public void setCelprs(String celprs) {
        this.celprs = celprs;
    }

    public void setCodciu(String codciu) {
        this.codciu = codciu;
    }

    public void setEmlprs(String emlprs) {
        this.emlprs = emlprs;
    }

    public void setPswprs(String pswprs) {
        this.pswprs = pswprs;
    }

    public void setExpprs(String expprs) {
        this.expprs = expprs;
    }

    public void setEskprs(String eskprs) {
        this.eskprs = eskprs;
    }

    public void setEstprs(String estprs) {
        this.estprs = estprs;
    }

    public void setSnpprs(String snpprs) {
        this.snpprs = snpprs;
    }

    public void setFsnprs(Date fsnprs) {
        this.fsnprs = fsnprs;
    }

    public void setNroxvt(String nroxvt) {
        this.nroxvt = nroxvt;
    }

    public void setPsnprs(double psnprs) {
        this.psnprs = psnprs;
    }

    public void setStdbas(String stdbas) {
        this.stdbas = stdbas;
    }

    public void setMilbas(String milbas) {
        this.milbas = milbas;
    }

    public void setNrmbas(String nrmbas) {
        this.nrmbas = nrmbas;
    }

    public void setNdmbas(int ndmbas) {
        this.ndmbas = ndmbas;
    }

    public void setCtgbas(int ctgbas) {
        this.ctgbas = ctgbas;
    }

    public void setModicf(String modicf) {
        this.modicf = modicf;
    }

    public void setPtjicf(double ptjicf) {
        this.ptjicf = ptjicf;
    }

    public void setKgpbas(String kgpbas) {
        this.kgpbas = kgpbas;
    }

    public void setAbpbas(int abpbas) {
        this.abpbas = abpbas;
    }

    public void setAepbas(int aepbas) {
        this.aepbas = aepbas;
    }

    public void setCodklg(String codklg) {
        this.codklg = codklg;
    }

    public void setAbsbas(int absbas) {
        this.absbas = absbas;
    }

    public void setAesbas(int aesbas) {
        this.aesbas = aesbas;
    }

    public void setDifbas(String difbas) {
        this.difbas = difbas;
    }

    public void setTefbas(String tefbas) {
        this.tefbas = tefbas;
    }

    public void setKlcbas(String klcbas) {
        this.klcbas = klcbas;
    }

    public void setKtgbas(String ktgbas) {
        this.ktgbas = ktgbas;
    }

    public void setBkabas(String bkabas) {
        this.bkabas = bkabas;
    }

    public void setNropgm(String nropgm) {
        this.nropgm = nropgm;
    }

    public void setAkdbas(String akdbas) {
        this.akdbas = akdbas;
    }

    public void setNropsm(String nropsm) {
        this.nropsm = nropsm;
    }

    public void setSdtbas(String sdtbas) {
        this.sdtbas = sdtbas;
    }

    public void setMtrbas(String mtrbas) {
        this.mtrbas = mtrbas;
    }

    public void setBibbas(String bibbas) {
        this.bibbas = bibbas;
    }

    public void setNgrbas(String ngrbas) {
        this.ngrbas = ngrbas;
    }

    public void setBntbas(String bntbas) {
        this.bntbas = bntbas;
    }

    public void setPagbas(String pagbas) {
        this.pagbas = pagbas;
    }

    public void setGrdbas(String grdbas) {
        this.grdbas = grdbas;
    }

    public void setPtjeke(double ptjeke) {
        this.ptjeke = ptjeke;
    }

    public void setSmtpsm(String smtpsm) {
        this.smtpsm = smtpsm;
    }

    public void setPswbas(String pswbas) {
        this.pswbas = pswbas;
    }

    public void setCrrakd(int crrakd) {
        this.crrakd = crrakd;
    }

    public void setPrmbas(double prmbas) {
        this.prmbas = prmbas;
    }

    public void setPrqbas(double prqbas) {
        this.prqbas = prqbas;
    }

    public void setSnpbas(String snpbas) {
        this.snpbas = snpbas;
    }

    public void setCodspc(String codspc) {
        this.codspc = codspc;
    }

    public void setAgnprs(String agnprs) {
        this.agnprs = agnprs;
    }

    public void setPrdprs(String prdprs) {
        this.prdprs = prdprs;
    }

    public void setCodcst(String codcst) {
        this.codcst = codcst;
    }

    public void setCpsbas(String cpsbas) {
        this.cpsbas = cpsbas;
    }

    public void setNrocrs(String nrocrs) {
        this.nrocrs = nrocrs;
    }

    public void setAgnngr(String agnngr) {
        this.agnngr = agnngr;
    }

    public void setPrdngr(String prdngr) {
        this.prdngr = prdngr;
    }

    public void setNgsbas(double ngsbas) {
        this.ngsbas = ngsbas;
    }

    public void setStrbas(String strbas) {
        this.strbas = strbas;
    }

    public void setCodcrs(String codcrs) {
        this.codcrs = codcrs;
    }

    public void setEtnbas(String etnbas) {
        this.etnbas = etnbas;
    }

    public void setPlnbas(String plnbas) {
        this.plnbas = plnbas;
    }

    public void setDisbas(String disbas) {
        this.disbas = disbas;
    }

    public void setSbnbas(int sbnbas) {
        this.sbnbas = sbnbas;
    }

    public void setNrojob(String nrojob) {
        this.nrojob = nrojob;
    }

    public void setVivbas(String vivbas) {
        this.vivbas = vivbas;
    }

    public void setDudviv(String dudviv) {
        this.dudviv = dudviv;
    }

    public void setPhrbas(int phrbas) {
        this.phrbas = phrbas;
    }

    public void setPsnklg(double psnklg) {
        this.psnklg = psnklg;
    }

    public void setSdebas(String sdebas) {
        this.sdebas = sdebas;
    }

    public void setPkgbas(int pkgbas) {
        this.pkgbas = pkgbas;
    }

    public void setRgnbas(int rgnbas) {
        this.rgnbas = rgnbas;
    }

    public void setPlqbas(double plqbas) {
        this.plqbas = plqbas;
    }

    public void setNropte(String nropte) {
        this.nropte = nropte;
    }

    public void setCodrgl(String codrgl) {
        this.codrgl = codrgl;
    }

    public void setNepbas(String nepbas) {
        this.nepbas = nepbas;
    }

    public void setNembas(String nembas) {
        this.nembas = nembas;
    }

    public void setOcpbas(String ocpbas) {
        this.ocpbas = ocpbas;
    }

    public void setOcmbas(String ocmbas) {
        this.ocmbas = ocmbas;
    }

    public void setSqlbkr(String sqlbkr) {
        this.sqlbkr = sqlbkr;
    }

    public static Smabas load(ResultSet rst) throws SQLException {

        Smabas smabas = new Smabas();

        smabas.setNroids(rst.getString(1));
        smabas.setCbysys(rst.getString(2));
        smabas.setConsys(rst.getDate(3));
        smabas.setUbysys(rst.getString(4));
        smabas.setUonsys(rst.getDate(5));
        smabas.setCodcia(rst.getString(6));
        smabas.setTitprs(rst.getString(7));
        smabas.setCodprs(rst.getString(8));
        smabas.setApeprs(rst.getString(9));
        smabas.setNomprs(rst.getString(10));
        smabas.setNpqprs(rst.getString(11));
        smabas.setNacprs(rst.getString(12));
        smabas.setFhnprs(rst.getDate(13));
        smabas.setIdeprs(rst.getString(14));
        smabas.setNriprs(rst.getString(15));
        smabas.setEpsprs(rst.getString(16));
        smabas.setSngprs(rst.getString(17));
        smabas.setCivprs(rst.getString(18));
        smabas.setSexprs(rst.getString(19));
        smabas.setDirprs(rst.getString(20));
        smabas.setTelprs(rst.getString(21));
        smabas.setCelprs(rst.getString(22));
        smabas.setCodciu(rst.getString(23));
        smabas.setEmlprs(rst.getString(24));
        smabas.setPswprs(rst.getString(25));
        smabas.setExpprs(rst.getString(26));
        smabas.setEskprs(rst.getString(27));
        smabas.setEstprs(rst.getString(28));
        smabas.setSnpprs(rst.getString(29));
        smabas.setFsnprs(rst.getDate(30));
        smabas.setNroxvt(rst.getString(31));
        smabas.setPsnprs(rst.getDouble(32));
        smabas.setStdbas(rst.getString(33));
        smabas.setMilbas(rst.getString(34));
        smabas.setNrmbas(rst.getString(35));
        smabas.setNdmbas(rst.getInt(36));
        smabas.setCtgbas(rst.getInt(37));
        smabas.setModicf(rst.getString(38));
        smabas.setPtjicf(rst.getDouble(39));
        smabas.setKgpbas(rst.getString(40));
        smabas.setAbpbas(rst.getInt(41));
        smabas.setAepbas(rst.getInt(42));
        smabas.setCodklg(rst.getString(43));
        smabas.setAbsbas(rst.getInt(44));
        smabas.setAesbas(rst.getInt(45));
        smabas.setDifbas(rst.getString(46));
        smabas.setTefbas(rst.getString(47));
        smabas.setKlcbas(rst.getString(48));
        smabas.setKtgbas(rst.getString(49));
        smabas.setBkabas(rst.getString(50));
        smabas.setNropgm(rst.getString(51));
        smabas.setAkdbas(rst.getString(52));
        smabas.setNropsm(rst.getString(53));
        smabas.setSdtbas(rst.getString(54));
        smabas.setMtrbas(rst.getString(55));
        smabas.setBibbas(rst.getString(56));
        smabas.setNgrbas(rst.getString(57));
        smabas.setBntbas(rst.getString(58));
        smabas.setPagbas(rst.getString(59));
        smabas.setGrdbas(rst.getString(60));
        smabas.setPtjeke(rst.getDouble(61));
        smabas.setSmtpsm(rst.getString(62));
        smabas.setPswbas(rst.getString(63));
        smabas.setCrrakd(rst.getInt(64));
        smabas.setPrmbas(rst.getDouble(65));
        smabas.setPrqbas(rst.getDouble(66));
        smabas.setSnpbas(rst.getString(67));
        smabas.setCodspc(rst.getString(68));
        smabas.setAgnprs(rst.getString(69));
        smabas.setPrdprs(rst.getString(70));
        smabas.setCodcst(rst.getString(71));
        smabas.setCpsbas(rst.getString(72));
        smabas.setNrocrs(rst.getString(73));
        smabas.setAgnngr(rst.getString(74));
        smabas.setPrdngr(rst.getString(75));
        smabas.setNgsbas(rst.getDouble(76));
        smabas.setStrbas(rst.getString(77));
        smabas.setCodcrs(rst.getString(78));
        smabas.setEtnbas(rst.getString(79));
        smabas.setPlnbas(rst.getString(80));
        smabas.setDisbas(rst.getString(81));
        smabas.setSbnbas(rst.getInt(82));
        smabas.setNrojob(rst.getString(83));
        smabas.setVivbas(rst.getString(84));
        smabas.setDudviv(rst.getString(85));
        smabas.setPhrbas(rst.getInt(86));
        smabas.setPsnklg(rst.getDouble(87));
        smabas.setSdebas(rst.getString(88));
        smabas.setPkgbas(rst.getInt(89));
        smabas.setRgnbas(rst.getInt(90));
        smabas.setPlqbas(rst.getDouble(91));
        smabas.setNropte(rst.getString(92));
        smabas.setCodrgl(rst.getString(93));
        smabas.setNepbas(rst.getString(94));
        smabas.setNembas(rst.getString(95));
        smabas.setOcpbas(rst.getString(96));
        smabas.setOcmbas(rst.getString(97));
        smabas.setSqlbkr(rst.getString(98));

        return smabas;

    }

}
