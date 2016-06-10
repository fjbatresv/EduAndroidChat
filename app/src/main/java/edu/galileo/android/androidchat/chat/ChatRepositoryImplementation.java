package edu.galileo.android.androidchat.chat;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import edu.galileo.android.androidchat.chat.events.ChatEvent;
import edu.galileo.android.androidchat.contactlist.events.ContactListEvent;
import edu.galileo.android.androidchat.domain.FirebaseHelper;
import edu.galileo.android.androidchat.entities.ChatMessage;
import edu.galileo.android.androidchat.libs.EventBus;
import edu.galileo.android.androidchat.libs.GreenRobotEventBus;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatRepositoryImplementation implements ChatRepository {
    private FirebaseHelper helper;
    private EventBus bus;
    private String recipient;
    private ChildEventListener chatEventListener;

    public ChatRepositoryImplementation() {
        this.helper = FirebaseHelper.getInstancia();
        this.bus = GreenRobotEventBus.getInstancia();
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        helper.changeUserConnectionStatus(status);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage(msg, helper.getAuthUserEmail(), true);
        Firebase chatsReference = helper.getChatsReference(this.recipient);
        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void subscribe() {
        if(chatEventListener == null){
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();
                    chatMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail()));
                    bus.post(new ChatEvent(chatMessage));
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            };
        }
        Log.e("ChatRecipient", recipient);
        helper.getChatsReference(recipient).addChildEventListener(chatEventListener);
    }

    @Override
    public void unSubscribe() {
        if(chatEventListener != null ){
            helper.getChatsReference(recipient).removeEventListener(chatEventListener);
        }

    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }
}
