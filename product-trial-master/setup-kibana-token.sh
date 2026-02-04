#!/bin/bash
# ============================================
# Script pour générer le token Kibana
# ============================================
set -e

echo "🔐 Génération du token Kibana service account"
echo "=============================================="

# Charger les variables d'environnement
if [ -f .env ]; then
export $(cat .env | grep -v '^#' | xargs)
else
echo "❌ Fichier .env introuvable"
exit 1
fi

# ✅ Vérifier que Elasticsearch est up (depuis l'hôte)
echo "⏳ Attente d'Elasticsearch..."
for i in { 1..30 }; do
if curl -s -u elastic:${ELASTIC_PASSWORD} http://localhost:9200/_cluster/health > /dev/null 2>&1; then
echo "✅ Elasticsearch est UP"
break
fi
if [ $i -eq 30 ]; then
echo "❌ Elasticsearch ne répond pas après 30 tentatives"
exit 1
fi
echo "   Tentative $i/30..."
sleep 2
done

# ✅ Créer le service account Kibana
echo ""
echo "🔧 Création du service account 'kibana'..."

TOKEN=$(curl -s -u elastic:${ELASTIC_PASSWORD} \
  -X POST "http://localhost:9200/_security/service/elastic/kibana/credential/token/kibana_token" \
  | grep -o '"value":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
echo "❌ Impossible de générer le token"
echo "Réponse de l'API:"
curl -u elastic:${ELASTIC_PASSWORD} \
-X POST "http://localhost:9200/_security/service/elastic/kibana/credential/token/kibana_token"
exit 1
fi

echo "✅ Token généré avec succès !"
echo ""
echo "📋 Ajouter cette ligne dans votre .env :"
echo "============================================"
echo "KIBANA_SERVICE_ACCOUNT_TOKEN=$TOKEN"
echo "============================================"
echo ""
echo "💡 Puis relancer: docker-compose up -d kibana"