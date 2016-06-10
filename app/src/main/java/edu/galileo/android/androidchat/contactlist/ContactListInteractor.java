package edu.galileo.android.androidchat.contactlist;

/**
 * Created by javie on 8/06/2016.
 */
public interface ContactListInteractor {
    void subscribe();
    void unSubscribe();
    void destroyListener();
    void removeContact(String email);
    void addContact(String email);
}
