<?php
     	include_once("connect.php");

  ?>
<!DOCTYPE html>
 <html>
	 <head>
	 	<meta charset="utf-8" />
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		 <link rel="stylesheet" href="index.css" />
		 <title>Surveillance de TP</title>
		 <script src="ajax.js"></script>


		 
	 </head>

	 <body>
	 	<nav class="navbar navbar-inverse bg-primary">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Surveillance de TP</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="index.php">Page Principale</a></li>
					<li><a href="ajout.php">Ajouter un Examen</a></li>
					<li><a href="apropos.php">A propos</a></li>
				</ul>
				<!-- <ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul> -->
			</div>
		</nav> 

		<section>
			<column>
				<div>
					<?php
					include_once("connect.php");
					$listeExam = $conn->query("SELECT * FROM EXAMEN ORDER BY date");
					echo "<table  class=\"table table-striped table-hover\">";
					echo "<tr><th><span class=\"glyphicon\">&#xe020;</span></th><th>ID</th><th>Date</th><th>Enseignant</th><th>Matière</th><th>Promotion</th></tr>";
					foreach ($listeExam as $row) {
						$id = $row["id"];
						echo "<tr onclick=\"executeScript('etudiantsExam.php?exam_id=$id', afficherEtudiants)\">";
						echo"<td><a href=\"suppExam.php?exam_id=$id\"> <span class=\"glyphicon glyphicon-trash\"></span></a></td>";
						echo "<td>" . $row["id"] . "</td>";
						echo "<td>" . date("m/d", $row["date"]) . "</td>";
					
						$id_ens = $row["ENSEIGNANT_id"];
						$ens = $conn->query("SELECT prenom, nom FROM ENSEIGNANT WHERE id = $id_ens")->fetch();
						echo "<td>" . $ens[0]. " ".$ens[1] . "</td>";
					
						$id_matiere = $row["MATIERE_id"];
						$matiere = $conn->query("SELECT libelle FROM MATIERE WHERE id = $id_matiere")->fetch();
						echo "<td>" . $matiere[0] . "</td>";
					
						$id_promo = $row["PROMOTION_id"];
						$promo = $conn->query("SELECT libelle FROM PROMOTION WHERE id = $id_promo")->fetch();
						echo "<td>" . $promo[0] . "</td>";

						echo "</tr>";
					}
					echo "</table>";
					?>
				</div>

				<div id = "etudiantsliste" >
				</div>
			</column>
			
			<column>
				<div id="video">
					
				</div>
			</column>
		</section>	 
		
	</body>

</html>

