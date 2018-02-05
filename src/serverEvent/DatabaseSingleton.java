package serverEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Classe Singleton de la connexion à la BDD
 */
public class DatabaseSingleton {

	// instance private unique
	private static DatabaseSingleton instance = null;
	// classe pour le connecteur jdbc
	private Class c;
	// connexion à la base
	private Connection connect = null;
	// un statement
	private Statement stmt;
	// infos de connexion
	private String url;
	private String user;
	private String psw;

	private DatabaseSingleton() {
		// ctor
		url = "127.0.0.1";
		user = psw = "";
	}

	/**
	 * @return l'instance unique de la connexion
	 */
	public static synchronized DatabaseSingleton getInstance() {
		if (instance == null){
			instance = new DatabaseSingleton();
		}
		return instance;
	}

	/**
	 * Permet d'effectuer une requête à la BDD
	 * @param sql - La requête
	 * @return le ResultSet de la requête
	 * @throws SQLException
	 */
	public synchronized ResultSet query(String sql) throws SQLException{
		stmt = connect.createStatement();
		ResultSet ret = stmt.executeQuery(sql);
		stmt.close();
		return ret;
	}
	
	/**
	 * Permet d'effectuer un "update" sur la BDD
	 * @param sql - L'update
	 * @return TRUE si l'update a fonctionné, sinon FALSE
	 * @throws SQLException
	 */
	public synchronized boolean insert(String sql) throws SQLException{
		boolean ret = false;
		stmt = connect.createStatement();
		ret = stmt.executeUpdate(sql) != 0 ? true : false;
		stmt.close();
		return ret;
	}
	
	/**
	 * Permet de lancer une connexion à la BDD
	 * @param adresse - L'ip ou hostname de la BDD
	 * @param usr - Le nom d'utilisateur de la BDD
	 * @param db - Le nom de la base
	 * @param mdp - Le mdp de l'utilisateur de la BDD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect(String adresse, String usr, String db, String mdp) throws ClassNotFoundException, SQLException{
		url = "jdbc:mysql://"+adresse+"/"+db;
		user = usr;
		psw = mdp;
		c = Class.forName("org.mariadb.jdbc.Driver");
		connect = DriverManager.getConnection(url, user, psw);
	}
	
	/**
	 * Permet de fermer la connexion
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException{
		// stmt.close(); -> est fermé après chaque utilisation
		connect.close();
	}

}