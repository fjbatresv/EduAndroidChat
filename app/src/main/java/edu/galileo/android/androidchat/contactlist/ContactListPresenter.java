package edu.galileo.android.androidchat.contactlist;

import edu.galileo.android.androidchat.contactlist.events.ContactListEvent;

/**
 * Created by javie on 8/06/2016.
 */
public interface ContactListPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void signOut();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);
    void addContact(String email);


}
