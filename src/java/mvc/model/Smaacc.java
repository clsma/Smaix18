package mvc.model;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Smaacc{ 

	private String nroacc;
	private String codcia;
	private String codprs;
	private String dspacc;
	private String nomdbf;
	private int scracc;
	private int insacc;
	private int edtacc;
	private int delacc; 
	private String sqlbkr;


	public String getNroacc(){
		return (this.nroacc);
	}

	public String getCodcia(){
		return (this.codcia);
	}

	public String getCodprs(){
		return (this.codprs);
	}

	public String getDspacc(){
		return (this.dspacc);
	}

	public String getNomdbf(){
		return (this.nomdbf);
	}

	public int getScracc(){
		return (this.scracc);
	}

	public int getInsacc(){
		return (this.insacc);
	}

	public int getEdtacc(){
		return (this.edtacc);
	}

	public int getDelacc(){
		return (this.delacc);
	}

	public String getSqlbkr(){
		return (this.sqlbkr);
	}



	public void setNroacc(String nroacc){
		this.nroacc=nroacc;
	}

	public void setCodcia(String codcia){
		this.codcia=codcia;
	}

	public void setCodprs(String codprs){
		this.codprs=codprs;
	}

	public void setDspacc(String dspacc){
		this.dspacc=dspacc;
	}

	public void setNomdbf(String nomdbf){
		this.nomdbf=nomdbf;
	}

	public void setScracc(int scracc){
		this.scracc=scracc;
	}

	public void setInsacc(int insacc){
		this.insacc=insacc;
	}

	public void setEdtacc(int edtacc){
		this.edtacc=edtacc;
	}

	public void setDelacc(int delacc){
		this.delacc=delacc;
	}

	public void setSqlbkr(String sqlbkr){
		this.sqlbkr=sqlbkr;
	}


	public static Smaacc load(ResultSet rst)throws SQLException{

		Smaacc smaacc= new Smaacc();

		smaacc.setNroacc(rst.getString(1));
		smaacc.setCodcia(rst.getString(2));
		smaacc.setCodprs(rst.getString(3));
		smaacc.setDspacc(rst.getString(4));
		smaacc.setNomdbf(rst.getString(5));
		smaacc.setScracc(rst.getInt(6));
		smaacc.setInsacc(rst.getInt(7));
		smaacc.setEdtacc(rst.getInt(8));
		smaacc.setDelacc(rst.getInt(9));
		smaacc.setSqlbkr(rst.getString(10));

		return smaacc;

	}

}