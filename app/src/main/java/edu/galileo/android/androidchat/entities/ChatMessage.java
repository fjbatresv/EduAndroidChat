package edu.galileo.android.androidchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by javie on 10/06/2016.
 */
@JsonIgnoreProperties({"sentByMe"})
public class ChatMessage {
    private String msg;
    private String sender;
    private boolean sentByMe;

    public ChatMessage() {
    }

    public ChatMessage(String message, String sender, boolean sentByMe) {
        this.msg = message;
        this.sender = sender;
        this.sentByMe = sentByMe;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object obj){
        boolean equals = false;
        if(obj instanceof  ChatMessage){
            ChatMessage recipe = (ChatMessage)obj;
            equals = this.sender.equalsIgnoreCase(recipe.getSender())
                    && this.msg.equals(recipe.getMessage())
                    && this.sentByMe == recipe.isSentByMe();
        }
        return equals;
    }
}
