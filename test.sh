#!/bin/bash

# Clean previous builds
rm -rf bin Simulation.jar

# Create output directory
mkdir -p bin

# Compile all sources
javac -d bin src/main/*.java src/inputs/*.java

# Copy resources to maintain folder structure
mkdir -p bin/res
cp -r res/* bin/res/

# Create JAR with proper resource paths
jar cvfe Simulation.jar main.Main -C bin .

echo "Lancez avec: java -jar Simulation.jar"
