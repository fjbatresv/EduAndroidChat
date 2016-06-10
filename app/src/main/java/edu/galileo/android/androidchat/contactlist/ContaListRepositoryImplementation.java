package edu.galileo.android.androidchat.contactlist;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import edu.galileo.android.androidchat.contactlist.events.ContactListEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 9/06/2016.
 */
public class ContaListRepositoryImplementation implements ContactListRepository {
    private FirebaseHelper helper;
    private ChildEventListener contactEventlistener;
    private EventBus bus;

    public ContaListRepositoryImplementation() {
        this.helper = FirebaseHelper.getInstancia();
        this.bus = GreenRobotEventBus.getInstancia();
    }

    @Override
    public void signOut() {
        this.helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return this.helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = this.helper.getAuthUserEmail();
        this.helper.getOnContactReference(currentUserEmail, email).removeValue();
        this.helper.getOnContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void addContact(String email) {
    }

    @Override
    public void suscribeToContactListEvents() {
        if(contactEventlistener == null){
            contactEventlistener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.contactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.contactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.contacRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            };
        }
        this.helper.getMyContactsReference().addChildEventListener(contactEventlistener);
    }

    @Override
    public void unSuscribeToContactListEvents() {
        if(contactEventlistener != null){
            this.helper.getMyContactsReference().removeEventListener(contactEventlistener);
        }
    }

    private void handleContact(DataSnapshot dataSnapshot, int code){
        String email = dataSnapshot.getKey();
        email = email.replace("_", ".");
        boolean online  = ((Boolean) dataSnapshot.getValue()).booleanValue();
        User user = new User(email, online, null);
        post(code, user);
    }

    private void post(int code, User user){
        bus.post(new ContactListEvent(user, code));
    }

    @Override
    public void destoyListener() {
        contactEventlistener = null;
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        this.helper.changeUserConnectionStatus(status);
    }
}
