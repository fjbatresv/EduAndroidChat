package edu.galileo.android.androidchat.addContact;

import edu.galileo.android.androidchat.addContact.events.AddContactEvent;

/**
 * Created by javie on 9/06/2016.
 */
public interface AddContactPresenter {
    void onShow();//Equivale a un onCreate
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
