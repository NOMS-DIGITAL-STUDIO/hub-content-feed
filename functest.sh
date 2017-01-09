#!/usr/bin/env bash
./gradlew functionaltest;
echo $CIRCLE_TEST_REPORTS
if [[ ${PIPESTATUS[0]} != 0 ]];
    then
    echo "FAILED TEST COPY REPORTS"
      pwd;
      ls -ltra;
      mkdir -p $CIRCLE_TEST_REPORTS/functionaltest/;
      find . -type f -regex ".*/build/test-results/functionaltest" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
    exit 1
fi