# UA-SurveillanceTP
Projet surveillance de séance de TP

# Installation
### Les librairies à importer
* [Maria-DB](https://downloads.mariadb.com/Connectors/java/connector-java-2.2.0/mariadb-java-client-2.2.0.jar)
* [Sqlite](https://bitbucket.org/xerial/sqlite-jdbc/downloads/sqlite-jdbc-3.21.0.jar)
* Icons - www.aha-soft.com

### Processus à installer
* [ffmpeg](apt://ffmpeg) - enregistrement vidéo
```
sudo add-apt-repository ppa:mc3man/trusty-media
sudo apt-get update
sudo apt-get install ffmpeg
sudo add-apt-repository -r ppa:mc3man/trusty-media
```
* [tcpdump](https://doc.ubuntu-fr.org/trafic) - écoute du réseau
```
sudo apt-get install tcpdump
```

### Initialisation de la base de données
Il faut préalablement créer un utilisateur 'UA-user' avec le mdp 'ua-user' (ces informations ne sont pas secrètes, elles sont dans le code, à vous de les changer si vous souhaitez sécuriser votre application).

Le dump de la base de données après la soutenance est disponible à la racine du projet dans le fichier "ua_surveillance.sql"
