#!/bin/bash

echo "What's your name"
echo

read name && echo "Which directory do u wanna have a look?"
echo

read directory

if [ -d "$directory" ]; then
  echo "$(ls $directory)"
else
  echo "Error: Something went wrong" >&2
  exit 1

fi
