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
					<a class="navbar-brand" href="#">Surveillance de TP</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="index.php">Page Principale</a></li>
					<li class="active"><a href="ajout.php">Ajouter un Examen</a></li>
					<li><a href="apropos.php">A propos</a></li>
				</ul>
				<!-- <ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul> -->
			</div>
		</nav> 


		


		<div class="container">
			<div class="row main">
				<div class="main-login main-center">
					<form action="ajoutExam.php" method="POST">
						
						<div class="form-group">
							<label for="date" class="cols-sm-2 control-label">Date de l'Examen:</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									<input type="date" class="form-control" name="date" id="date" required/>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="enseignant" class="cols-sm-2 control-label">Enseignant</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
									<?php
											include_once("connect.php");
											$prof = $conn->query("SELECT id , nom, prenom from ENSEIGNANT ORDER BY nom, prenom");
											echo "<select id = \"enseignant\"class=\"form-control\" name = \"enseignant\">";
											// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
											//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
											foreach ($prof as $row){
												echo "<option value=\"" . $row["id"] . "\">" .$row["prenom"]." ". $row["nom"] . "</option>";
											}
											echo"</select>";
										?>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="promotion" class="cols-sm-2 control-label">Promotion</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-education"></span></span>
									<?php
											include_once("connect.php");
											$promo = $conn->query("SELECT id, libelle from PROMOTION ORDER BY libelle");
											//dans le name en vbas c ce qui fait le lien avc le php
											echo "<select id = \"promotion\" class=\"form-control\" name = \"promotion\">";
											// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
											//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
											foreach ($promo as $row){
												echo "<option value=\"" . $row["id"] . "\">" .$row["libelle"]. "</option>";
											}
											echo"</select>";
										?>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="matiere" class="cols-sm-2 control-label">Matière :</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"></span></span>
									<?php
											include_once("connect.php");
											$mat = $conn->query("SELECT id ,libelle from MATIERE ORDER BY libelle");
											//dans le name en vbas c ce qui fait le lien avc le php
											echo "<select id =\"matiere\" class = \"form-control\" name = \"matiere\">";
											// on parcourt les resultats de la requete, select c le menu deroulant html qui englobe les options
											//la value c ce ui va etre mis dans la bdd  et le reste c ce que je vais afficher 
											foreach ($mat as $row){
												echo "<option value=\"" . $row["id"] . "\">" .$row["libelle"]. "</option>";
											}
											echo"</select>";
											
										?>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="duree" class="cols-sm-2 control-label">Durée</label>
							<div class="cols-sm-10">
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-hourglass"></span></span>
									<input type="text" class="form-control" name="duree" id="duree" required/>
								</div>
							</div>
						</div>

						<br>
						
						<div class="form-group ">
							<input class="btn btn-primary btn-lg btn-block login-button" type="submit" value="Ajouter l'Examen" />
						</div>
						
					</form>
				</div>
			</div>
		</div>
		 
		 
		
	</body>

</html>
