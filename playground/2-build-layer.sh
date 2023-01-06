#!/bin/bash
set -eo pipefail
./gradlew -q packageLibs
mv build/distributions/blank-java.zip build/blank-java-lib.zip