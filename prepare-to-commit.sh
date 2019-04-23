#!/usr/bin/env bash

abort() {
    echo >&2 '
*************************************************************************
*** You are not ready to commit! Fix the errors above and re-execute. ***
*************************************************************************
'
    echo "An error occurred. Exiting..." >&2
    exit 1
}

trap 'abort' 0

set -e

#########################################
# SCRIPTS_START
#########################################

./mvnw license:update-file-header

./mvnw fmt:format

./mvnw clean install -P validate-license,validate-code-format,validate-code-style,validate-code-bugs,validate-code,docs \
 -DskipTests=true -Dmaven.javadoc.skip=true -B -V

./mvnw test -B

#########################################
# SCRIPTS_END
#########################################

trap : 0

echo >&2 '
***********************************************
*** GOOD JOB! You are ready to commit now. ***
***********************************************
'
