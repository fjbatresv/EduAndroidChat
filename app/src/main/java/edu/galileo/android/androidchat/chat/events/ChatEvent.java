package edu.galileo.android.androidchat.chat.events;

import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatEvent {
    private ChatMessage message;

    public ChatEvent() {
    }

    public ChatEvent(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
