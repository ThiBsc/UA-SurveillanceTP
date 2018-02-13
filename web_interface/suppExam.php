<?php
  include_once("connect.php");



  if (isset($_GET["exam_id"])) {
        $exam_id = $_GET["exam_id"]; 
        $conn->exec("delete from EXAMEN_has_VIDEO_PATH where EXAMEN_id = $exam_id ") ;
        $conn->exec("delete from EXAMEN_has_EVENEMENT where EXAMEN_id = $exam_id") ;
        $conn->exec("delete from EXAMEN where id = $exam_id");
        suppression_video($exam_id);
        header('Location: index.php');
  }
else {
    echo("erreur:donner l'id de l'exam");
}

function suppression_video($exam_id){
    $dir = "/var/www/html/tmp/"; // TODO Verifier que ça marche sur le server
        //  si le dossier pointe existe
        if (is_dir($dir)) {

        // si il contient quelque chose
        if ($dh = opendir($dir)) {

            // boucler tant que quelque chose est trouve
            while (($file = readdir($dh)) !== false) {

                // affiche le nom et le type si ce n'est pas un element du systeme
                if( $file != '.' && $file != '..' && preg_match("/^".$exam_id."_/", $file)) {
                    echo "fichier : $dir$file <br />\n";
                    unlink("$dir$file");
                
                }
            }

            // on ferme la connection
            closedir($dh);
        }
    }

}


?>