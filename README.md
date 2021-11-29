# DFlipFlop

DFlipFLop est une application e-commerce web qui offre à ses clients une large gamme de produits informatique.

## Prérequis
**Développement**
- MySQL 5.7+ ou MariaDB 10.2+
- Java 8
- Maven

**Déployement**
- Ubuntu 20.04

## Installation
- Cloner le repo

    ``git clone https://github.com/AMT-D-Flip-Flop/AMT-projet``


- Créer une base de données ainsi qu'un utilisateur avec les droits sur celle-ci


- Modifier le fichier *src/main/application.properties* avec la base de données et les identifiants de l'utilisateur


- Lancer le projet:

  ``./mvnw spring-boot:run``

## Déployement

Pour déployer l'application, copiez le script *Setup/server-setup.sh* sur le serveur et suivez la procédure d'installation.

Sur la machine de build, créer un fichier **settings.xml** dans le dossier **.m2** de votre home directory qui contient les informations de connexion à votre serveur Tomcat
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>TomcatServer</id>
      <username></username>
      <password></password>
    </server>
  </servers>
</settings>
```
Remplissez la partie username et password avec vos identifiants Tomcat.

Modifiez aussi le fichier pom.xml avec l'adresse de votre serveur (partie build).

Démarrez le deployment:
``
 ./mvnw tomcat7:deploy
``

## Contribuer
Rendez vous sur la partie [collaboration](https://github.com/AMT-D-Flip-Flop/AMT-projet/wiki/Collaboration) du wiki pour vous renseigner sur le workflow pour contribuer au projet.

N'oubliez pas de créer des tests en fonction des nouvelles fonctionnalités ajoutées.

## License
[MIT](https://choosealicense.com/licenses/mit/)
