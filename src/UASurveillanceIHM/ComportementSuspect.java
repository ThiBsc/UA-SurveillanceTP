package UASurveillanceIHM;

public class ComportementSuspect {
	private String m_nom;
	private String m_prenom;
	private String m_type;
	private String m_description;
	
	
	public ComportementSuspect(String m_nom, String m_prenom, String m_type, String m_description) {
		this.m_nom = m_nom;
		this.m_prenom = m_prenom;
		this.m_type = m_type;
		this.m_description = m_description;
	}


	public String getM_nom() {
		return m_nom;
	}


	public String getM_prenom() {
		return m_prenom;
	}


	public String getM_type() {
		return m_type;
	}


	public String getM_description() {
		return m_description;
	}
	
	
	
	

}
