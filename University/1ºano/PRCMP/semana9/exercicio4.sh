#!/bin/bash

if [ "$#" -ne 1 ]; then
  echo "Error: you should only pass one argument" >&2
  exit 1
fi

file=$1

if [ ! -f $file ]; then
  echo "Error: this file does not exist." >&2
  exit 1
fi

success=0

while read -r line; do
  url="$line"

  if curl -O "$url"; then
    success=$((success + 1))
  else
    echo "Failed to download: $url"
  fi

done < "$file"

echo "Successfully downloaded $success URL's."
