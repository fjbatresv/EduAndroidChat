package edu.galileo.android.androidchat.register;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;
import edu.galileo.android.androidchat.register.events.RegisterEvent;
import edu.galileo.android.androidchat.register.ui.RegisterView;

/**
 * Created by javie on 10/06/2016.
 */
public class RegisterPresenterImplementation implements RegisterPresenter {
    private RegisterView view;
    private RegisterInteractor interactor;
    private EventBus bus;

    public RegisterPresenterImplementation(RegisterView view) {
        this.view = view;
        this.interactor = new RegisterInteractorImplementation();
        this.bus = GreenRobotEventBus.getInstancia();
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestoy() {
        bus.unRegister(this);
    }

    @Override
    public void addUser(String email, String password) {
        if(view != null){
            view.toggleInputs(false);
            view.toogleProgressBar(true);
            interactor.addUser(email, password);
        }
    }

    @Override
    @Subscribe
    public void onEventMainThred(RegisterEvent event) {
        switch (event.getEvent()){
            case RegisterEvent.singUpSuccess:
                view.succesSignUp();
                break;
            case RegisterEvent.singUpError:
                view.errorSignUp(event.getError());
                break;
        }
        view.toggleInputs(true);
        view.toogleProgressBar(false);
    }
}
