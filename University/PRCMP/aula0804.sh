#!/bin/bash

echo "What's your name"
echo

read name && echo "Which directory do u wanna have a look, $name?"
echo

read directory

if [ -d "$directory" ]; then
  ls "$directory"
else
  echo "Error: '$directory' is not a valid directory" >&2
  exit 1
fi
