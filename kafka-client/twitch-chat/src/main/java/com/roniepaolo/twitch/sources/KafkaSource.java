package com.roniepaolo.twitch.sources;

import org.apache.kafka.clients.producer.KafkaProducer;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageKey;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageValue;

public interface KafkaSource {
    public void produce(
            KafkaProducer<TwitchMessageKey, TwitchMessageValue> producer,
            String topic);
}
