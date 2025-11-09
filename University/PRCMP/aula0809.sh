#!/bin/bash

if [ $# -eq 0 ]; then
  echo
  echo "usage: $0 file not found" >&2
  exit 1
fi

for file_name in "$*"; do
  echo
  file "$file_name"
done
