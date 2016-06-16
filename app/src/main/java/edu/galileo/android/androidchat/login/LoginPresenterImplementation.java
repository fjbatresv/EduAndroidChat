package edu.galileo.android.androidchat.login;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;
import edu.galileo.android.androidchat.login.events.LoginEvent;
import edu.galileo.android.androidchat.login.ui.LoginView;

/**
 * Created by javie on 7/06/2016.
 */
public class LoginPresenterImplementation implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImplementation(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImplemetation();
        this.eventBus = GreenRobotEventBus.getInstancia();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unRegister(this);
    }

    @Override
    public void checkAuthenticatedUser() {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }


    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getError());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedRecoverSession();
                break;
        }
    }

    private void onFailedRecoverSession() {
        if(loginView != null){
            loginView.enableInputs();
            loginView.hideProgress();
        }
    }

    private void onSignInSuccess(){
        if(loginView != null){
            loginView.navigateToMainScreen();
        }
    }

    private void onSignInError(String error){
        if(loginView != null){
            loginView.enableInputs();
            loginView.hideProgress();
            loginView.loginError(error);
        }
    }

}
