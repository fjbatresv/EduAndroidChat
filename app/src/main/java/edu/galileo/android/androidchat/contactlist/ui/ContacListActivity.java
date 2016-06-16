package edu.galileo.android.androidchat.contactlist.ui;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.util.ErrorDialogManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.addContact.ui.AddContactFragment;
import edu.galileo.android.androidchat.chat.ui.ChatActivity;
import edu.galileo.android.androidchat.contactlist.ContactListPresenter;
import edu.galileo.android.androidchat.contactlist.ContactListPresenterImplementation;
import edu.galileo.android.androidchat.contactlist.ui.adapters.ContactListAdapter;
import edu.galileo.android.androidchat.contactlist.ui.adapters.OnItemClickListener;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.GlideImageLoader;
import edu.galileo.android.androidchat.libs.ImageLoader;
import edu.galileo.android.androidchat.login.ui.LoginActivity;

public class ContacListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {
    public static String signedUser = "signed";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;
    @Bind(R.id.layoutContactList)
    CoordinatorLayout container;
    private ContactListPresenter presenter;
    private ContactListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac_list);
        ButterKnife.bind(this);
        setUpAdapter();
        setUpRecyclerView();
        presenter = new ContactListPresenterImplementation(this);
        presenter.onCreate();
        setUpToolbar();
        bienvenida(getIntent());
    }

    public void bienvenida(Intent intent){
        Snackbar.make(container, String.format(getString(R.string.contactlist_message_welcome)
                , intent.getStringExtra(this.signedUser)), Snackbar.LENGTH_LONG).show();
    }

    private void setUpRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setUpAdapter() {
        ImageLoader loader = new GlideImageLoader(this.getApplicationContext());
        List<User> users = new ArrayList<User>();
        //users.add(new User("test@test.com", true, null));
        //users.add(new User("test2@test.com", false, null));
        adapter = new ContactListAdapter(new ArrayList<User>(), loader, this);
    }

    private void setUpToolbar() {
        toolbar.setTitle(presenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                presenter.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("onResume", "onResume");
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("OnPausee", "OnPause");
        presenter.onPause();
    }

    @OnClick(R.id.fab)
    public void addContact(){
        new AddContactFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        //Snackbar.make(container, user.getEmail(), Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ChatActivity.class)
                .putExtra(ChatActivity.email, user.getEmail())
                .putExtra(ChatActivity.status, user.isOnline());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }
}
