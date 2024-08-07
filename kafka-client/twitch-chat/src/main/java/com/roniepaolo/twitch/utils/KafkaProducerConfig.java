package com.roniepaolo.twitch.utils;

public class KafkaProducerConfig extends KafkaConfig {
    public KafkaProducerConfig() {
        this.CLIENT_PREFIX = "KAFKA_PRODUCER_";
    }
}
