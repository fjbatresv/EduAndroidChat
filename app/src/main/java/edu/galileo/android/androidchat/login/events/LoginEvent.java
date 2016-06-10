package edu.galileo.android.androidchat.login.events;

/**
 * Created by javie on 8/06/2016.
 */
public class LoginEvent {
    public final static int onSignInError = 0;
    public final static int onSignInSuccess = 1;
    public final static int onSignUpError = 2;
    public final static int onSignUpSuccess = 3;
    public final static int onFailedToRecoverSession = 4;

    private int eventType;
    private String error;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}