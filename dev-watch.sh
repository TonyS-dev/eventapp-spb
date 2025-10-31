#!/bin/bash

echo "🔍 Observando cambios en src/ para recompilar automáticamente..."

# Función para compilar
compile() {
    echo "🔨 Detectado cambio, recompilando..."
    mvn compile -q
    if [ $? -eq 0 ]; then
        echo "✅ Compilación exitosa - Spring DevTools recargará automáticamente"
    else
        echo "❌ Error en compilación"
    fi
}

# Instalar inotify si no está disponible
if ! command -v inotifywait &> /dev/null; then
    echo "📦 Instalando inotify-tools..."
    apt-get update -qq && apt-get install -y -qq inotify-tools > /dev/null 2>&1
fi

# Observar cambios en archivos .java
while inotifywait -r -e modify,create,delete --exclude '\.class$|target/' src/; do
    compile
done
