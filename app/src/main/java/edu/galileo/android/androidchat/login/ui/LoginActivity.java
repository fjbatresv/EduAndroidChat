package edu.galileo.android.androidchat.login.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.contactlist.ui.ContacListActivity;
import edu.galileo.android.androidchat.login.LoginPresenter;
import edu.galileo.android.androidchat.login.LoginPresenterImplementation;

public class LoginActivity extends AppCompatActivity implements LoginView{
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btnSignin)
    Button btnSignIn;
    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenterImplementation(this);
        presenter.onCreate();
        presenter.checkAuthenticatedUser();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignup)
    @Override
    public void handleSingUp() {
        presenter.registerNewUser(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSingIn() {
        presenter.validateLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContacListActivity.class));
    }

    @Override
    public void loginError(String error) {
        txtPassword.setText(null);
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        txtPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_success_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        txtPassword.setText(null);
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        txtPassword.setError(msgError);
    }

    private void setInputs(boolean status){
        txtEmail.setEnabled(status);
        txtPassword.setEnabled(status);
        btnSignIn.setEnabled(status);
        btnSignUp.setEnabled(status);
    }

}
