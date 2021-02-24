#!/usr/bin/env bash
if [ "$1" = "-skipTests" ]; then
  echo "building without tests!"
  mvn clean install -DskipTests -pl account-app -am
elif [ "$1" = "-run" ]; then
  echo "running app..."
  mvn -pl account-app spring-boot:run
elif [ "$1" = "-e2e" ]; then
  echo "running e2e test..."
  mvn clean test -pl e2e-test -am
elif [ "$1" = "-build" ]; then
  mvn clean install -pl account-app -am
else
    echo "Usage: $0 -build|-run|-skipTests|-e2e"
    exit 1
fi
