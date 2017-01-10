#!/usr/bin/env bash
./gradlew functionaltest;
STATUS_CODE="$?"
echo "Exit Code" $STATUS_CODE;
mkdir -p $CIRCLE_TEST_REPORTS/functionaltest/;
find . -type f -regex ".*/build/test-results/functionaltest/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
cp -r build/reports/functionaltest $CIRCLE_TEST_REPORTS/report;
echo "Returning exit";
exit $STATUS_CODE