package UASurveillanceIHM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * @author thibaut & Victoria
 */


public class ConnexionBase {

	private Class c;
	protected  Connection connect = null;
	protected  Statement stmt;
	private String url;
	private String user;
	private String psw;
	private ResultSet rs;
	
	private static ConnexionBase uniqueInstance;
	
	private ConnexionBase(String adresse, String usr, String mdp) throws ClassNotFoundException, SQLException{
		url = "jdbc:mysql://"+adresse+"/ua_surveillance";
		user = usr;
		psw = mdp;
		c = Class.forName("org.mariadb.jdbc.Driver");
		connect = DriverManager.getConnection(url, user, psw);
	}
	/**
	 * 
	 * @return le singleton de la connexion
	 */
	public static ConnexionBase getInstance(){
		if (uniqueInstance==null){
			try {
				uniqueInstance=new ConnexionBase("127.0.0.1", "user", "user");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uniqueInstance;
	}

	/**
	 * Exécute les requêtes SQL
	 * @param sql la requête envoyés
	 * @return le résultat de la requête
	 * @throws SQLException
	 */
	public ResultSet query(String sql) throws SQLException{
		stmt = connect.createStatement();
        ResultSet rset = stmt.executeQuery(sql);
        return rset;
	}
	/**
	 * Récupérer tous les examens
	 * @return la liste des examens en base
	 */
	public ArrayList<Examen> allExam(){
		ArrayList<Examen> listEx = new ArrayList<Examen>();
		Examen ex;
		try{
			rs = query ("SELECT PROMOTION.libelle AS promo, MATIERE.libelle as mat, date, duree, EXAMEN.id, ENSEIGNANT_id, MATIERE_id, PROMOTION_id FROM EXAMEN JOIN PROMOTION ON (EXAMEN.PROMOTION_id = PROMOTION.id) JOIN MATIERE ON (EXAMEN.MATIERE_id = MATIERE.id) ORDER BY date");
			while (rs.next()){
				
				ex = new Examen(rs.getDate("date").toString(),rs.getString("duree"),rs.getInt("ENSEIGNANT_id"),rs.getInt("MATIERE_id"),rs.getInt("PROMOTION_id"));
				ex.setId(rs.getInt("id"));
				ex.setMatiere(rs.getString("mat"));
				ex.setPromotion(rs.getString("promo"));
				listEx.add(ex);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
		return listEx;
	}
	
/**
 * Liste tous les Comportements Suspects en base
 * @param id l'identifiant de l'examen
 * @return une ArrayList des Comportements Suspects présents en base
 */
	public ArrayList<ComportementSuspect> allSuspect(int id){
		ArrayList<ComportementSuspect> listComp = new ArrayList<ComportementSuspect>();
		ComportementSuspect cs;
		try{
			rs = query("SELECT etu_nom, etu_prenom, EVENEMENT_type, other FROM EXAMEN_has_EVENEMENT where EXAMEN_id = "+id+";");
			while (rs.next()){
				cs = new ComportementSuspect(rs.getString("etu_nom"), rs.getString("etu_prenom"), rs.getString("EVENEMENT_type"), rs.getString("other"));
				listComp.add(cs);
			}
		}	
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
		return listComp;
	}
	
	/**
	 * Ajoute un examen dans la base
	 * @param ex l'examen à enregistrer
	 */
	public void addToDB(Examen ex){
		try {			
			rs = query("INSERT INTO EXAMEN (date,duree,ENSEIGNANT_id,MATIERE_id,PROMOTION_id) VALUES (STR_TO_DATE('"+ex.getDate()+"','%d-%m-%Y'),'"+ex.getDuree()+"',"+ex.getEns_id()+","+ex.getMat_id()+","+ex.getProm_id()+");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}