package edu.galileo.android.androidchat.entities;

import java.util.Map;

/**
 * Created by javie on 8/06/2016.
 */
public class User {
    //Los campos privados, deben llamarse igual que n Firebase o truena.
    private String email;
    private boolean online;
    private Map<String, Object> contacts;
    public final static boolean onLine = true;
    public final static boolean offLine = false;

    public User() {
    }

    public User(String email, boolean online, Map<String, Object> contacts) {
        this.email = email;
        this.online = online;
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnLine(boolean online) {
        this.online = online;
    }

    public Map<String, Object> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, Object> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object obj){
        boolean equals = false;
        if(obj instanceof  User){
            User recipe = (User)obj;
            equals = this.email.equalsIgnoreCase(recipe.getEmail());
        }
        return equals;
    }
}
