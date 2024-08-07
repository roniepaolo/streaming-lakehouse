package com.roniepaolo.twitch.sources;

import org.apache.kafka.clients.producer.Producer;

public interface KafkaSource {
    void produce(Producer<String, String> producer, String topic);
}
