package UASurveillanceEngine;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


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
	public static long AUTHORIZED_FILE_SIZE_CHANGE = 256;
	public static int DEEP_WATCH_LEVEL = 7;
	
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
	 * La liste des fichiers modifiés avec leur dernière taille
	 */
	private HashMap<String, Long> lastModifs;
    
	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public DirectoryWatcher( Vector<Path> directories ) throws IOException {
		super("DIRECTORY");
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        this.directoryList = directories;
        this.lastModifs = new HashMap<String, Long>();
        
        // Pour chaque dossier on parcourt les sous dossiers et on les watch
        for (int i=0; i < this.directoryList.size(); i++) {
        	if ( !(new File(directoryList.get(i).toString()).isHidden()))
        		walkAndRegisterDirectories(this.directoryList.get(i)); 
        }
        
	}
	 
	/**
	 * ===========================================================================
	 * Getters et Setters
	 * ===========================================================================
	 */

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
    private void registerDirectory(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, /*ENTRY_CREATE, ENTRY_DELETE,*/ ENTRY_MODIFY);
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
			public FileVisitResult postVisitDirectory(Path arg0,
					IOException arg1) throws IOException {
				return super.postVisitDirectory(arg0, arg1);
			}

			@Override
			public FileVisitResult preVisitDirectory(Path arg0,
					BasicFileAttributes arg1) throws IOException {
				FileVisitResult ret = FileVisitResult.CONTINUE;
				if (arg1.isDirectory()){
					File dir = arg0.toFile();
					ret = dir.canRead() && !dir.isHidden() ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
					// Si rien ne l'empeche et si on est pas à plus de 5 niveau de profondeur, on le regarde
					if (ret == FileVisitResult.CONTINUE && arg0.toString().split("/").length < DEEP_WATCH_LEVEL)
						registerDirectory(arg0);
				}
				return ret;
			}

			@Override
			public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1)
					throws IOException {
				FileVisitResult ret = FileVisitResult.CONTINUE;
				if (arg1.isDirectory()){
					File dir = arg0.toFile();
					ret = dir.canRead() && !dir.isHidden() ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
				}
				return ret;
			}

			@Override
			public FileVisitResult visitFileFailed(Path arg0, IOException arg1)
					throws IOException {
				FileVisitResult ret = FileVisitResult.CONTINUE;
				File f = arg0.toFile();
				if (f.isDirectory()){
					ret = f.canRead() && !f.isHidden() ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
				}
				// System.out.println("visitFileFailed, "+arg0+ret);
				return ret;
			}
        	
        });
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
    	long difference = 0;
    	// On récupère le dernier enregistrement du fichier
    	if (lastModifs.containsKey(fullPath.toString())){
    		difference = size - lastModifs.get(fullPath.toString());
    	} else {
    		lastModifs.put(fullPath.toString(), size);
    	}    	 
    	// Si le fichier n'est pas enregistré on renvoie 0
    	return difference;
    }
    
    /**
     * RUN
     */
	@Override
	public void run() {
		isRecording = true;
		while(isRecording) {
			 
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
                
                /* Zone du traitement des fichiers cachés.
                 * On ne veut pas afficher les fichiers cachés, ou les fichiers qui sont dans des dossiers cachés.
                 * Par exemple : pas de fichiers de cache, ...
                 */
                
                /* Avec la méthodes Files.isHidden() on voit directement si le fichier est un fichier caché ou pas.
                 * Il faut ensuite traité le path du fichier pour voir s'il est pas dans un dossier caché.
                 */
                File file = child.toFile();
                Date date_event = new Date();
                
                // Le fichier n'est pas caché, on le traite
                if( !file.isHidden()){
                	
                	String type = event.kind().name();
                	Path fullPath = child;
                	Long size = file.length();
                	String time = date_event.toString();
                	
                	// On peut le traiter
                	System.out.printf("#- %s: %s size: %d bytes at %s\n", type, fullPath, size, time);

                	// Si on détecte un gros changement, on alerte
                	long sizeFileDifference = sizeFileDifference(type, fullPath, size, time);
                	if ( sizeFileDifference > AUTHORIZED_FILE_SIZE_CHANGE) {
	                	// Le changement est trop grand donc suspect
	                	String delimiter = ";";
	                	String info_changes_text = type + delimiter	+ sizeFileDifference;
	                	//System.out.println(info_changes_text);
	            		sendEvent(info_changes_text);
                	}
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
}
