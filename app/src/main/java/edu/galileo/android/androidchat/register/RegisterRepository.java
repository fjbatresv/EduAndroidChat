package edu.galileo.android.androidchat.register;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 10/06/2016.
 */
public interface RegisterRepository {
    void addUser(String email, String password);
}
