#!/bin/bash

echo "🚀 Iniciando ambiente de desarrollo..."
echo ""

# Levantar los contenedores
docker compose -f docker-compose.dev.yml up -d

# Esperar a que arranquen
echo "⏳ Esperando a que los servicios estén listos..."
sleep 8

# Mostrar estado
echo ""
echo "✅ Servicios iniciados:"
docker compose -f docker-compose.dev.yml ps

echo ""
echo "📋 Para ver los logs en tiempo real, ejecuta:"
echo "   docker compose -f docker-compose.dev.yml logs -f"
echo ""
echo "🌐 Aplicación disponible en: http://localhost:8080"
echo "📊 Base de datos disponible en: localhost:5432"
echo ""
