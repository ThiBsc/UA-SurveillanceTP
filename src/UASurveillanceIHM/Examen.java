package UASurveillanceIHM;


public class Examen {
	private ConnexionBase conn;
	private ResultSet rs;
	private int id;
	private String date;
	private String duree;
	private int ens_id;
	private int mat_id;
	private String matiere;
	private int prom_id;
	private String promotion;
	private ArrayList<ComportementSuspect> m_comportements;
	

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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getMatiere() {
		return matiere;
	}

	public String getPromotion() {
		return promotion;
	}

	public ConnexionBase getConn() {
		return conn;
	}

	public ResultSet getRs() {
		return rs;
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
	
	public void addComportementSuspect(ComportementSuspect c){
        m_comportements.add(c);
    }
    
    public ArrayList<ComportementSuspect> getComportementsSuspects(){
        return m_comportements;
    }
}
