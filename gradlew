#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

DIR="$( cd "$( dirname "$0" )" && pwd )"
GRADLE_USER_HOME="${GRADLE_USER_HOME:-$DIR/.gradle}"

exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"