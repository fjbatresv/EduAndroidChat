package edu.galileo.android.androidchat.login;

/**
 * Created by javie on 7/06/2016.
 */
public class LoginInteractorImplemetation implements LoginInteractor {
    private LoginRepository repo;

    public LoginInteractorImplemetation() {
        repo = new LoginRepositoryImplementation();
    }

    @Override
    public void checkSession() {
        repo.checkSession();
    }

    @Override
    public void doSignIn(String email, String password) {
        repo.signIn(email, password);
    }

}
