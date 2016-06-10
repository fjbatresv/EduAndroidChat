package edu.galileo.android.androidchat.addContact;

/**
 * Created by javie on 9/06/2016.
 */
public class AddContactInteractoRImplementation implements AddContactInteractor {
    private AddContactRepository repo;

    public AddContactInteractoRImplementation(){
        this.repo = new AddContactRepositoryImplementation();
    }

    @Override
    public void execute(String email) {
        repo.addContact(email);
    }
}
