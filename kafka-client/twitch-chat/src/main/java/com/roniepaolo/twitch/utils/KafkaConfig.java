package com.roniepaolo.twitch.utils;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class KafkaConfig {
    public String CLIENT_PREFIX;

    public Properties getProps() {
        Properties props = new Properties();
        props.putAll(System.getenv().entrySet().stream()
                .filter(mapEntry -> mapEntry.getKey().startsWith(CLIENT_PREFIX))
                .collect(
                        Collectors.toMap(
                                mapEntry -> convertEnvVarToPropertyKey(
                                        mapEntry.getKey()),
                                Map.Entry::getValue)));
        return props;
    }

    public String convertEnvVarToPropertyKey(String envVar) {
        if (envVar == null || envVar.isEmpty()) {
            throw new IllegalArgumentException(
                    "Environment variable name cannot be null or empty");
        }
        return envVar.substring(CLIENT_PREFIX.length()).toLowerCase()
                .replace('_', '.');
    }
}
