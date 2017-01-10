#!/usr/bin/env bash
./gradlew functionaltest;
STATUS_CODE="$?"
set -e
echo "Exit Code" $STATUS_CODE;
mkdir -p $CIRCLE_TEST_REPORTS/functionaltest/;
find . -type f -regex ".*/build/test-results/functionaltest/.*xml" -exec cp {} $CIRCLE_TEST_REPORTS/functionaltest/ \;
cp -r build/reports/tests/functionaltest $CIRCLE_TEST_REPORTS/functionaltest/report;
echo "Returning exit";
exit $STATUS_CODE