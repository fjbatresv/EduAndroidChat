package edu.galileo.android.androidchat.chat;

import edu.galileo.android.androidchat.chat.events.ChatEvent;

/**
 * Created by javie on 9/06/2016.
 */
public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMeinThread(ChatEvent event);
}
