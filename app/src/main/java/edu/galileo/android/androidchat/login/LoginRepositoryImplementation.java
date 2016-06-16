package edu.galileo.android.androidchat.login;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;
import edu.galileo.android.androidchat.login.events.LoginEvent;

/**
 * Created by javie on 7/06/2016.
 */
public class LoginRepositoryImplementation implements LoginRepository {
    private FirebaseHelper helper;
    private Firebase dataReference;
    private Firebase myUserReference;

    public LoginRepositoryImplementation() {

        this.helper = FirebaseHelper.getInstancia();
        this.dataReference = this.helper.getDataReference();
        //ESto es nulo cuando no hay sesion°onFailedRecoverSession
        this.myUserReference = this.helper.getMyUserReference();
    }

    @Override
    public void signIn(String email, String password) {
        dataReference.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                initSignIn();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
            }
        });
        //postEvent(LoginEvent.onSignInSuccess);
    }

    private void initSignIn(){
        myUserReference = helper.getMyUserReference();
        myUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser == null) {
                    postEvent(LoginEvent.onSignInError, "No existe el usuario");
                }
                helper.changeUserConnectionStatus(User.onLine);
                postEvent(LoginEvent.onSignInSuccess);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void checkSession() {
        if(dataReference.getAuth() != null){
            Log.e("LoginRepo", "Sesion exitente");
            initSignIn();
        }else{
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void postEvent(int type, String error){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if(error != null){
            loginEvent.setError(error);
        }
        EventBus eventBus = GreenRobotEventBus.getInstancia();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        postEvent(type, null);
    }
}
