# 🛒 Mini Plateforme E-commerce – Test Technique Fullstack
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
## Documentation de l'API

La documentation complète et interactive de l'API est disponible via Swagger UI :

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Elle vous permet de visualiser tous les endpoints, leurs paramètres, leurs réponses attendues, ainsi que de les tester directement après authentification (JWT).

##🧪 Tests via Postman

-📥 1. Importation de la collection
Importez le fichier fourni : ALTEN.postman_collection.json dans Postman.

-👤 2. Création d’un utilisateur
Requête : POST /auth/account

Exemple de payload :

{
  "username": "user",
  "firstname": "John",
  "email": "john@example.com",
  "password": "password123"
}

-🔐 3. Connexion & gestion automatique du token
Requête : POST /auth/token

Exemple :

{
  "email": "john@example.com",
  "password": "password123"
}
✅ À la connexion, un script Postman récupère automatiquement le token JWT et le stocke dans une variable de collection :


pm.collectionVariables.set("token", JSON.parse(responseBody).token);
Toutes les autres requêtes utilisent cette variable automatiquement :

Authorization: Bearer {{token}}

### ELK
# ELK Stack Setup (Windows)

## Prérequis
- Docker Desktop
- Git Bash

## Installation

### 1. Configurer le fichier .env
Créez un fichier `.env` avec :
```
ELASTIC_PASSWORD=votremotdepasse
KIBANA_SERVICE_ACCOUNT_TOKEN=
```

### 2. Démarrer Elasticsearch
```bash
docker-compose up -d elasticsearch
```

### 3. Générer le token Kibana
Ouvrir **Git Bash** et exécuter :
```bash
./setup-kibana-token.sh
```

Copier le token généré et le coller dans `.env`

### 4. Démarrer Kibana et Logstash
```bash
docker-compose up -d
```

### 5. Accéder aux services
- Elasticsearch: http://localhost:9200
- Kibana: http://localhost:5601
- Logstash: localhost:5000

## Redémarrage
Pour les redémarrages suivants (token déjà configuré) :
```bash
docker-compose down
docker-compose up -d
```
