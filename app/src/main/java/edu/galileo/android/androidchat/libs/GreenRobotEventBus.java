package edu.galileo.android.androidchat.libs;

/**
 * Created by javie on 8/06/2016.
 */
public class GreenRobotEventBus implements EventBus {
    private org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder{
        private static final  GreenRobotEventBus instance = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstancia(){
        return SingletonHolder.instance;
    }

    public GreenRobotEventBus() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object suscriber) {
        eventBus.register(suscriber);
    }

    @Override
    public void unRegister(Object suscriber) {
        eventBus.unregister(suscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
