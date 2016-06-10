package edu.galileo.android.androidchat.chat;

/**
 * Created by javie on 10/06/2016.
 */
public interface ChatRepository {
    void changeConnectionStatus(boolean status);

    void setRecipient(String recipient);
    void sendMessage(String msg);

    void subscribe();
    void unSubscribe();
    void destroyListener();
}
