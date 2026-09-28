#!/bin/bash

echo "Which command do you want to execute?"
echo

read command

if [ "$($command)" ]; then
  eval $command

else
  echo
  echo "Command not found"

fi
