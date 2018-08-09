package mvc.model;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Smaofr{

	private String codcia;
	private String agnprs;
	private String prdprs;
	private String codofr;
	private String tpoofr;
	private String nroofr;
	private int qpoofr;
	private String tpopgm;
	private int sndeml;
	private int insofr;
	private int preofr;
	private int slcofr;
	private int mtrofr;
	private String nomofr;
	private String obsofr;
	private Date bgnofr;
	private Date endofr;
	private String nrodqr;
	private double vlrofr;
	private String stdofr;
	private Date bgsofr;
	private Date ensofr;
	private int pinofr;
	private String sqlbkr;


	public String getCodcia(){
		return (this.codcia);
	}

	public String getAgnprs(){
		return (this.agnprs);
	}

	public String getPrdprs(){
		return (this.prdprs);
	}

	public String getCodofr(){
		return (this.codofr);
	}

	public String getTpoofr(){
		return (this.tpoofr);
	}

	public String getNroofr(){
		return (this.nroofr);
	}

	public int getQpoofr(){
		return (this.qpoofr);
	}

	public String getTpopgm(){
		return (this.tpopgm);
	}

	public int getSndeml(){
		return (this.sndeml);
	}

	public int getInsofr(){
		return (this.insofr);
	}

	public int getPreofr(){
		return (this.preofr);
	}

	public int getSlcofr(){
		return (this.slcofr);
	}

	public int getMtrofr(){
		return (this.mtrofr);
	}

	public String getNomofr(){
		return (this.nomofr);
	}

	public String getObsofr(){
		return (this.obsofr);
	}

	public Date getBgnofr(){
		return (this.bgnofr);
	}

	public Date getEndofr(){
		return (this.endofr);
	}

	public String getNrodqr(){
		return (this.nrodqr);
	}

	public double getVlrofr(){
		return (this.vlrofr);
	}

	public String getStdofr(){
		return (this.stdofr);
	}

	public Date getBgsofr(){
		return (this.bgsofr);
	}

	public Date getEnsofr(){
		return (this.ensofr);
	}

	public int getPinofr(){
		return (this.pinofr);
	}

	public String getSqlbkr(){
		return (this.sqlbkr);
	}



	public void setCodcia(String codcia){
		this.codcia=codcia;
	}

	public void setAgnprs(String agnprs){
		this.agnprs=agnprs;
	}

	public void setPrdprs(String prdprs){
		this.prdprs=prdprs;
	}

	public void setCodofr(String codofr){
		this.codofr=codofr;
	}

	public void setTpoofr(String tpoofr){
		this.tpoofr=tpoofr;
	}

	public void setNroofr(String nroofr){
		this.nroofr=nroofr;
	}

	public void setQpoofr(int qpoofr){
		this.qpoofr=qpoofr;
	}

	public void setTpopgm(String tpopgm){
		this.tpopgm=tpopgm;
	}

	public void setSndeml(int sndeml){
		this.sndeml=sndeml;
	}

	public void setInsofr(int insofr){
		this.insofr=insofr;
	}

	public void setPreofr(int preofr){
		this.preofr=preofr;
	}

	public void setSlcofr(int slcofr){
		this.slcofr=slcofr;
	}

	public void setMtrofr(int mtrofr){
		this.mtrofr=mtrofr;
	}

	public void setNomofr(String nomofr){
		this.nomofr=nomofr;
	}

	public void setObsofr(String obsofr){
		this.obsofr=obsofr;
	}

	public void setBgnofr(Date bgnofr){
		this.bgnofr=bgnofr;
	}

	public void setEndofr(Date endofr){
		this.endofr=endofr;
	}

	public void setNrodqr(String nrodqr){
		this.nrodqr=nrodqr;
	}

	public void setVlrofr(double vlrofr){
		this.vlrofr=vlrofr;
	}

	public void setStdofr(String stdofr){
		this.stdofr=stdofr;
	}

	public void setBgsofr(Date bgsofr){
		this.bgsofr=bgsofr;
	}

	public void setEnsofr(Date ensofr){
		this.ensofr=ensofr;
	}

	public void setPinofr(int pinofr){
		this.pinofr=pinofr;
	}

	public void setSqlbkr(String sqlbkr){
		this.sqlbkr=sqlbkr;
	}


	public static Smaofr load(ResultSet rst)throws SQLException{

		Smaofr smaofr= new Smaofr();

		smaofr.setCodcia(rst.getString(1));
		smaofr.setAgnprs(rst.getString(2));
		smaofr.setPrdprs(rst.getString(3));
		smaofr.setCodofr(rst.getString(4));
		smaofr.setTpoofr(rst.getString(5));
		smaofr.setNroofr(rst.getString(6));
		smaofr.setQpoofr(rst.getInt(7));
		smaofr.setTpopgm(rst.getString(8));
		smaofr.setSndeml(rst.getInt(9));
		smaofr.setInsofr(rst.getInt(10));
		smaofr.setPreofr(rst.getInt(11));
		smaofr.setSlcofr(rst.getInt(12));
		smaofr.setMtrofr(rst.getInt(13));
		smaofr.setNomofr(rst.getString(14));
		smaofr.setObsofr(rst.getString(15));
		smaofr.setBgnofr(rst.getDate(16));
		smaofr.setEndofr(rst.getDate(17));
		smaofr.setNrodqr(rst.getString(18));
		smaofr.setVlrofr(rst.getDouble(19));
		smaofr.setStdofr(rst.getString(20));
		smaofr.setBgsofr(rst.getDate(21));
		smaofr.setEnsofr(rst.getDate(22));
		smaofr.setPinofr(rst.getInt(23));
		smaofr.setSqlbkr(rst.getString(24));

		return smaofr;

	}

}