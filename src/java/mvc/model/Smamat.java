package mvc.model;

import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Smamat{

	private String codcia;
	private String codmat;
	private String nommat;
	private String npqmat;
	private String codare;
	private int crdmat;
	private double pjamat;
	private double pjbmat;
	private double pjcmat;
	private double pjdmat; 
	private int teomat;
	private int pramat;
	private int invmat;
	private String tipaul;
	private String tpomat;
	private String klcmat;
	private int ntsmat;
	private int habmat;
	private int lblmat;
	private int knpmat;
	private int intmat;
	private int smnmat;
	private int rtcmat;
	private double vtrmat;
	private int clrmat;
	private int ngrmat;
	private String codmdl;
	private String sqlbkr;


	public String getCodcia(){
		return (this.codcia);
	}

	public String getCodmat(){
		return (this.codmat);
	}

	public String getNommat(){
		return (this.nommat);
	}

	public String getNpqmat(){
		return (this.npqmat);
	}

	public String getCodare(){
		return (this.codare);
	}

	public int getCrdmat(){
		return (this.crdmat);
	}

	public double getPjamat(){
		return (this.pjamat);
	}

	public double getPjbmat(){
		return (this.pjbmat);
	}

	public double getPjcmat(){
		return (this.pjcmat);
	}

	public double getPjdmat(){
		return (this.pjdmat);
	}

	public int getTeomat(){
		return (this.teomat);
	}

	public int getPramat(){
		return (this.pramat);
	}

	public int getInvmat(){
		return (this.invmat);
	}

	public String getTipaul(){
		return (this.tipaul);
	}

	public String getTpomat(){
		return (this.tpomat);
	}

	public String getKlcmat(){
		return (this.klcmat);
	}

	public int getNtsmat(){
		return (this.ntsmat);
	}

	public int getHabmat(){
		return (this.habmat);
	}

	public int getLblmat(){
		return (this.lblmat);
	}

	public int getKnpmat(){
		return (this.knpmat);
	}

	public int getIntmat(){
		return (this.intmat);
	}

	public int getSmnmat(){
		return (this.smnmat);
	}

	public int getRtcmat(){
		return (this.rtcmat);
	}

	public double getVtrmat(){
		return (this.vtrmat);
	}

	public int getClrmat(){
		return (this.clrmat);
	}

	public int getNgrmat(){
		return (this.ngrmat);
	}

	public String getCodmdl(){
		return (this.codmdl);
	}

	public String getSqlbkr(){
		return (this.sqlbkr);
	}



	public void setCodcia(String codcia){
		this.codcia=codcia;
	}

	public void setCodmat(String codmat){
		this.codmat=codmat;
	}

	public void setNommat(String nommat){
		this.nommat=nommat;
	}

	public void setNpqmat(String npqmat){
		this.npqmat=npqmat;
	}

	public void setCodare(String codare){
		this.codare=codare;
	}

	public void setCrdmat(int crdmat){
		this.crdmat=crdmat;
	}

	public void setPjamat(double pjamat){
		this.pjamat=pjamat;
	}

	public void setPjbmat(double pjbmat){
		this.pjbmat=pjbmat;
	}

	public void setPjcmat(double pjcmat){
		this.pjcmat=pjcmat;
	}

	public void setPjdmat(double pjdmat){
		this.pjdmat=pjdmat;
	}

	public void setTeomat(int teomat){
		this.teomat=teomat;
	}

	public void setPramat(int pramat){
		this.pramat=pramat;
	}

	public void setInvmat(int invmat){
		this.invmat=invmat;
	}

	public void setTipaul(String tipaul){
		this.tipaul=tipaul;
	}

	public void setTpomat(String tpomat){
		this.tpomat=tpomat;
	}

	public void setKlcmat(String klcmat){
		this.klcmat=klcmat;
	}

	public void setNtsmat(int ntsmat){
		this.ntsmat=ntsmat;
	}

	public void setHabmat(int habmat){
		this.habmat=habmat;
	}

	public void setLblmat(int lblmat){
		this.lblmat=lblmat;
	}

	public void setKnpmat(int knpmat){
		this.knpmat=knpmat;
	}

	public void setIntmat(int intmat){
		this.intmat=intmat;
	}

	public void setSmnmat(int smnmat){
		this.smnmat=smnmat;
	}

	public void setRtcmat(int rtcmat){
		this.rtcmat=rtcmat;
	}

	public void setVtrmat(double vtrmat){
		this.vtrmat=vtrmat;
	}

	public void setClrmat(int clrmat){
		this.clrmat=clrmat;
	}

	public void setNgrmat(int ngrmat){
		this.ngrmat=ngrmat;
	}

	public void setCodmdl(String codmdl){
		this.codmdl=codmdl;
	}

	public void setSqlbkr(String sqlbkr){
		this.sqlbkr=sqlbkr;
	}


	public static Smamat load(ResultSet rst)throws SQLException{

		Smamat smamat= new Smamat();

		smamat.setCodcia(rst.getString(1));
		smamat.setCodmat(rst.getString(2));
		smamat.setNommat(rst.getString(3));
		smamat.setNpqmat(rst.getString(4));
		smamat.setCodare(rst.getString(5));
		smamat.setCrdmat(rst.getInt(6));
		smamat.setPjamat(rst.getDouble(7));
		smamat.setPjbmat(rst.getDouble(8));
		smamat.setPjcmat(rst.getDouble(9));
		smamat.setPjdmat(rst.getDouble(10));
		smamat.setTeomat(rst.getInt(11));
		smamat.setPramat(rst.getInt(12));
		smamat.setInvmat(rst.getInt(13));
		smamat.setTipaul(rst.getString(14));
		smamat.setTpomat(rst.getString(15));
		smamat.setKlcmat(rst.getString(16));
		smamat.setNtsmat(rst.getInt(17));
		smamat.setHabmat(rst.getInt(18));
		smamat.setLblmat(rst.getInt(19));
		smamat.setKnpmat(rst.getInt(20));
		smamat.setIntmat(rst.getInt(21));
		smamat.setSmnmat(rst.getInt(22));
		smamat.setRtcmat(rst.getInt(23));
		smamat.setVtrmat(rst.getDouble(24));
		smamat.setClrmat(rst.getInt(25));
		smamat.setNgrmat(rst.getInt(26));
		smamat.setCodmdl(rst.getString(27));
		smamat.setSqlbkr(rst.getString(28));

		return smamat;

	}

}