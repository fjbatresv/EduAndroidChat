package edu.galileo.android.androidchat.addContact.events;

/**
 * Created by javie on 9/06/2016.
 */
public class AddContactEvent {
    private boolean error = false;

    public AddContactEvent() {
    }

    public AddContactEvent(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
