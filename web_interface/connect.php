<?php
     $dbhost = 'localhost';
     $dbuser = 'UA-user';
     $dbpass = 'ua-user';
     
     try {
     	$conn = new PDO('mysql:host=localhost;dbname=ua_surveillance;charset=utf8mb4', $dbuser, $dbpass) ;
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		} catch (PDOException $e) {
		   echo 'Échec lors de la connexion : ' . $e->getMessage();
		}
  ?>