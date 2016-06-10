package edu.galileo.android.androidchat.contactlist;

/**
 * Created by javie on 8/06/2016.
 */
public class ContactListInteractorImplementation implements ContactListInteractor {
    ContactListRepository repo;

    public ContactListInteractorImplementation() {
        this.repo = new ContaListRepositoryImplementation();
    }

    @Override
    public void subscribe() {
        repo.suscribeToContactListEvents();
    }

    @Override
    public void unSubscribe() {
        repo.unSuscribeToContactListEvents();
    }

    @Override
    public void destroyListener() {
        repo.destoyListener();
    }

    @Override
    public void removeContact(String email) {
        repo.removeContact(email);
    }

    @Override
    public void addContact(String email) {
        repo.addContact(email);
    }
}
