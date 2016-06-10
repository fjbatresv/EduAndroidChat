package edu.galileo.android.androidchat.libs;

/**
 * Created by javie on 8/06/2016.
 */
//En si este es un envoltorio de libreria
public interface EventBus {
    void register(Object suscriber);
    void unRegister(Object suscriber);
    void post(Object event);
}
