# UA-SurveillanceTP
Projet surveillance de séance de TP

# Installation
## Partie enseignant
### Programme serveur
Compiler un .jar du serveur (driver jdbc [Maria-DB](https://downloads.mariadb.org/connector-java/2.2.1/))  
Le main se trouve dans serverEvent.EventReceiver
```shell
# pour le lancer
java -jar server.jar blacklist.txt
# il demande ensuite ou sauvgarder les vidéos
0. Default: /var/www/html/ua_surveillance/
1. Test: /tmp/
# le fichier blacklist.txt est un fichier contenant les
# sites déclenchant un évènement suspect de la forme:
docs.google
facebook
...
```
### Interface de visualisation
* Installer un serveur Apache
* Mettre les fichiers du dossier *web_interface* sur le serveur

### Configuration
Dans la base de données, pour que le site puisse correctement afficher les vidéos, penser à préciser un chemin accessible depuis le serveur Apache dans la table VIDEO_PATH, ex:
```sql
update VIDEO_PATH
set path = "/var/www/html/ua_surveillance/movie"
where nom like "Default";
```

## Partie étudiant
### Processus à installer
```shell
# pour l'enregistrement vidéo
sudo apt install ffmpeg
# pour la surveillance du réseau
sudo apt install tcpdump
```
Compiler un .jar du client  
Le main se trouve dans UASurveillanceIHMEtud.Window
```shell
# pour le lancer
sudo java -jar client.jar
# le sudo est nécessaire pour l'écoute réseau,
# s'il est lancé sans, il fonctionnera sans écouter le réseau.
...
```

## Le protocole
Pour communiquer, le client envoie des chaines de caractères de la forme:
```java
"TYPE|exam_id|etu_nom|etu_prenom|date|(?other)"
```
* TYPE: [SCREEN|NETWORK|USB|DIRECTORY]
* exam_id: Sur quel examen enregistrer l'évènemment
* etu_(pre)nom: Le (pre)nom de l'étudiant ayant déclencher l'évènemment
* date: La date ou l'évènement à été déclenché
* other (facultatif): Un information supplémentaire sur l'évènement

Si le serveur reçoit une chaine qui:
* N'a pas le bon nombre de pipe, il affichera:
```java
"Invalid parameters."
```
* A un TYPE non prévu, il affichera:
```java
"Protocol not recognized."
```

### Initialisation de la base de données
Il faut préalablement créer un utilisateur 'UA-user' avec le mdp 'ua-user' (ces informations ne sont pas secrètes, elles sont dans le code, à vous de les changer si vous souhaitez sécuriser votre application).  

Les étudiants n'ont pas besoin d'avoir les identifiants de la bdd pour faire fonctionner le programme.

Le dump de la base de données est disponible à la racine du projet dans le fichier "ua_surveillance.sql"
```shell
# importer le dump
mysql -u UA-user -D ua_surveillance -p < ua_surveillance.sql
```

### Crédits
Icons - www.aha-soft.com
