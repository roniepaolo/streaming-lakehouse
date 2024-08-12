package com.roniepaolo.twitch;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.roniepaolo.twitch.sources.KafkaSource;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageKey;
import com.roniepaolo.twitch.sources.twitch.TwitchMessageValue;
import com.roniepaolo.twitch.utils.KafkaConfig;
import com.roniepaolo.twitch.utils.KafkaProducerConfig;

public class KafkaHelper {
    private Properties producerConf;
    private KafkaSource kafkaSource;

    private static final Logger log = LogManager.getLogger(KafkaHelper.class);

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
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted: {}", e);
        } finally {
            producer.close();
            log.info("Kafka producer is closed");
        }
    }
}
