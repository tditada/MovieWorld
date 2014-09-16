#!/bin/bash

# echo "Cleaning..."
mvn clean
rm -rf target
echo "Compiling..."
mvn compile

echo "Copying generated builders in .../g4/model"
mkdir -p src/main/resources/generated/java/ar/edu/itba/paw/g4/model
for file in $(find target/classes/ar/edu/itba/paw/g4/model/*Builder.java); do
	echo $file

	$(cp -rf $file src/main/resources/generated/java/ar/edu/itba/paw/g4/model)
done;

echo "Copying generated builders in .../g4/util"
mkdir -p src/main/resources/generated/java/ar/edu/itba/paw/g4/util
for file in $(find target/classes/ar/edu/itba/paw/g4/util/*Builder.java); do
	echo $file
	$(cp -rf $file src/main/resources/generated/java/ar/edu/itba/paw/g4/util)
done;
