package edu.galileo.android.androidchat.addContact.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.addContact.AddContactPresenter;
import edu.galileo.android.androidchat.addContact.AddContactPresenterImplementation;
import edu.galileo.android.androidchat.entities.User;

/**
 * A simple {@link Fragment} subclass.
 *
 * Si no hereda de dialog fragment, no es un popup*/
public class AddContactFragment extends DialogFragment implements AddContactView, DialogInterface.OnShowListener {
    @Bind(R.id.editTextEmail)
    EditText txtEmail;
    @Bind(R.id.progressBarAdd)
    ProgressBar progressBar;

    private AddContactPresenter presenter;
    private View parentView;

    public AddContactFragment() {
        // Required empty public constructor
        presenter = new AddContactPresenterImplementation(this);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addcontact_message_title)
                .setPositiveButton(R.string.addcontact_message_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.addcontact_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        View view =  getActivity().getLayoutInflater().inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void showInput() {
        txtEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        txtEmail.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void contactAdded() {
        //Snackbar.make(parentView, String.format(getString(R.string.addcontact_message_contactadded)), Snackbar.LENGTH_LONG);
        Toast.makeText(getContext().getApplicationContext(), getString(R.string.addcontact_message_contactadded), Toast.LENGTH_LONG);
        dismiss();
    }

    @Override
    public void contactNotAdded() {
        txtEmail.setError(String.format(getString(R.string.addcontact_error_message)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //onShowListener nos permite controlar el cierre y apertura
    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog dialog2 = (AlertDialog) getDialog();
        if(dialog2 != null){
            Button positiveButton = dialog2.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog2.getButton(Dialog.BUTTON_NEGATIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addContact(txtEmail.getText().toString());
                }
            });
            negativeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
