package edu.galileo.android.androidchat.register;

import android.widget.Switch;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;
import edu.galileo.android.androidchat.login.events.LoginEvent;
import edu.galileo.android.androidchat.register.events.RegisterEvent;

/**
 * Created by javie on 10/06/2016.
 */
public class RegisterRepositoryImplementation implements RegisterRepository {
    private FirebaseHelper helper;
    private Firebase dataReference;
    private Firebase myUserReference;
    private String emailA;
    private EventBus bus;

    public RegisterRepositoryImplementation() {
        this.helper = FirebaseHelper.getInstancia();
        this.dataReference = helper.getDataReference();
        this.bus = GreenRobotEventBus.getInstancia();
    }

    private void registerNewUser(String email){
        if (email != null) {
            myUserReference = helper.getUserReference(email);
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    @Override
    public void addUser(final String email, final String password) {
        this.emailA = email;
        dataReference.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                registerNewUser(email);
                postEvent(RegisterEvent.singUpSuccess, null);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                postEvent(RegisterEvent.singUpError, firebaseError.getMessage());
            }
        });
    }


    private void postEvent(int type, String error){
        bus.post(new RegisterEvent(this.emailA, error, type));
    }
}
