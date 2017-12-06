package UASurveillanceIHM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
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

	/**
	 * Default constructor
	 */
	private DatabaseSingleton() {
		url = "127.0.0.1";
		user = psw = "";
	}

	/**
	 * @return l'instance unique
	 */
	public static synchronized DatabaseSingleton getInstance() {
		if (instance == null){
			instance = new DatabaseSingleton();
		}
		return instance;
	}

	public ResultSet query(String sql) throws SQLException{
		stmt = connect.createStatement();
		ResultSet ret = stmt.executeQuery(sql);
		stmt.close();
		return ret;
	}
	
	public boolean insert(String sql) throws SQLException{
		boolean ret = false;
		stmt = connect.createStatement();
		ret = stmt.executeUpdate(sql) != 0 ? true : false;
		stmt.close();
		return ret;
	}
	
	public void connect(String adresse, String usr, String db, String mdp) throws ClassNotFoundException, SQLException{
		url = "jdbc:mysql://"+adresse+"/"+db;
		user = usr;
		psw = mdp;
		c = Class.forName("org.mariadb.jdbc.Driver");
		connect = DriverManager.getConnection(url, user, psw);
	}
	
	public void disconnect() throws SQLException{
		// stmt.close(); -> est fermé après chaque utilisation
		connect.close();
	}

}