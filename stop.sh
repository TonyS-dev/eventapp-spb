#!/bin/bash

echo "🛑 Deteniendo ambiente de desarrollo..."
echo ""

# Detener y eliminar contenedores
docker compose -f docker-compose.dev.yml down

echo ""
echo "✅ Servicios detenidos correctamente"
echo ""
echo "💡 Para eliminar también los volúmenes (base de datos), usa:"
echo "   docker compose -f docker-compose.dev.yml down -v"
echo ""
