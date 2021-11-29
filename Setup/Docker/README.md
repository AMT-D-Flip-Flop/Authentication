# Setup Docker

Afin de setup un environnement de développement identique pour tout le monde, nous utilisons Docker avec le fichier "docker-compose.yml".

Pour installer l'environnement il suffit de lancer la commande suivante en étant au même niveau que le fichier "docker-compose.yml" :

```
docker-compose -p AMT_Project up --build
```



## Description

A l'intérieur du docker on retrouve un serveur MySQL, qui écoute sur le port 3306. Le serveur MySQL utilise l'encodage "utf8mb4_unicode_ci".

On retrouve aussi adminer qui permet de visualiser graphiquement la base de donnée. Celui-ci est accessible via l'url [http://localhost:8082/](http://localhost:8082/).

Le port 8080 étant utilisé par le serveur Tomcat de IntelliJ pour accéder au site web et le port 8081 étant utilisé via le tunnel SSH pour accéder au site en production nous avons décider de mettre adminer sur le port 8082.

