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

if ! ls *.dat >/dev/null 2>&1; then
  echo "Não há ficheiros .dat no diretório."
  exit 0
fi

contador=0

for filename in *.dat; do
  contador=$((contador + 1))
  new_filename="${contador}-${filename}"
  mv -- "$filename" "$new_filename"
  echo "$new_filename"
done

echo "Foram renomeados $contador ficheiros."
