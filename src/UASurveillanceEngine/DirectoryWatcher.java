package UASurveillanceEngine;

import java.util.Vector;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;


/**
 * @author tgraveleau
 */
public class DirectoryWatcher extends Watcher {
	 
	/**
	 * ===========================================================================
	 * Variables
	 * ===========================================================================
	 */

	/**
	 * Le changement de taille autorisé entre deux enregistrements de fichier en bytes
	 */
	public static final long AUTHORIZED_FILE_SIZE_CHANGE = 256;
	
	private static final String ALERT_SUSPECT_SAVED = "/!\\ ALERT_SUSPECT_SAVED: %s vient d'augmenter la taille du fichier %s de %d bytes.\n";
	
	/**
	 * Variables nécessaires au run()
	 */
    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;

	/**
	 * La liste des dossiers à watcher
	 */
	private Vector<Path> directoryList;

    /**
     * Partie temporaire en attendant la bdd
     */
	private Vector<String> changeTypes;
	private Vector<Path> changePaths;
	private Vector<Long> changeSizes;
	private Vector<String> changeTimes;
	 
	/**
	 * ===========================================================================
	 * Constructeur
	 * ===========================================================================
	 */
    
	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public DirectoryWatcher( Vector<Path> directories ) throws IOException {
		super("DIRECTORY");
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        this.directoryList = directories;
        
        // Pour chaque dossier on parcourt les sous dossiers et on les watch
        for (int i=0; i < this.directoryList.size(); i++) {
        	walkAndRegisterDirectories(this.directoryList.get(i)); 
        }
        
        /**
         * Partie temporaire en attendant la bdd
         */
        this.changeTypes = new Vector<String>();
        this.changePaths = new Vector<Path>();
        this.changeSizes = new Vector<Long>();
        this.changeTimes = new Vector<String>();
	}
	 
	/**
	 * ===========================================================================
	 * Getters et Setters
	 * ===========================================================================
	 */

	/**
	 * Renvoie la liste des dossiers watchés
	 */
	public String toString() {
		String result = "";
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostname = "(erreur hostname)";
		}
		
		result += "\n - Liste des dossiers watchés (les sous dossiers le sont aussi) pour l'ordinateur " + hostname + ":\n";
		for (int i=0; i < this.directoryList.size(); i++) {
			result += this.directoryList.get(i) + "\n";
		}
		
