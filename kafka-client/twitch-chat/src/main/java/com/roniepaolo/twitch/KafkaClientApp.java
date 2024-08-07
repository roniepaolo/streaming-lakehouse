package com.roniepaolo.twitch;

import com.roniepaolo.twitch.sources.twitch.TwitchSource;

public class KafkaClientApp {
    public static void main(String[] args) {
        String[] channels = System.getenv("TWITCH_CHANNELS").split(",\\s*");
        String topic = System.getenv("KAFKA_TOPIC");
        KafkaHelper kh = new KafkaHelper(new TwitchSource(channels));
        kh.produceSource(topic);
    }
}
