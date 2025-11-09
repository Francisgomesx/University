#!/bin/bash

echo "Enter an exam score to label it's value"

read exam

if [ $exam -le 20 ] && [ $exam -ge 18 ]; then
  echo "Very good"

elif [ $exam -le 17 ] && [ $exam -ge 14 ]; then
  echo "Good"

elif [ $exam -le 13 ] && [ $exam -ge 10 ]; then
  echo "Average"

elif [ $exam -lt 10 ] && [ $exam -ge 0 ]; then
  echo "Insufficient"

else
  echo
  echo "The value must be between [0;20]"

fi
