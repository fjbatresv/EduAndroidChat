package edu.galileo.android.androidchat.login;

import edu.galileo.android.androidchat.login.events.LoginEvent;

/**
 * Created by javie on 7/06/2016.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkAuthenticatedUser();
    void validateLogin(String email, String password);
    void onEventMainThread(LoginEvent event);
}
