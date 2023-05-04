#!/bin/bash

for i in {1..20}
do
  curl -k https://localhost/api/echo/test
  printf " "
  curl -k https://localhost/api/echov2/test
  printf "\n"
done