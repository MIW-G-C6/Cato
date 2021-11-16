#!/bin/bash

echo
echo Running integration test script
mvn test -Pintegration-tests surefire:test