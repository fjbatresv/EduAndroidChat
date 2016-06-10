package edu.galileo.android.androidchat.contactlist.events;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 8/06/2016.
 */
public class ContactListEvent {
    public final static int contactAdded = 0;
    public final static int contactChanged = 1;
    public final static int contacRemoved = 2;

    private User user;
    private int event;

    public ContactListEvent() {
    }

    public ContactListEvent(User user, int event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }
}
