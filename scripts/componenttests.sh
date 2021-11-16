#!/bin/bash

echo
echo Running component test script
mvn test -Pcomponent-tests surefire:test