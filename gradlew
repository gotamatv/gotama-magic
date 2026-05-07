#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -ld "$PRG"
    link=`expr "$PRG" : '.*->\(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="$(cd "$(dirname \"$PRG\")" && pwd)"
app_path="${SAVED}"
APP_HOME="${app_path}"

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='\" -Xmx64m -Xms64m \"'

# Use the maximum available, or set a value
# [-Xmx | -Xms] value must be a number, followed by 'm' for 'MB' or 'g' for 'GB'
# as an option to java it must be passed as -Joption

warn ( ) {
    echo "$*" >&2
}

die ( ) {
    echo
    echo "$*"
    echo
    exit 1
}

#
# Class specification on the command line for the wrapper
#
if [ -n "$GRADLE_OPTS" ] ; then
    DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS $GRADLE_OPTS"
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$OSTYPE" = "cygwin" -o "$OSTYPE" = "msys" ] ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`
    JAVACMD=`cygpath --mixed "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSRAW=`find -L / -maxdepth 3 -type d -name gradle 2>/dev/null | head -n 1`
    [ -z "$ROOTDIRSRAW" ] && ROOTDIRSRAW=`find -L /usr/local/Cellar -maxdepth 3 -type d -name gradle 2>/dev/null | head -n 1`
    [ -z "$ROOTDIRSRAW" ] && ROOTDIRSRAW=`find -L /Applications -maxdepth 3 -type d -name gradle 2>/dev/null | head -n 1`
    [ -z "$ROOTDIRSRAW" ] && ROOTDIRSRAW=`find -L /usr -maxdepth 3 -type d -name gradle 2>/dev/null | head -n 1`
    [ -z "$ROOTDIRSRAW" ] && ROOTDIRSRAW=`find -L /opt -maxdepth 3 -type d -name gradle 2>/dev/null | head -n 1`

    # Extract java version
    javaVer=`java -version 2>&1 | grep  '"' | cut -d '"' -f 2 | cut -d '.' -f 1`

    # Get the default JVM opts for the Java version
    DEFAULT_JVM_OPTS="$(getDefaultJvmOpts $javaVer)"
fi

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
else
    JAVACMD="java"
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$msys" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD_LIMIT" != "unlimited" ] ; then
            ulimit -n $MAX_FD_LIMIT
        fi
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if $darwin; then
    GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$CYGWIN" = "true" -o "$MSYS" = "true" ] ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`
    JAVACMD=`cygpath --mixed "$JAVACMD"`
fi

# Start the Gradle daemon
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"