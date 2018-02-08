<?php
// ce script affiche la video de l'etudiant et ses comportements suspects 
include_once("connect.php");

if (isset($_GET["exam_id"]) && isset($_GET["nom"]) && isset($_GET["prenom"])) {
    $nom = $_GET["nom"];
    $prenom = $_GET["prenom"];
    $exam_id = $_GET["exam_id"];

    // recupereation du path vers la video de l'etudiant
    $video_path = $conn->query("SELECT path FROM EXAMEN_has_VIDEO_PATH join VIDEO_PATH on (EXAMEN_has_VIDEO_PATH.VIDEO_PATH_nom=VIDEO_PATH.nom) WHERE EXAMEN_id = $exam_id AND etu_nom='$nom' AND etu_prenom='$prenom'  ")->fetch();

    //$path=$video_path[0];
    $path="/tmp/";
    $video=$exam_id."_".$nom."_".$prenom.".mp4";
    echo "<video id='vid_exam' preload='metadata'  controls><source src=\"$path$video\" type=\"video/mp4\">La vidéo est inexistante.</video>";

   

    // recupereation des evenements suspects de l'etudiant
    $events = $conn->query("select EVENEMENT_type, other, timestampdiff(SECOND, create_date, date) as event_time_in_video from EXAMEN_has_EVENEMENT E join EXAMEN_has_VIDEO_PATH V on (E.etu_nom=V.etu_nom and E.etu_prenom=V.etu_prenom) WHERE E.EXAMEN_id = $exam_id AND E.etu_nom = '$nom' AND  E.etu_prenom = '$prenom'");
    echo "<table id ='listesuspecte' class=\"table table-striped table-hover\">";
    echo "<tr><th>Type</th><th>Moment</th></tr>";
    foreach($events as $row) {
        echo "<tr onclick=\"setCurTime(".$row["event_time_in_video"].")\">";
        echo "<td>".$row["EVENEMENT_type"]."</td>";
        $min = floor($row["event_time_in_video"] / 60);
        $sec = $row["event_time_in_video"] % 60;
        echo "<td>$min:$sec</td>";
        echo "</tr>";
    }
    echo "</table>";

}
else {
    echo "Erreur : exam_id ,nom et prenom non trouvés";
}
?>
