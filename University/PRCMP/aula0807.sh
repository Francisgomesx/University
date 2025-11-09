#!/bin/bash

if [ $# -ne 1 ]; then
  echo "usage: $0 file" >&2
  exit 2
fi


if [ ! -f "$1" ]; then
  echo "error: file not found" >&2
  exit 1
else
  echo
  cat "$1"

fi
