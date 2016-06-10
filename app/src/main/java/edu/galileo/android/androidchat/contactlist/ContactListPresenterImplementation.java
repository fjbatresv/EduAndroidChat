package edu.galileo.android.androidchat.contactlist;

import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.contactlist.events.ContactListEvent;
import edu.galileo.android.androidchat.contactlist.ui.ContactListView;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 8/06/2016.
 */
public class ContactListPresenterImplementation implements ContactListPresenter {
    private EventBus bus;
    private ContactListView view;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImplementation(ContactListView view) {
        this.view = view;
        this.bus = GreenRobotEventBus.getInstancia();
        this.listInteractor = new ContactListInteractorImplementation();
        this.sessionInteractor = new ContacListSessionInteractorImplementation();
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onPause() {
        sessionInteractor.changeConnectionStatus(User.offLine);
        listInteractor.unSubscribe();
    }

    @Override
    public void onResume() {
        sessionInteractor.changeConnectionStatus(User.onLine);
        listInteractor.subscribe();
    }

    @Override
    public void onDestroy() {
        bus.unRegister(this);
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void signOut() {
        sessionInteractor.changeConnectionStatus(User.offLine);
        sessionInteractor.signOut();
        listInteractor.unSubscribe();
        listInteractor.destroyListener();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Subscribe
    @Override
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEvent()){
            case ContactListEvent.contactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.contactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.contacRemoved:
                onContactRemoved(user);
                break;
        }
    }

    @Override
    public void addContact(String email) {
        listInteractor.addContact(email);
    }

    private void onContactAdded(User user){
        if(view != null){
            view.onContactAdded(user);
        }
    }

    private void onContactRemoved(User user){
        if (view != null){
            view.onContactRemoved(user);
        }
    }

    private void onContactChanged(User user){
        if (view != null){
            view.onContactChanged(user);
        }
    }
}
