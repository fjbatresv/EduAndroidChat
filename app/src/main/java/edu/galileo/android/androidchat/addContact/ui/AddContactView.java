package edu.galileo.android.androidchat.addContact.ui;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 9/06/2016.
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgressBar();
    void hideProgressBar();
    void contactAdded();
    void contactNotAdded();
}
