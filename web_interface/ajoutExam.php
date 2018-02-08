<?php
	include_once("connect.php");
	// $date = date("Y-m-d H:i:s", $_POST["date"]);
	$date = $_POST["date"];
	$enseignant = $_POST["enseignant"] ;
	$matiere = $_POST["matiere"] ;
	$promotion = $_POST["promotion"] ;
	$duree = $_POST["duree"] ;
	$conn->exec("INSERT INTO EXAMEN (date, duree,ENSEIGNANT_id, MATIERE_id,PROMOTION_id) VALUES ('$date','$duree','$enseignant','$matiere','$promotion') " ) or die(print_r($conn->errorInfo(),true));
	header('Location: index.php');


	

?>