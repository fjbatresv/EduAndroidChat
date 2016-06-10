package edu.galileo.android.androidchat.contactlist;

/**
 * Created by javie on 8/06/2016.
 */
public class ContacListSessionInteractorImplementation implements ContactListSessionInteractor {
    ContactListRepository repo;

    public ContacListSessionInteractorImplementation() {
        this.repo = new ContaListRepositoryImplementation();
    }

    @Override
    public void signOut() {
        repo.signOut();
    }

    @Override
    public String getCurrentUserEmail() {
        return repo.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        repo.changeConnectionStatus(status);
    }
}
