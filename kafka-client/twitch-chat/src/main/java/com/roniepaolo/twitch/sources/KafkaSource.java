package com.roniepaolo.twitch.sources;

import com.roniepaolo.twitch.sources.twitch.TwitchMessageKey;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageValue;
import org.apache.kafka.clients.producer.KafkaProducer;

public interface KafkaSource {
    public void produce(
            KafkaProducer<TwitchMessageKey, TwitchMessageValue> producer,
            String topic);

    public void stop(
            KafkaProducer<TwitchMessageKey, TwitchMessageValue> producer);
}
