package edu.galileo.android.androidchat.chat;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatSessionInteractorImplementation implements ChatSessionInteractor {
    private ChatRepository repo;

    public ChatSessionInteractorImplementation() {
        this.repo = new ChatRepositoryImplementation();
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        repo.changeConnectionStatus(status);
    }
}
