# premiere partie 
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

# 📚 Guide ELK Stack - Configuration et Test

## 📋 Table des matières
1. [Architecture](#architecture)
2. [Prérequis](#prérequis)
3. [Installation](#installation)
4. [Configuration](#configuration)
5. [Test du pipeline](#test-du-pipeline)
6. [Visualisation dans Kibana](#visualisation-dans-kibana)
7. [Intégration Spring Boot](#intégration-spring-boot)

---

## 🏗️ Architecture

```
Spring Boot Application
    ↓ (TCP:5000 - JSON)
Logstash (Input)
    ↓ (Traitement)
Elasticsearch (Stockage)
    ↓ (Lecture)
Kibana (Visualisation)
```

---

## ✅ Prérequis

- Docker et Docker Compose installés
- Port 5000, 5601 et 9200 disponibles

---

## 📦 Installation

### 1. Structure des fichiers

```
projet/
├── docker-compose.yml
├── .env
└── logstash/
    └── pipeline/
        └── logstash.conf
```

## 🚀 Configuration

### 1. Démarrer la stack ELK

```bash
docker-compose up -d
```

### 2. Vérifier que les services sont démarrés

```bash
docker-compose ps
```

**Résultat attendu :**
```
NAME            STATUS
elasticsearch   Up (healthy)
logstash        Up
kibana          Up
```

### 3. Vérifier les logs de Logstash

```bash
docker logs logstash --tail 20
```

**Vous devez voir :**
```
[INFO] Pipelines running {:count=>1, :running_pipelines=>[:main]}
```

**Pas d'erreur 401 !**

---

## 🧪 Test du pipeline

### 1. Envoyer un log de test manuel

```bash
docker run --rm alpine sh -c 'echo "{\"message\":\"Test manuel\",\"level\":\"INFO\"}" | nc host.docker.internal 5000'
```

### 2. Vérifier que l'index a été créé

```bash
curl -u elastic:Elastic123! "http://localhost:9200/_cat/indices?v"
```

**Vous devez voir :**
```
yellow open   springboot-logs-2026.02.04   1   6.2kb
```

### 3. Vérifier le contenu du log

```bash
curl -u elastic:Elastic123! "http://localhost:9200/springboot-logs-*/_search?pretty"
```

**Vous devez voir :**
```json
{
  "_source" : {
    "message" : "Test manuel",
    "level" : "INFO",
    "@timestamp" : "2026-02-04T..."
  }
}
```

---

## 📊 Visualisation dans Kibana

### 1. Ouvrir Kibana

```
http://localhost:5601
```

### 2. Créer un Data View

1. Menu (☰) → **Management** → **Stack Management**
2. **Kibana** → **Data Views**
3. Cliquer sur **Create data view**
4. Remplir :
   - **Name** : `springboot-logs`
   - **Index pattern** : `springboot-logs-*`
   - **Timestamp field** : `@timestamp`
5. Cliquer sur **Save data view to Kibana**

### 3. Voir les logs dans Discover

1. Menu (☰) → **Analytics** → **Discover**
2. Sélectionner `springboot-logs` dans le dropdown (en haut à gauche)
3. Ajuster la plage de temps en haut à droite : **Last 24 hours**
4. **Vous voyez vos logs !** 🎉

### 4. Ajouter des colonnes utiles

Dans Discover, cliquer sur **+** à côté de ces champs :
- `level`
- `message`
- `logger_name`

---

## 🍃 Intégration Spring Boot

### 1. Ajouter la dépendance dans `pom.xml`

```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version>
</dependency>
```

### 2. Créer `src/main/resources/logback-spring.xml`

```xml
<configuration>
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>localhost:5000</destination>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder" />
    </appender>
    
    <root level="INFO">
        <appender-ref ref="LOGSTASH" />
    </root>
</configuration>
```

### 3. Créer un contrôleur de test

```java
package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @GetMapping("/test")
    public String test() {
        logger.info("Test log envoyé vers Logstash");
        logger.error("Test erreur");
        return "Logs envoyés !";
    }
}
```

### 4. Tester l'application

```bash
# Démarrer Spring Boot
./mvnw spring-boot:run

# Appeler l'endpoint
curl http://localhost:8080/test

# Vérifier dans Elasticsearch
curl -u elastic:Elastic123! "http://localhost:9200/springboot-logs-*/_search?pretty&size=2"

# Voir dans Kibana Discover
# Les logs apparaissent automatiquement !
```

---

## 🔍 Recherches utiles dans Kibana

Dans la barre de recherche KQL :

```
# Logs d'erreur
level: "ERROR"

# Messages contenant "test"
message: *test*

# Erreurs OU warnings
level: ("ERROR" OR "WARN")

# Logs des 15 dernières minutes
@timestamp >= now-15m

# Combinaison
level: "ERROR" AND @timestamp >= now-1h
```

---

## 🛠️ Commandes utiles

### Redémarrer un service

```bash
docker-compose restart logstash
```

### Voir les logs d'un service

```bash
docker logs -f logstash
docker logs -f elasticsearch
docker logs -f kibana
```

### Arrêter la stack

```bash
docker-compose down
```

### Supprimer les données (volumes)

```bash
docker-compose down -v
```

### Compter les logs

```bash
curl -u elastic:Elastic123! "http://localhost:9200/springboot-logs-*/_count?pretty"
```

### Supprimer un index

```bash
curl -X DELETE -u elastic:Elastic123! "http://localhost:9200/springboot-logs-2026.02.04"
```

---

## 📈 Explication du flux

1. **Spring Boot** envoie les logs en JSON via TCP sur le port 5000
2. **Logstash** reçoit les logs sur le port 5000
3. **Logstash** ajoute `@timestamp` et `@version`
4. **Logstash** envoie vers Elasticsearch dans l'index `springboot-logs-YYYY.MM.dd`
5. **Elasticsearch** stocke les logs (un nouvel index par jour)
6. **Kibana** lit depuis Elasticsearch et affiche les logs

---

## ✅ Points importants

- ✅ Un **nouvel index est créé chaque jour** : `springboot-logs-2026.02.04`, `springboot-logs-2026.02.05`, etc.
- ✅ Le statut **yellow** est normal en développement (un seul nœud Elasticsearch)
- ✅ Les logs sont **visibles en temps réel** dans Kibana
- ✅ La plage de temps par défaut dans Kibana est **15 minutes**, pensez à l'élargir

---

## 🎯 Résumé rapide

```bash
# 1. Démarrer
docker-compose up -d

# 2. Tester
docker run --rm alpine sh -c 'echo "{\"message\":\"Test\",\"level\":\"INFO\"}" | nc host.docker.internal 5000'

# 3. Vérifier
curl -u elastic:Elastic123! "http://localhost:9200/springboot-logs-*/_count?pretty"

# 4. Visualiser
# Ouvrir http://localhost:5601
```

---

**Votre stack ELK est opérationnelle ! 🎉**


## **Synchronisation manuelle elasticsearch et postgresql !**
Document Technique – Configuration Spring Boot avec Elasticsearch et JPA
1️⃣ Contexte

Dans ce projet, nous avons besoin d’une application Spring Boot qui :

Gère les utilisateurs dans une base SQL via JPA (UsersEntity).

Indexe les utilisateurs dans Elasticsearch pour permettre des recherches rapides et full-text (UsersDocument).

Assure la synchronisation SQL ↔ Elasticsearch à la création, mise à jour et suppression des utilisateurs.

2️⃣ Structure des Entities / Documents
a) UsersEntity – Table SQL
id = Integer auto-incrémenté par la base SQL.

Utilisé pour toutes les opérations CRUD avec JPA.

b) UsersDocument – Elasticsearch
id = String → correspond à UsersEntity.id converti en String pour Elasticsearch.
Permet d’éviter les erreurs de conversion String -> Integer.

3️⃣ Repositories

Fournit toutes les méthodes CRUD SQL.

Permet de rechercher un utilisateur par username ou email.

b) Elasticsearch Repository – UserSearchRepository

Fournit toutes les méthodes CRUD sur Elasticsearch.

Les recherches sont basées sur les conventions Spring Data.

Type d’id = String pour éviter les erreurs de conversion.

4️⃣ Service – Gestion des utilisateurs
Assure la synchronisation JPA ↔ Elasticsearch à la création et à la suppression.
Recherche rapide via Elasticsearch.

5️⃣ Configuration Spring Boot
a) application.properties
# Elasticsearch
spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.username=elastic
spring.elasticsearch.password=Elastic123!

6️⃣ Kibana – Vérification des données
Supprimer un document :

DELETE users/_doc/{id}  // id = SQL id converti en String

7️⃣ Bonnes pratiques

Toujours utiliser le même id pour SQL et Elasticsearch pour éviter les conversions.

Ne pas mélanger types Integer / String dans les repositories.

Toujours supprimer dans SQL puis dans ES pour rester synchronisé.

Utiliser findByUsernameContaining pour des recherches partielles.
mais il existe d'autre synchronisation
SolutionComplexitéTemps réelRecommandation
Spring Boot (manuel)⭐ Simple✅ OuiDébutant
Logstash JDBC⭐⭐ Moyen❌ Non (délai)Bon compromis
Debezium CDC⭐⭐⭐ Complexe✅ OuiProduction
Triggers PostgreSQL⭐⭐⭐ Complexe✅ OuiRarement