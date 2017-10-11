package UASurveillanceEngine;

import java.util.Vector;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.IOException;
import java.io.File;
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
 * 
 */
public class DirectoryWatcher extends Watcher {
	 
	/**
	 * ===========================================================================
	 * Variables
	 * ===========================================================================
	 */

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;

	/**
	 * On fait un vector de Path et non plus de String pour faciliter les appels aux fonctions
	 */
	private Vector<Path> directoryList;
	 
	/**
	 * ===========================================================================
	 * Constructeur
	 * ===========================================================================
	 */
    
	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public DirectoryWatcher(Vector<Path> directories) throws IOException {
		
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        
        this.directoryList = directories;
        
        for (int i=0; i < this.directoryList.size(); i++) {
        	walkAndRegisterDirectories(this.directoryList.get(i));
        }
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
		
		result += "\nListe des dossiers watchés (les sous dossiers le sont aussi):\n";
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
	 * Déclaration de la façon de watcher les dossiers et leurs sous-dossiers
	 * ===========================================================================
	 */
	
    /**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir) throws IOException
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        this.keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			 
            // wait for key to be signalled
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
                
                // On regarde si le fichier est caché (le nom commence par un point)
                for(int i=0; i<etape_path.length; i++){
                	if(etape_path[i].length() > 0){
	                	if(etape_path[i].charAt(0) == '.'){
	                		is_hidden = true;
	                		break;
	                	}
                	}
                }
                
                if(!is_hidden){
                	System.out.format("%s: %s size: %d bytes at %s\n", event.kind().name(), child, file.length(), date_event.toString());
                }     
                /*======================================================================================================*/
                
                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) {
                            walkAndRegisterDirectories(child);
                        }
                    } catch (IOException x) {
                        // do something useful
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
 
    public static void main(String[] args) throws IOException {
    	// directoriesToWatch pourra être une variable static final
    	Vector <Path> directoriesToWatch = new Vector<Path>();
    	
    	String usernameEtudiant = "etudiant";
    	// Variables à tester
    	// String usernameEtudiant = System.getProperty("user.name"); // Renvoie root ...
    	// String homeEtudiant = System.getProperty("user.home");
    	
    	// Pour tester j'ai mis le dossier du projet et celui de téléchargement
    	directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/flicage_tp/") );
    	directoriesToWatch.add( Paths.get("/home/"+ usernameEtudiant +"/Téléchargements/") );
    	
        new DirectoryWatcher(directoriesToWatch).start();
    }
}