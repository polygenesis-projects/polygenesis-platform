#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    ./mvnw clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
      -Dsonar.projectKey=polygenesis-projects_polygenesis-platform \
      -Dsonar.organization=polygenesis-projects \
      -Dsonar.host.url=https://sonarcloud.io \
      -Dsonar.login=$SONAR_TOKEN
else
    ./mvnw test -B
fi


if [ "$TRAVIS_BRANCH" = 'dev' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    ./mvnw clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
      -Dsonar.projectKey=polygenesis-projects_polygenesis-platform \
      -Dsonar.organization=polygenesis-projects \
      -Dsonar.host.url=https://sonarcloud.io \
      -Dsonar.login=$SONAR_TOKEN
fi
