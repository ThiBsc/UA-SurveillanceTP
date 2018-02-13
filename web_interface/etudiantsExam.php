<?php
include_once("connect.php");

if (isset($_GET["exam_id"])) {
    $exam_id = $_GET["exam_id"];

    $listeEtudiants = $conn->query("SELECT id , etu_nom, etu_prenom , VIDEO_PATH_nom FROM EXAMEN_has_VIDEO_PATH WHERE EXAMEN_id = $exam_id ORDER BY etu_nom, etu_prenom");
    echo "<table  class=\"table table-striped table-hover\">";
    echo "<tr><th>Nom</th><th>Prénom</th><th><span class=\"glyphicon\">&#xe209;</span></th></tr>";
    foreach ($listeEtudiants as $row) {
        $nom=$row["etu_nom"] ;
        $prenom=$row["etu_prenom"];
        echo "<tr onclick=\"executeScript('videoExam.php?exam_id=$exam_id&nom=$nom&prenom=$prenom', afficherVideo)\">";
        echo "<td>" . $nom . "</td>";
        echo "<td>" . $prenom . "</td>";
        
        $nbEvents = $conn->query("SELECT COUNT(*) FROM EXAMEN_has_EVENEMENT WHERE etu_nom = '$nom' AND etu_prenom = '$prenom' and EXAMEN_id = $exam_id")->fetch();
        echo "<td>" . $nbEvents[0] . "</td>";
        
        echo "</tr>";
    }

}
else {
    echo "Erreur : Exam id non trouvé";
}
?>