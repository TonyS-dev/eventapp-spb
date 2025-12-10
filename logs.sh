#!/bin/bash

echo "📋 Mostrando logs del ambiente de desarrollo..."
echo "   (Presiona Ctrl+C para salir)"
echo ""

docker compose -f docker-compose.dev.yml logs -f --tail=100
