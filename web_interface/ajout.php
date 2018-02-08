<!DOCTYPE html>
 <html>
	 <head>
	 	<meta charset="utf-8" />
		 <link rel="stylesheet" href="index.css" />
		 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	 	<title>Ajout Examen</title>
	 </head>

	 <body>
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Surveillance de tp</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="index.php">Accueil</a></li>
					<li class="active"><a href="ajout.php">Ajout</a></li>
					<li><a href="#contact">Contact</a></li>
					<li><a href="#propos">A propos</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
		</nav> 

		<form class="form-horizontal" action="ajoutExam.php" method="POST">
		<div class = "form-group">	  
			<label class = "col-sm-2 control-label">Date de l'examen:</label>
			<div class="col-sm-10"><input type="date" name="date" required /></div>
		</div>

		<div class = "form-group">
		<label class = "col-sm-2 control-label">Enseignant :</label>
			<?php
				include_once("connect.php");
				$prof = $conn->query("SELECT id , nom, prenom from ENSEIGNANT ORDER BY nom, prenom");
				echo "<div class=\"col-sm-10\"><select name = \"enseignant\">";
				// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
				//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
				foreach ($prof as $row){
					echo "<option value=\"" . $row["id"] . "\">" .$row["prenom"]." ". $row["nom"] . "</option>";
				}
				echo"</select></div>";
			?>
		</div >
		
		<div class = "form-group">
		<label class = "col-sm-2 control-label">Matière :</label>
			<?php
				include_once("connect.php");
				$mat = $conn->query("SELECT id ,libelle from MATIERE ORDER BY libelle");
				//dans le name en vbas c ce qui fait le lien avc le php
				echo "<div class=\"col-sm-10\"><select name = \"matiere\">";
				// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
				//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
				foreach ($mat as $row){
					echo "<option value=\"" . $row["id"] . "\">" .$row["libelle"]. "</option>";
				}
				echo"</select></div>";
				
			?>
		</div>
		
		<div class = "form-group">
		<label class = "col-sm-2 control-label">Promotion :</label>
			<?php
				include_once("connect.php");
				$promo = $conn->query("SELECT id, libelle from PROMOTION ORDER BY libelle");
				//dans le name en vbas c ce qui fait le lien avc le php
				echo "<div class=\"col-sm-10\"><select name = \"promotion\">";
				// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
				//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
				foreach ($promo as $row){
					echo "<option value=\"" . $row["id"] . "\">" .$row["libelle"]. "</option>";
				}
				echo"</select></div>";
			?>
		</div>	
		<div class = "form-group">
		<label class = "col-sm-2 control-label">Durée : </label>
		<div class="col-sm-10"><input type="text" name="duree" required/ ></div>
		</div>

		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-10"><input class="btn btn-primary" type="submit" value="Ajout" required /></div>
		</div>
		</form>
		 
		 
		
	</body>

</html>
