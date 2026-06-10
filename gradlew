#!/bin/sh
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export JAVA_HOME
exec java -classpath "gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
