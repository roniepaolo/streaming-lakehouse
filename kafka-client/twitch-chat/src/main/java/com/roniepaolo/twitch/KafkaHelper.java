package com.roniepaolo.twitch;

import com.roniepaolo.twitch.sources.KafkaSource;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageKey;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageValue;
import com.roniepaolo.twitch.utils.KafkaConfig;
import com.roniepaolo.twitch.utils.KafkaProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class KafkaHelper {
    private static final Logger log = LogManager.getLogger(KafkaHelper.class);
    private final Properties producerConf;
    private final KafkaSource kafkaSource;

    public KafkaHelper(KafkaSource kafkaSource) {
        KafkaConfig config = new KafkaProducerConfig();
        this.producerConf = config.getProps();
        this.kafkaSource = kafkaSource;

        log.info("Kafka client configuration: {}", this.producerConf);
        log.info("Kafka source: {}", this.kafkaSource);
    }

    public void produceSource(String topic) {
        KafkaProducer<TwitchMessageKey, TwitchMessageValue> producer =
                new KafkaProducer<>(producerConf);
        try {
            log.info("Kafka producer is starting writing in topic {}", topic);
            kafkaSource.produce(producer, topic);
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            log.info("Thread was interrupted: {}", e.getMessage());
            kafkaSource.stop(producer);
            Thread.currentThread().interrupt();
        }
    }
}
