FROM azul/zulu-openjdk-alpine:17-jre-latest

RUN set -eux; \
    apk add --no-cache --upgrade bash

COPY --chmod=+x docker-entrypoint.sh /
COPY ./src/main/resources/log4j2.properties /bin/
COPY ./target/twitch-chat-1.0-SNAPSHOT.jar /app/

WORKDIR /app

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["twitch-chat-1.0-SNAPSHOT.jar"]
