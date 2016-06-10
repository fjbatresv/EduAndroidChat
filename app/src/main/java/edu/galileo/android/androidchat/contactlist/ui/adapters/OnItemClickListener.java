package edu.galileo.android.androidchat.contactlist.ui.adapters;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by javie on 8/06/2016.
 */
public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);

}
