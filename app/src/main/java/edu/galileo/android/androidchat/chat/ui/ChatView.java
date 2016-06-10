package edu.galileo.android.androidchat.chat.ui;

import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by javie on 10/06/2016.
 */
public interface ChatView {
    void onMessageReceiverd(ChatMessage msg);
}
