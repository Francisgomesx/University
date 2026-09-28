#!/bin/bash

attempt=0
guess=0

echo "Guess the random number: "

random=$((RANDOM % 100 + 1))

while [[ $number -ne $random ]]; do
  read -p "Your guess: " guess
  ((attempt=attempt+1))

  if [ "$guess" -lt 0 -o "$guess" -gt 100 ]; then
    echo "Not inside the interval, [0;100]"
    echo
  fi

  if [[ $guess -lt $random ]]; then
    echo "Higher! Try again."

  elif [[ $guess -gt $random ]]; then
    echo "Lower! Try again."

  else
    echo "Congratulations, you guessed the number "$random" in "$attempt" attempts."
    exit 0
  fi

done
