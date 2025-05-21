KATA API
Présentation
Cette API est réalisée dans le cadre d’un test technique fullstack. Elle gère des produits, un panier, une wishlist et l’authentification sécurisée via JWT.

# Technologies utilisées
- Java 17 / Spring Boot

- PostgreSQL (base de données)

- JWT pour sécuriser les endpoints

- Springdoc OpenAPI pour la documentation Swagger UI

# Prérequis
- Java 17 installé

- PostgreSQL installé et en fonctionnement

- Maven pour construire le projet

# Configuration de la base de données
# Créez une base de données PostgreSQL nommée kata :

- CREATE DATABASE kata;

# Notez votre utilisateur et mot de passe PostgreSQL.

# Modifiez le fichier src/main/resources/application.properties avec vos paramètres :

spring.datasource.url=jdbc:postgresql://localhost:5432/kata
spring.datasource.username='VOTRE_USERNAME'
spring.datasource.password='VOTRE_MOT_DE_PASSE'

# Lancement du projet

# Documentation Swagger
# L’API est documentée avec Swagger UI disponible ici :
http://localhost:8080/swagger-ui.html