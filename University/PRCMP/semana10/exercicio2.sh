#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Erro: indique um ficheiro para ser lido"
    exit 1
fi

filename="$1"

if [[ ! -f "$filename" ]]; then
  echo "Erro: ficheiro inacessível ou inexistente"
  exit 1
else
  echo "A ler ficheiro"
  for dir in $(cat "$filename"); do
    if [[ -d "$dir" ]]; then
      (cd "$dir" && git pull)
    else
      echo "Erro: Diretório inexistente"
      exit 1
    fi
  done
fi
