package mvc.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @descripcion Modelo de la tabla smafac
 * @nombre Smafac
 * @copyrigth ClSMA Ltda
 * @author Erick Castillo Caballero
 * @fechaModificacion 15/07/2014
 */
public class Smafac extends BaseObject
{
	private String codcia;
	private String codfac;
	private String nomfac;
	private String npqfac;
	private Date   fchfac;
	private String dirfac;
	private String telfac;
	private String extfac;
	private String coddkn;
	private String codkrd;
	private String codscr;
	private String sqlbkr;


	public String getCodcia(){
		return (this.codcia);
	}

	public String getCodfac(){
		return (this.codfac);
	}

	public String getNomfac(){
		return (this.nomfac);
	}

	public String getNpqfac(){
		return (this.npqfac);
	}

	public Date getFchfac(){
		return (this.fchfac);
	}

	public String getDirfac(){
		return (this.dirfac);
	}

	public String getTelfac(){
		return (this.telfac);
	}

	public String getExtfac(){
		return (this.extfac);
	}

	public String getCoddkn(){
		return (this.coddkn);
	}

	public String getCodkrd(){
		return (this.codkrd);
	}

	public String getCodscr(){
		return (this.codscr);
	}

	public String getSqlbkr(){
		return (this.sqlbkr);
	}



	public void setCodcia(String codcia){
		this.codcia=codcia;
	}

	public void setCodfac(String codfac){
		this.codfac=codfac;
	}

	public void setNomfac(String nomfac){
		this.nomfac=nomfac;
	}

	public void setNpqfac(String npqfac){
		this.npqfac=npqfac;
	}

	public void setFchfac(Date fchfac){
		this.fchfac=fchfac;
	}

	public void setDirfac(String dirfac){
		this.dirfac=dirfac;
	}

	public void setTelfac(String telfac){
		this.telfac=telfac;
	}

	public void setExtfac(String extfac){
		this.extfac=extfac;
	}

	public void setCoddkn(String coddkn){
		this.coddkn=coddkn;
	}

	public void setCodkrd(String codkrd){
		this.codkrd=codkrd;
	}

	public void setCodscr(String codscr){
		this.codscr=codscr;
	}

	public void setSqlbkr(String sqlbkr){
		this.sqlbkr=sqlbkr;
	}


	public static Smafac load(ResultSet rst)throws SQLException{

		Smafac smafac= new Smafac();

		smafac.setCodcia(rst.getString(1));
		smafac.setCodfac(rst.getString(2));
		smafac.setNomfac(rst.getString(3));
		smafac.setNpqfac(rst.getString(4));
		smafac.setFchfac(rst.getDate(5));
		smafac.setDirfac(rst.getString(6));
		smafac.setTelfac(rst.getString(7));
		smafac.setExtfac(rst.getString(8));
		smafac.setCoddkn(rst.getString(9));
		smafac.setCodkrd(rst.getString(10));
		smafac.setCodscr(rst.getString(11));
		smafac.setSqlbkr(rst.getString(12));

		return smafac;

	}

}