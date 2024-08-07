package com.roniepaolo.twitch.sources.twitch;

public class TwitchMessage {
    private final String channel;
    private final String user;
    private final String message;

    public TwitchMessage(String channel, String user, String message) {
        this.channel = channel;
        this.user = user;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
