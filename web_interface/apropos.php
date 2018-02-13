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

	 <body onload="executeScript('https://thibdev.github.io/UA-SurveillanceTP/index.html', afficherReadme)">
	 	<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Surveillance de TP</a>
				</div>
				<ul class="nav navbar-nav">
					<li ><a href="index.php">Page Principale</a></li>
					<li><a href="ajout.php">Ajouter un Examen</a></li>
					<li class="active"><a href="apropos.php">A propos</a></li>
				</ul>

			</div>
		</nav> 

		<section>
			<readme>
            </readme>
		</section>	 
		
	</body>

</html>

