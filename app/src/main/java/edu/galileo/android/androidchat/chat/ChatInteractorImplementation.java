package edu.galileo.android.androidchat.chat;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatInteractorImplementation implements ChatIinteractor {
    private ChatRepository repo;

    public ChatInteractorImplementation() {
        this.repo = new ChatRepositoryImplementation();
    }

    @Override
    public void setRecipient(String recipient) {
        repo.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        repo.sendMessage(msg);
    }

    @Override
    public void subscribe() {
        repo.subscribe();
    }

    @Override
    public void unSubscribe() {
        repo.unSubscribe();
    }

    @Override
    public void destroyListener() {
        repo.destroyListener();
    }
}
