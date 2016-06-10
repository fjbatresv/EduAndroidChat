package edu.galileo.android.androidchat.chat.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.entities.ChatMessage;

/**
 * Created by javie on 10/06/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, null, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage msg = chatMessages.get(position);
        String message = msg.getMessage();
        holder.txtMessage.setText(message);
        int color = fecthColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;
        if(!msg.isSentByMe()){
            color = fecthColor(R.attr.colorAccent);
            gravity = Gravity.RIGHT;
        }
        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = gravity;
        holder.txtMessage.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage msg){
        if(!chatMessages.contains(msg)){
            chatMessages.add(msg);
            notifyDataSetChanged();
        }
    }

    public int fecthColor(int color){
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[]{color});
        int returnColor = typedArray.getColor(0, 0);
        typedArray.recycle();
        return returnColor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtMessage)
        TextView txtMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
