#!/bin/bash
set +x

if [ -z "$JAVA_OPTS" ]; then
	export JAVA_OPTS="${JAVA_OPTS} -Dlog4j2.configurationFile=file:/bin/log4j2.properties"
fi

exec java $JAVA_OPTS -jar $JAR "$@"