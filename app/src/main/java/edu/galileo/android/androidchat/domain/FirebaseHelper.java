package edu.galileo.android.androidchat.domain;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 7/06/2016.
 */
public class FirebaseHelper {
    private Firebase dataReference;
    private final static String firebaseUrl = "https://android-chat-fjbatresv.firebaseio.com";
    private final static String separator = "__";
    //Los directorios en el arbol de firebase
    private final static String usersPath = "users";
    private final static String chatsPath = "chats";
    private final static String contactsPath = "contacts";

    private static class SingletonHolder {
        private final static FirebaseHelper instance = new FirebaseHelper();
    }

    public static FirebaseHelper getInstancia(){
        return SingletonHolder.instance;
    }

    public FirebaseHelper() {
        this.dataReference = new Firebase(firebaseUrl);
    }

    public Firebase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail(){
        AuthData authData = dataReference.getAuth();
        String email = null;
        if(authData!= null){
            Map<String, Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if(email != null){
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getRoot().child(usersPath).child(emailKey);
        }
        return userReference;
    }

    public Firebase getMyUserReference (){
        return getUserReference(getAuthUserEmail());
    }

    public Firebase getContactsReference(String email){
        return getUserReference(email).child(contactsPath);
    }

    public Firebase getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public Firebase getOnContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".", "_");
        return getUserReference(mainEmail).child(contactsPath).child(childKey);
    }

    public Firebase getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");
        String keyChat = keySender + separator + keyReceiver;
        if(keySender.compareTo(keyReceiver) > 0){
            keyChat = keyReceiver + separator + keySender;
        }
        return dataReference.getRoot().child(chatsPath).child(keyChat);
    }

    public void changeUserConnectionStatus(Boolean online){
        Firebase myUser = getMyUserReference();
        if(myUser != null){
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online", online);
            getMyUserReference().updateChildren(updates);
            notifyContactOfConnectionChange(online);
        }
    }

    public void notifyContactOfConnectionChange(Boolean online) {
        notifyContactOfConnectionChange(online, false);
    }

    public void signOff(){
        notifyContactOfConnectionChange(User.offLine, true);
    }

    private void notifyContactOfConnectionChange(final Boolean online, final Boolean signOff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String email = child.getKey();
                    Firebase reference = getOnContactReference(email, myEmail);
                    reference.setValue(online);
                }
                //Si esto fuera antes del aviso, no se avisaria.
                if(signOff){
                    dataReference.unauth();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

}
