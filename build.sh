#!/bin/bash

echo "-------------------------------"
echo "------------BUILDING-----------"
echo "-------------------------------"
mkdir -p buildAllJars | true
y=5

for i in $(seq 12 $END); do
    sh gradlew :fabric:build -Pindex="$y"

    if [ "$y" -gt 5 ]; then
        sh gradlew :neoforge:build -Pindex="$y"
    else
        sh gradlew :forge:build -Pindex="$y"
    fi

    mv ./*/build/libs/cui-*-[!c]*-*[[:digit:]].jar "buildAllJars"
    ((y=y+1))
done



echo "-------------------------------"
echo "--------------DONE-------------"
echo "-------------------------------"
