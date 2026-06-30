#!/usr/bin/env bash
#
# run.sh — Compila (se necessário) e executa a aplicação Me Livra.
#
set -euo pipefail
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

if [ ! -f dist/me-livra.jar ]; then
  ./build.sh jar
fi
java -jar dist/me-livra.jar
