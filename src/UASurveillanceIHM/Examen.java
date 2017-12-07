package UASurveillanceIHM;

import java.sql.*;
import java.util.ArrayList;

import UASurveillanceEngine.*;


public class Examen {
	private ConnexionBase conn;
	private ResultSet rs;
	private String date;
	private String duree;
	private int ens_id;
	private int mat_id;
	private int prom_id;
	private int id;
	private String promo;
	private String matiere;


	/**
	 * Constructeur visant à créer un nouvel examen
	 * @param date La date de l'exament
	 * @param duree La durée de l'examen
	 * @param ens_id l'identifiant de l'enseignant
	 * @param mat_id l'identifiant de la matière
	 * @param prom_id l'identifiant de la promotion
	 */
	public Examen(String date, String duree, int ens_id, int mat_id, int prom_id) {
		super();
		conn = ConnexionBase.getInstance();
		this.date = date;
		this.duree = duree;
		this.ens_id = ens_id;
		this.mat_id = mat_id;
		this.prom_id = prom_id;
	}
	
	public ConnexionBase getConn() {
		return conn;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setPromotion(String promo) {
		this.promo = promo;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	
	public String getDate() {
		return date;
	}

	public String getDuree() {
		return duree;
	}

	public int getEns_id() {
		return ens_id;
	}

	public int getMat_id() {
		return mat_id;
	}

	public int getProm_id() {
		return prom_id;
	}
	
	
}
