#!/usr/bin/env bash
echo $CIRCLE_TEST_REPORTS;
./gradlew functionaltest;
if [[ ${PIPESTATUS[0]} != 0 ]];
    then
      echo "FAILED TEST COPY REPORTS";
      pwd;
      ls -ltra;
      mkdir -p $CIRCLE_TEST_REPORTS/functionaltest/;
      #find . -type f -regex ".*/build/test-results/functionaltest" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
      find . -type f -regex ".*/build/test-results/functionaltest/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
      find . -type f -regex ".*/build/reports/functionaltest/*.*" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
      echo "Returning exit 1";
      exit 1
fi