package edu.galileo.android.androidchat.login.ui;

/**
 * Created by javie on 7/06/2016.
 */
public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSingUp();
    void handleSingIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
