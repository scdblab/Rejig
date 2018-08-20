#!/bin/bash
./gradlew clean
mvn clean
./gradlew installDist
mvn package -DskipTests
mvn install -DskipTests
