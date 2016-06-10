package edu.galileo.android.androidchat.chat;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.chat.events.ChatEvent;
import edu.galileo.android.androidchat.chat.ui.ChatView;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatPresenterImplementation implements ChatPresenter {
    private ChatView view;
    private ChatIinteractor interactor;
    private ChatSessionInteractor session;
    private EventBus bus;

    public ChatPresenterImplementation(ChatView view) {
        this.view = view;
        this.bus = GreenRobotEventBus.getInstancia();
        this.interactor = new ChatInteractorImplementation();
        this.session = new ChatSessionInteractorImplementation();
    }

    @Override
    public void onPause() {
        interactor.unSubscribe();
        session.changeConnectionStatus(User.offLine);
    }

    @Override
    public void onResume() {
        interactor.subscribe();
        session.changeConnectionStatus(User.onLine);
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        bus.unRegister(this);
        interactor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        interactor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        interactor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMeinThread(ChatEvent event) {
        if(view != null){
            view.onMessageReceiverd(event.getMessage());
        }
    }
}
