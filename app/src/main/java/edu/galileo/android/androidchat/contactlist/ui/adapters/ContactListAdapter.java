package edu.galileo.android.androidchat.contactlist.ui.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.galileo.android.androidchat.R;
import edu.galileo.android.androidchat.domain.AvatarHelper;
import edu.galileo.android.androidchat.entities.User;
import edu.galileo.android.androidchat.libs.ImageLoader;

/**
 * Created by javie on 8/06/2016.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>  {
    private List<User> contacList;
    private ImageLoader imageLoader;
    private OnItemClickListener listener;

    public ContactListAdapter(List<User> contacList, ImageLoader imageLoader, OnItemClickListener listener) {
        this.contacList = contacList;
        this.imageLoader = imageLoader;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_contact, null, false);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = contacList.get(position);
        String email = user.getEmail();
        holder.setOnClickListener(user, listener);
        holder.txtUuser.setText(email);
        String status = user.isOnline() ? "En linea" : "Desconectado";
        //int color =  user.isOnline() ? R.color.colorOnline : R.color.colorOffline;
        int color = user.isOnline() ? Color.GREEN : Color.RED;
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);
        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount() {
        return contacList.size();
    }

    public void add(User user){
        if(!contacList.contains(user)){
            contacList.add(user);
            notifyDataSetChanged();
        }
    }
    public void update(User user){
        if (contacList.contains(user)){
            int position = contacList.indexOf(user);
            contacList.set(position, user);
            notifyDataSetChanged();
        }
    }
    public void remove(User user){
        if (contacList.contains(user)){
            contacList.remove(user);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtUser)
        TextView txtUuser;
        @Bind(R.id.txtStatus)
        TextView txtStatus;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        private void setOnClickListener(final User user, final OnItemClickListener listener){
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);

                }
            });
            this.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
