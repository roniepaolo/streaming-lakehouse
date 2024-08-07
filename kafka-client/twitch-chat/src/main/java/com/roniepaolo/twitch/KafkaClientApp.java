package com.roniepaolo.twitch;

import com.roniepaolo.twitch.sources.twitch.TwitchSource;

public class KafkaClientApp {
    public static void main(String[] args) {
        String[] channels = {"roniepaolo"};
        String topic = "twitch_chat";
        KafkaHelper kh = new KafkaHelper(new TwitchSource(channels));
        kh.produceSource(topic);
    }
}
