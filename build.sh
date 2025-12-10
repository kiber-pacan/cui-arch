#!/bin/bash

echo "-------------------------------"
echo "------------BUILDING-----------"
echo "-------------------------------"
mkdir -p buildAllJars | true
y=7

for i in $(seq 10 $END); do
    sh gradlew build -Pindex="$y"

    mv ./*/build/libs/cui-*-[!c]*-*[[:digit:]].jar "buildAllJars"
    ((y=y+1))
done



echo "-------------------------------"
echo "--------------DONE-------------"
echo "-------------------------------"
