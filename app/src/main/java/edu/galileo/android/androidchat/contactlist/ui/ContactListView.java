package edu.galileo.android.androidchat.contactlist.ui;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 8/06/2016.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
