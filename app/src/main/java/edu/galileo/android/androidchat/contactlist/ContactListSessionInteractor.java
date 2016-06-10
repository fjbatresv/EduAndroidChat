package edu.galileo.android.androidchat.contactlist;

/**
 * Created by javie on 8/06/2016.
 */
public interface ContactListSessionInteractor {
    void signOut();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean status);

}
