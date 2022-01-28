# Authentication

`Authentication` est un micro-service d'authentification

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


- Créer une base de données `db_authentication` ainsi qu'un utilisateur avec les droits sur celle-ci


- Modifier le fichier *src/main/application.properties* avec la base de données et les identifiants de l'utilisateur

- Pour avoir un administrateur :


  - 1)  il faut créer un compte avec un appel à `/register`

  - 2) Modifier manuellement  dans la BDD la colonne `role`  pour l'utilisateur préalablement crée.

    - Le rôle doit être `ROLE_ADMIN` si c'est pour fonctionner avec l'application principal de notre organisation `AMT-projet`

- Dans `com/amt/dflipflop/Constants.java` :

    - Pour le mode production, mettre la variable  `IS_PROD` à true

  ```java
  public final static Boolean IS_PROD = true;
  ```

    - Pour le mode local, mettre la variable  `IS_PROD` à false

  Conséquence : la clé secrète pour les jwt sera la valeur de la constante `tokenSecretDefault` (par défaut : `secret`)

```java
public final static Boolean IS_PROD = false;
```

- Lancer le projet:


`./mvnw spring-boot:run`

- Une fois lancée, les routes locales définies sont les suivantes :
  - [http://localhost:8081/login](http://localhost:8081/login)
  - [http://localhost:8081/register](http://localhost:8081/register)

## Utilisation

Lorsque vous créez un utilisateur en réalisant une requête à `/register`, le rôle par défaut attribué à votre utilisateur est `ROLE_USER`.



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



## FAQ

- Lorsque je lance le micro service, j'ai les erreurs suivantes : `BeanInstantiationException: Failed to instantiate, nested exception is java.io.FileNotFoundException: `

*Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.amt.dflipflop.Services.JwtProvider]: Constructor threw exception; nested exception is java.io.FileNotFoundException: \opt\tomcat\webapps\zone_secret\jwt.txt (Le chemin d’accès spécifié est introuvable)
[...]
Caused by: java.io.FileNotFoundException: \opt\tomcat\webapps\zone_secret\jwt.txt (Le chemin d’accès spécifié est introuvable)*

> Piste : Dans src\main\java\com\amt\dflipflop, avez-vous bien configuré correctement le fichier Constants ? Dans celui-ci, une variable IS_PROD permet d'indiquer si il faut utiliser le chemin spécifié par la constante jwtfileNamePath se trouvant dans le même fichier.

- J'aimerais tester mon application en local. Ais-je besoin de configurer une clé secrète pour signer les jwt?

> Non. Dans, src\main\java\com\amt\dflipflop, vous pouvez mettre la valeur de la constante IS_PROD à false. Cela aura pour conséquence d'utiliser "secret" comme clé pour signer les jwt. Attention néanmoins à remette IS_PROD à true quand vous serez en production.

- J'ai crée un utilisateur, je peux me connecter mais je n'arrive pas à accéder aux zones réservées aux admin dans mon application. D'où vient le problème ?

> Piste  : Avez-bien mis le rôle de votre utilisateur admin en ROLE_ADMIN dans votre base de donnée ?



## Contribuer

Rendez vous sur la partie [collaboration](https://github.com/AMT-D-Flip-Flop/AMT-projet/wiki/Collaboration) du wiki pour vous renseigner sur le workflow pour contribuer au projet.

N'oubliez pas de créer des tests en fonction des nouvelles fonctionnalités ajoutées.

## License
[MIT](https://choosealicense.com/licenses/mit/)
