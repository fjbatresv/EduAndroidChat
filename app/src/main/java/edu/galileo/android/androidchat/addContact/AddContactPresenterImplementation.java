package edu.galileo.android.androidchat.addContact;

import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.addContact.events.AddContactEvent;
import edu.galileo.android.androidchat.addContact.ui.AddContactView;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 9/06/2016.
 */
public class AddContactPresenterImplementation implements AddContactPresenter {
    private AddContactView view;
    private AddContactInteractor interactor;
    private EventBus bus;
    public AddContactPresenterImplementation(AddContactView view) {
        this.view = view;
        this.bus = GreenRobotEventBus.getInstancia();
        this.interactor = new AddContactInteractoRImplementation();
    }

    @Override
    public void onShow() {
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        bus.unRegister(this);
    }

    @Override
    public void addContact(String email) {
        if(view != null){
            view.hideInput();
            view.showProgressBar();
            view.contactAdded();
        }
        interactor.execute(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if(view != null){
            view.hideProgressBar();
            view.showInput();
            if(event.isError()){
                view.contactNotAdded();
            }
        }
    }
}
