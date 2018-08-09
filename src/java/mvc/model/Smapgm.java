package mvc.model;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Smapgm{

	private String idepgm;
	private String codcia;
	private String nropgm;
	private String codpgm;
	private String nompgm;
	private String tpopgm;
	private String stdpgm;
	private String npqpgm;
	private String nropsm;
	private String codfac;
	private String jndpgm;
	private String telpgm;
	private String extpgm;
	private Date fchpgm;
	private int smtpgm;
	private String krdprs;
	private String akdprs;
	private String strprs;
	private String rslpgm;
	private String codcst;
	private String codcnv;
	private String codrgs;
	private String tponrm;
	private String nronrm;
	private Date fchnrm;
	private String codorg;
	private String stgpgm;
	private String titpgm;
	private String prdpgm;
	private int kpcpgm;
	private int crdakd;
	private String codciu;
	private String paspgm;
	private String pgrpgm;
	private String msopgm;
	private String vsopgm;
	private String obgpgm;
	private String objpgm;
	private String nroqnt;
	private String codbko;
	private String sqlbkr;


	public String getIdepgm(){
		return (this.idepgm);
	}

	public String getCodcia(){
		return (this.codcia);
	}

	public String getNropgm(){
		return (this.nropgm);
	}

	public String getCodpgm(){
		return (this.codpgm);
	}

	public String getNompgm(){
		return (this.nompgm);
	}

	public String getTpopgm(){
		return (this.tpopgm);
	}

	public String getStdpgm(){
		return (this.stdpgm);
	}

	public String getNpqpgm(){
		return (this.npqpgm);
	}

	public String getNropsm(){
		return (this.nropsm);
	}

	public String getCodfac(){
		return (this.codfac);
	}

	public String getJndpgm(){
		return (this.jndpgm);
	}

	public String getTelpgm(){
		return (this.telpgm);
	}

	public String getExtpgm(){
		return (this.extpgm);
	}

	public Date getFchpgm(){
		return (this.fchpgm);
	}

	public int getSmtpgm(){
		return (this.smtpgm);
	}

	public String getKrdprs(){
		return (this.krdprs);
	}

	public String getAkdprs(){
		return (this.akdprs);
	}

	public String getStrprs(){
		return (this.strprs);
	}

	public String getRslpgm(){
		return (this.rslpgm);
	}

	public String getCodcst(){
		return (this.codcst);
	}

	public String getCodcnv(){
		return (this.codcnv);
	}

	public String getCodrgs(){
		return (this.codrgs);
	}

	public String getTponrm(){
		return (this.tponrm);
	}

	public String getNronrm(){
		return (this.nronrm);
	}

	public Date getFchnrm(){
		return (this.fchnrm);
	}

	public String getCodorg(){
		return (this.codorg);
	}

	public String getStgpgm(){
		return (this.stgpgm);
	}

	public String getTitpgm(){
		return (this.titpgm);
	}

	public String getPrdpgm(){
		return (this.prdpgm);
	}

	public int getKpcpgm(){
		return (this.kpcpgm);
	}

	public int getCrdakd(){
		return (this.crdakd);
	}

	public String getCodciu(){
		return (this.codciu);
	}

	public String getPaspgm(){
		return (this.paspgm);
	}

	public String getPgrpgm(){
		return (this.pgrpgm);
	}

	public String getMsopgm(){
		return (this.msopgm);
	}

	public String getVsopgm(){
		return (this.vsopgm);
	}

	public String getObgpgm(){
		return (this.obgpgm);
	}

	public String getObjpgm(){
		return (this.objpgm);
	}

	public String getNroqnt(){
		return (this.nroqnt);
	}

	public String getCodbko(){
		return (this.codbko);
	}

	public String getSqlbkr(){
		return (this.sqlbkr);
	}



	public void setIdepgm(String idepgm){
		this.idepgm=idepgm;
	}

	public void setCodcia(String codcia){
		this.codcia=codcia;
	}

	public void setNropgm(String nropgm){
		this.nropgm=nropgm;
	}

	public void setCodpgm(String codpgm){
		this.codpgm=codpgm;
	}

	public void setNompgm(String nompgm){
		this.nompgm=nompgm;
	}

	public void setTpopgm(String tpopgm){
		this.tpopgm=tpopgm;
	}

	public void setStdpgm(String stdpgm){
		this.stdpgm=stdpgm;
	}

	public void setNpqpgm(String npqpgm){
		this.npqpgm=npqpgm;
	}

	public void setNropsm(String nropsm){
		this.nropsm=nropsm;
	}

	public void setCodfac(String codfac){
		this.codfac=codfac;
	}

	public void setJndpgm(String jndpgm){
		this.jndpgm=jndpgm;
	}

	public void setTelpgm(String telpgm){
		this.telpgm=telpgm;
	}

	public void setExtpgm(String extpgm){
		this.extpgm=extpgm;
	}

	public void setFchpgm(Date fchpgm){
		this.fchpgm=fchpgm;
	}

	public void setSmtpgm(int smtpgm){
		this.smtpgm=smtpgm;
	}

	public void setKrdprs(String krdprs){
		this.krdprs=krdprs;
	}

	public void setAkdprs(String akdprs){
		this.akdprs=akdprs;
	}

	public void setStrprs(String strprs){
		this.strprs=strprs;
	}

	public void setRslpgm(String rslpgm){
		this.rslpgm=rslpgm;
	}

	public void setCodcst(String codcst){
		this.codcst=codcst;
	}

	public void setCodcnv(String codcnv){
		this.codcnv=codcnv;
	}

	public void setCodrgs(String codrgs){
		this.codrgs=codrgs;
	}

	public void setTponrm(String tponrm){
		this.tponrm=tponrm;
	}

	public void setNronrm(String nronrm){
		this.nronrm=nronrm;
	}

	public void setFchnrm(Date fchnrm){
		this.fchnrm=fchnrm;
	}

	public void setCodorg(String codorg){
		this.codorg=codorg;
	}

	public void setStgpgm(String stgpgm){
		this.stgpgm=stgpgm;
	}

	public void setTitpgm(String titpgm){
		this.titpgm=titpgm;
	}

	public void setPrdpgm(String prdpgm){
		this.prdpgm=prdpgm;
	}

	public void setKpcpgm(int kpcpgm){
		this.kpcpgm=kpcpgm;
	}

	public void setCrdakd(int crdakd){
		this.crdakd=crdakd;
	}

	public void setCodciu(String codciu){
		this.codciu=codciu;
	}

	public void setPaspgm(String paspgm){
		this.paspgm=paspgm;
	}

	public void setPgrpgm(String pgrpgm){
		this.pgrpgm=pgrpgm;
	}

	public void setMsopgm(String msopgm){
		this.msopgm=msopgm;
	}

	public void setVsopgm(String vsopgm){
		this.vsopgm=vsopgm;
	}

	public void setObgpgm(String obgpgm){
		this.obgpgm=obgpgm;
	}

	public void setObjpgm(String objpgm){
		this.objpgm=objpgm;
	}

	public void setNroqnt(String nroqnt){
		this.nroqnt=nroqnt;
	}

	public void setCodbko(String codbko){
		this.codbko=codbko;
	}

	public void setSqlbkr(String sqlbkr){
		this.sqlbkr=sqlbkr;
	}


	public static Smapgm load(ResultSet rst)throws SQLException{

		Smapgm smapgm= new Smapgm();

		smapgm.setIdepgm(rst.getString(1));
		smapgm.setCodcia(rst.getString(2));
		smapgm.setNropgm(rst.getString(3));
		smapgm.setCodpgm(rst.getString(4));
		smapgm.setNompgm(rst.getString(5));
		smapgm.setTpopgm(rst.getString(6));
		smapgm.setStdpgm(rst.getString(7));
		smapgm.setNpqpgm(rst.getString(8));
		smapgm.setNropsm(rst.getString(9));
		smapgm.setCodfac(rst.getString(10));
		smapgm.setJndpgm(rst.getString(11));
		smapgm.setTelpgm(rst.getString(12));
		smapgm.setExtpgm(rst.getString(13));
		smapgm.setFchpgm(rst.getDate(14));
		smapgm.setSmtpgm(rst.getInt(15));
		smapgm.setKrdprs(rst.getString(16));
		smapgm.setAkdprs(rst.getString(17));
		smapgm.setStrprs(rst.getString(18));
		smapgm.setRslpgm(rst.getString(19));
		smapgm.setCodcst(rst.getString(20));
		smapgm.setCodcnv(rst.getString(21));
		smapgm.setCodrgs(rst.getString(22));
		smapgm.setTponrm(rst.getString(23));
		smapgm.setNronrm(rst.getString(24));
		smapgm.setFchnrm(rst.getDate(25));
		smapgm.setCodorg(rst.getString(26));
		smapgm.setStgpgm(rst.getString(27));
		smapgm.setTitpgm(rst.getString(28));
		smapgm.setPrdpgm(rst.getString(29));
		smapgm.setKpcpgm(rst.getInt(30));
		smapgm.setCrdakd(rst.getInt(31));
		smapgm.setCodciu(rst.getString(32));
		smapgm.setPaspgm(rst.getString(33));
		smapgm.setPgrpgm(rst.getString(34));
		smapgm.setMsopgm(rst.getString(35));
		smapgm.setVsopgm(rst.getString(36));
		smapgm.setObgpgm(rst.getString(37));
		smapgm.setObjpgm(rst.getString(38));
		smapgm.setNroqnt(rst.getString(39));
		smapgm.setCodbko(rst.getString(40));
		smapgm.setSqlbkr(rst.getString(41));

		return smapgm;

	}

}
//end Class