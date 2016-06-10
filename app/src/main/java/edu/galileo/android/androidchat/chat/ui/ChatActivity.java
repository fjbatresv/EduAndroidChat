package edu.galileo.android.androidchat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.chat.ui.adapters.ChatAdapter;
import edu.galileo.android.androidchat.chat.ChatPresenter;
import edu.galileo.android.androidchat.chat.ChatPresenterImplementation;
import edu.galileo.android.androidchat.domain.AvatarHelper;
import edu.galileo.android.androidchat.entities.ChatMessage;
import edu.galileo.android.androidchat.libs.GlideImageLoader;
import edu.galileo.android.androidchat.libs.ImageLoader;

public class ChatActivity extends AppCompatActivity implements ChatView {
    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.txtMessage)
    EditText txtMessage;
    @Bind(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    private ChatPresenter presenter;
    private ChatAdapter adapter;

    public final static String email = "email";
    public final static String status = "status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        presenter = new ChatPresenterImplementation(this);
        presenter.onCreate();
        setUpAdapter();
        setUpRecyclerView();
        setUpToolbar(getIntent());
    }

    private void setUpAdapter() {
        List<ChatMessage> msgs = new ArrayList<ChatMessage>();
//        msgs.add(new ChatMessage("Test1", "", false));
        //msgs.add(new ChatMessage("Test2", "", true));
        adapter = new ChatAdapter(this, msgs);
    }

    private void setUpRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.btnSendMessage)
    public void sendMessage(){
        presenter.sendMessage(txtMessage.getText().toString());
        txtMessage.setText("");
    }

    private void setUpToolbar(Intent intent) {
        String recipient = intent.getStringExtra(email);
        presenter.setChatRecipient(recipient);
        boolean online = intent.getBooleanExtra(status, false);
        txtUser.setText(recipient);
        String status = online ? "En linea" : "Desconectado";
        //int color =  user.isOnline() ? R.color.colorOnline : R.color.colorOffline;
        int color = online ? Color.GREEN : Color.RED;
        txtStatus.setText(status);
        txtStatus.setTextColor(color);
        ImageLoader imageLoader = new GlideImageLoader(getApplicationContext());
        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));
        setSupportActionBar(toolbar);
    }


    @Override
    public void onMessageReceiverd(ChatMessage msg) {
        adapter.add(msg);
        messageRecyclerView.scrollToPosition(adapter.getItemCount() -1);
    }
}
