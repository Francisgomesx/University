#!/bin/bash

if [ $# -eq 0 ]; then
  echo "Erro: forneça nomes de containers."
  exit 1
fi

for arg in "$@"; do
  if docker restart "$arg"; then
    echo "Operação bem sucedida: $arg"
  else
    echo "Operação falhou: $arg"
  fi
done
