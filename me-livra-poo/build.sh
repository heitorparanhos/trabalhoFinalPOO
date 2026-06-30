#!/usr/bin/env bash
#
# build.sh — Compila o projeto, gera o JAR executável e a documentação Javadoc.
# Não requer Maven/Gradle: usa apenas o JDK (javac, jar, javadoc).
#
# Uso:
#   ./build.sh            # compila + gera JAR + gera Javadoc
#   ./build.sh jar        # apenas compila e gera o JAR
#   ./build.sh javadoc    # apenas gera o Javadoc
#   ./build.sh test       # compila e roda os testes funcionais
#
set -euo pipefail

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

SRC="src"
BUILD="build"
DIST="dist"
JAR="$DIST/me-livra.jar"
JAVADOC_OUT="docs/javadoc"
ENC="UTF-8"

compilar() {
  echo ">> Compilando..."
  rm -rf "$BUILD"
  mkdir -p "$BUILD"
  find "$SRC" -name '*.java' > /tmp/melivra-sources.txt
  javac -encoding "$ENC" -d "$BUILD" @/tmp/melivra-sources.txt
  echo "   OK -> $BUILD/"
}

gerar_jar() {
  compilar
  echo ">> Gerando JAR executável..."
  mkdir -p "$DIST"
  jar cfm "$JAR" manifest.txt -C "$BUILD" .
  echo "   OK -> $JAR"
  echo "   Execute com: java -jar $JAR"
}

gerar_javadoc() {
  echo ">> Gerando Javadoc..."
  rm -rf "$JAVADOC_OUT"
  mkdir -p "$JAVADOC_OUT"
  find "$SRC" -name '*.java' > /tmp/melivra-sources.txt
  javadoc -encoding "$ENC" -charset "$ENC" -docencoding "$ENC" \
    -d "$JAVADOC_OUT" -quiet \
    -doctitle "Me Livra — Documentação" \
    -windowtitle "Me Livra" \
    @/tmp/melivra-sources.txt
  echo "   OK -> $JAVADOC_OUT/index.html"
}

rodar_testes() {
  compilar
  echo ">> Rodando testes funcionais..."
  java -cp "$BUILD" br.com.melivra.test.TesteFuncional
}

case "${1:-all}" in
  jar)     gerar_jar ;;
  javadoc) gerar_javadoc ;;
  test)    rodar_testes ;;
  all)     gerar_jar; gerar_javadoc ;;
  *) echo "Alvo inválido: $1 (use: jar | javadoc | test | all)"; exit 1 ;;
esac

echo ">> Concluído."
