#!/bin/bash

function usage {
    echo "Usage: `basename $0` [OPTION|COMMAND]... -- [JVM OPTION]..."
}

while getopts h OPT;
do
    case "$OPT" in
        h)
            usage
            exit 0
            ;;
        \?)
            usage
            exit 1
            ;;
    esac
done

shift `expr $OPTIND - 1`

SEP=" -- "
OPTS=$@

SBT_OPTS="${OPTS%$SEP*}"

if [ "$SBT_OPTS" != "$OPTS" ];
then
    JVM_OPTS="${OPTS#*$SEP}"
else
    JVM_OPTS=""
fi

JVM_VERSION=$(java -version 2>&1 | head -n 1 | grep -oP "\d+\.\d+" | cut -d. -f2)

JVM_DEFAULTS="                     \
    -Dfile.encoding=UTF-8          \
    -Xss8M                         \
    -Xmx2G                         \
    -XX:+CMSClassUnloadingEnabled  \
    -XX:+UseConcMarkSweepGC        "

if [ "$JVM_VERSION" = "7" ];
then
    JVM_DEFAULTS="$JVM_DEFAULTS -XX:MaxPermSize=1024M"
fi

JVM_OPTS="$JVM_DEFAULTS $JVM_OPTS"

function get_property {
    echo "$(cat project/build.properties | grep $1 | cut -d'=' -f2)"
}

SBT_VERSION="$(get_property sbt.version)"
SBT_LAUNCHER="$(dirname $0)/project/sbt-launch-$SBT_VERSION.jar"

if [ ! -e "$SBT_LAUNCHER" ];
then
    URL="http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/$SBT_VERSION/jars/sbt-launch.jar"
    if [ $(which curl) ];
    then
        curl -Lo $SBT_LAUNCHER $URL || exit
    elif [ $(which wget) ];
    then
        wget -O  $SBT_LAUNCHER $URL || exit
    else
        echo "Neither curl nor wget found. Could not download sbt-launcher-${SBT_VERSION}.jar"
        exit 1
    fi
fi

EXPECTED_MD5="$(get_property sbt.launcher.md5)"
COMPUTED_MD5="$(openssl md5 -hex < $SBT_LAUNCHER | cut -d' ' -f2)"

if [ "$EXPECTED_MD5" != "$COMPUTED_MD5" ];
then
    echo "$SBT_LAUNCHER has invalid MD5 signature: expected $EXPECTED_MD5, got $COMPUTED_MD5"
    exit 1
fi

java $JVM_OPTS -jar $SBT_LAUNCHER $SBT_OPTS