		return result;
	}

	/**
	 * @param dir
	 * Ajoute un dossier à la liste des dossiers à Watcher
	 */
	public void addDirectory(Path dir) {
		this.directoryList.add(dir);
	}

	/**
	 * @param dir
	 * Enlève un dossier de la liste des dossiers à Watcher
	 */
	public void removeDirectory(Path dir) {
		this.directoryList.remove(dir);
	}
 
	/**
	 * ===========================================================================
	 * Fonctions diverses
	 * ===========================================================================
	 */
	
    /**
     * cf walkAndRegisterDirectories
     * @param dir
     * @throws IOException
     */
    private void registerDirectory(Path dir) throws IOException
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        this.keys.put(key, dir);
    }
 
    /**
     * Déclaration de la façon de watcher les dossiers et leurs sous-dossiers grâce à registerDirectory
     * @param start
     * @throws IOException
     */
    private void walkAndRegisterDirectories(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    /**
     * Sauvegarde le changement détécté dans la BDD
     * @param type
     * @param fullPath
     * @param size
     * @param time
     */
    private void saveChange(String type, Path fullPath, Long size, String time) {
    	this.changeTypes.add(type);
    	this.changePaths.add(fullPath);
    	this.changeSizes.add(size);
    	this.changeTimes.add(time);
    }
    
    /**
     * Valeur en bytes du changement de taille entre l'enregistrement actuel d'un fichier et le dernier
     * @param type
     * @param fullPath
     * @param size
     * @param time
     * @return
     */
    private long sizeFileDifference(String type, Path fullPath, Long size, String time) {
    	
    	/**
    	 * Partie temporaire en attendant l'utilisation de la bdd
    	 */
    	// On récupère l'index du dernier enregistrement du fichier
    	int index = this.changePaths.lastIndexOf(fullPath);
    	
    	// Si le fichier est enregistré dans notre "bdd"
    	if (index != -1) {
    			return size - this.changeSizes.get(index);
    	}
    	
    	/**
    	 * fin de partie temporaire
    	 */
    	 
    	// Le fichier n'est pas enregistré
    	return 0;
    }

    /**
     * Déclenchement d'une alerte de comportement suspect
     * @param hostname
     * @param fullpath
     * @param sizeDifference
     */
    private void launchAlert(String hostname, Path fullpath, long sizeDifference) {
    	System.out.printf(ALERT_SUSPECT_SAVED, hostname, fullpath.toString(), sizeDifference);
    }
    
    /**
     * RUN
     */
	@Override
	public void run() {
		
		while(true) {
			 
            // wait for key to be signaled
            WatchKey key;
            try {
                key = this.watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = this.keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event : key.pollEvents()){
                @SuppressWarnings("rawtypes")
                WatchEvent.Kind kind = event.kind();
 
                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                
                /*======================================================================================================*/
                /* Zone du traitement des fichiers cachés.
                 * On ne veut pas afficher les fichiers cachés, ou les fichiers qui sont dans des dossiers cachés.
                 * Par exemple : pas de fichiers de cache, ...
                 */
                
                /* Avec la méthodes Files.isHidden() on voit directement si le fichier est un fichier caché ou pas.
                 * Il faut ensuite traité le path du fichier pour voir s'il est pas dans un dossier caché.
                 */
                File file = new File(child.toString());
                Date date_event = new Date();
                
                String path_fichier = new String(file.getPath());
                String[] etape_path = path_fichier.split("/");
                boolean is_hidden = false;
                
                // On regarde si le fichier est caché (= le nom commence par un point)
                for(int i=0; i<etape_path.length; i++){
                	if(etape_path[i].length() > 0){
	                	if(etape_path[i].charAt(0) == '.'){
	                		is_hidden = true;
	                		break;
	                	}
                	}
                }
                
                // Le fichier n'est pas caché, on le traite
                if( ! is_hidden){
                	
                	String type = event.kind().name();
                	Path fullPath = child;
                	Long size = file.length();
                	String time = date_event.toString();
                	
                	// On peut le traiter
                	System.out.printf("#- %s: %s size: %d bytes at %s\n", type, fullPath, size, time);

                	// Si on détecte un gros changement, on alerte
                	long sizeFileDifference = sizeFileDifference(type, fullPath, size, time);
                	if ( sizeFileDifference > AUTHORIZED_FILE_SIZE_CHANGE) {
                		
                		// On lance une alerte au serveur avec le nom de la machine qui vient de faire un enregistrement suspect
                		String hostname;
                		try {
                			hostname = InetAddress.getLocalHost().getHostName();
						} catch (UnknownHostException e) { 
							hostname = "(errorHostName)";
						}
						launchAlert(hostname, fullPath, sizeFileDifference);
						
                	}
                	
                	// Dans tous les cas, on sauvegarde le changement
            		saveChange(type, fullPath, size, time);
                	
                }     
                /*======================================================================================================*/
                
                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) {
                            walkAndRegisterDirectories(child);
                        }
                    } catch (IOException x) {
                        System.out.printf("ERROR : Le dossier "+ child.getFileName() +" n'as pas pû être ajouté à la liste des dossiers watchés");
                    }
                }
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
	}

	public static void main(String[] args) {
		// directoriesToWatch pourra être une variable static final
		Vector <Path> directoriesToWatch = new Vector<Path>();
		
		//DatabaseSingleton db = new DatabaseSingleton();
	
		String usernameEtudiant = "etudiant";
		// Variables à tester
		// String usernameEtudiant = System.getProperty("user.name"); // Renvoie root ...
		// String homeEtudiant = System.getProperty("user.home");
		
		// Pour tester j'ai mis le dossier du projet et celui de téléchargement
		directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/flicage_tp/") );
		directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/Téléchargements/") );
		
		DirectoryWatcher dW;
		try {
			dW = new DirectoryWatcher(directoriesToWatch);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("----- START -----");
		System.out.println(dW.toString());
		dW.start();
	
	}
	
}
