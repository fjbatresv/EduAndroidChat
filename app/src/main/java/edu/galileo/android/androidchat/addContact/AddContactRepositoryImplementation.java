package edu.galileo.android.androidchat.addContact;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import edu.galileo.android.androidchat.addContact.events.AddContactEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 9/06/2016.
 */
public class AddContactRepositoryImplementation implements AddContactRepository {
    private EventBus bus;
    private FirebaseHelper helper;

    public AddContactRepositoryImplementation() {
        this.bus = GreenRobotEventBus.getInstancia();
        this.helper = FirebaseHelper.getInstancia();
    }

    @Override
    public void addContact(String email) {
        final String key = email.replace(".", "_");
        Firebase userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null){
                    Firebase myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());
                    Firebase newUserContactReference = helper.getContactsReference(user.getEmail());
                    newUserContactReference.child((helper.getAuthUserEmail()).replace(".","_"))
                            .setValue(User.onLine);
                    post(false);
                }else{
                    post(true);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                post(true);
            }
        });
    }

    private void post(boolean error){
        bus.post(new AddContactEvent(error));
    }
}
