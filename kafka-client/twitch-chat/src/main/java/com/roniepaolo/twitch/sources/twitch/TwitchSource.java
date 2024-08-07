package com.roniepaolo.twitch.sources.twitch;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import com.roniepaolo.twitch.sources.KafkaSource;

public class TwitchSource implements KafkaSource {
    private SimpleEventHandler eventHandler;
    private TwitchClient client;

    private static final Logger log = LogManager.getLogger(TwitchSource.class);

    public TwitchSource(String[] twitchChannels) {
        this.client =
                TwitchClientBuilder.builder().withEnableChat(true).build();
        for (String channel : twitchChannels) {
            client.getChat().joinChannel(channel);

            log.info("Joinning channel {}", channel);
        }
        this.eventHandler = client.getEventManager()
                .getEventHandler(SimpleEventHandler.class);
    }

    @Override
    public void produce(Producer<String, String> producer, String topic) {
        eventHandler.onEvent(IRCMessageEvent.class, event -> {
            String channel = event.getChannel().getName();
            EventUser eventUser = event.getUser();
            String user = eventUser == null ? "" : eventUser.getName();
            String message = event.getMessage().orElseGet(String::new);
            String twitchMessage =
                    generateTwitchMessage(channel, user, message);
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(topic, twitchMessage);
            producer.send(record, (RecordMetadata metadata, Exception e) -> {
                if (e != null) {
                    log.error("Exception when producing message: {}",
                            e.getMessage());
                } else {
                    log.info("Channel: {}, User: {}, Message: {}", channel,
                            user, message);
                }
            });
        });
    }

    private static String generateTwitchMessage(String channel, String user,
            String message) {
        ObjectMapper mapper = new ObjectMapper();
        TwitchMessage twitchMessage = new TwitchMessage(channel, user, message);
        try {
            return mapper.writeValueAsString(twitchMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
