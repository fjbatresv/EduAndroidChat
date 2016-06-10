package edu.galileo.android.androidchat.contactlist;

/**
 * Created by javie on 8/06/2016.
 */
public interface ContactListRepository {
    void signOut();
    String getCurrentUserEmail();
    void removeContact(String email);
    void addContact(String email);
    void suscribeToContactListEvents();
    void unSuscribeToContactListEvents();
    void destoyListener();
    void changeConnectionStatus(boolean status);
}
