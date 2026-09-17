#!/bin/bash
	
if [ -d "$1" ]; then
  ls "$1"
else
  echo "Error: '$directory' is not a valid directory" >&2
  exit 1
fi
