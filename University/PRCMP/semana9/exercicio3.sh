#!/bin/bash

directory="$1"

if [ "$#" -ne 1 ]; then
  echo "Error: you should only pass one argument" >&2
  exit 1
fi

if [ ! -d "$directory" ]; then
  echo "Erro: '$directory' não é um diretório válido" >&2
  exit 1
fi

cd "$directory" || exit 1

contador=0

for filename in *.dat; do
  contador+=1
  echo ""

done
