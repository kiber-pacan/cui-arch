#!/bin/bash

echo "-------------------------------"
echo "------------BUILDING-----------"
echo "-------------------------------"
mkdir -p buildAllJars | true
y=5

for i in $(seq 1 $END); do
    sh gradlew clean -Pindex="$y"
    sh gradlew build modrinth -Pindex="$y"
    ((y=y+1))
done

echo "-------------------------------"
echo "--------------DONE-------------"
echo "-------------------------------"
