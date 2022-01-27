package com.somi.ordersroot.admin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.data.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterItemView> {


    private ArrayList<User> users;
    private final Context context;
    private UsersAdapterListener listener;


    public UsersAdapter(Context _context) {

        context = _context;
        if(users == null) users = new ArrayList<>();

    }//constructor


    public void addUser(User user) {

        users.add(user);
        notifyItemInserted(users.size() - 1);

    }//addUser

    public void updateUser(User user) {

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                notifyItemChanged(i);
                break;

            }

        }

    }//updateUser


    public boolean userExist(User user) {

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserId().equals(user.getUserId())) return true;

        }

        return false;

    }//addUser


    public void updateUsers(ArrayList<User> _users) {

        if(users == null) users = new ArrayList<>();

        if(users.size() == 0) {

            for (int i = 0; i < _users.size(); i++) {
                if(!userExist(_users.get(i))) addUser(_users.get(i));
            }


        }else {

            for (int i = 0; i < _users.size(); i++) {
                if(!userExist(_users.get(i))) addUser(_users.get(i));
            }

        }

    }//updateUsers


    public void deleteUser(User user) {

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.remove(i);
                notifyItemRemoved(i);
                break;

            }

        }

    }//deleteUser



    public ArrayList<User> getUsers() {

        return users;

    }//updateAllprocesss


    public void setListener(UsersAdapterListener _userAdapterListener) {

        listener = _userAdapterListener;

    }//setListener

    @NonNull
    public UsersAdapterItemView onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        return new UsersAdapterItemView(v);

    }//onCreateViewHolder


    public void onBindViewHolder(UsersAdapterItemView holder, int position) {

        if (users != null) {
            holder.itemView.setTag(users.get(position));

            User user = users.get(position);

            holder.tv_position.setText(position+"");
            holder.tv_id.setText(user.getUserId());
            holder.tv_pinCode.setText(user.getPinCode());
            holder.tv_deviceID.setText(user.getDeviceId());

            holder.cv_card.setOnClickListener(v -> {
                if (listener != null) listener.onItemClicked(user);
            });



        }


    }//onBindViewHolder

    public User getUserById(String objectId) {
        if (users == null) return null;
        for (User process: users) {
            if (process.getUserId().equals(objectId)) return process;
        }

        return null;
    }


    public static class UsersAdapterItemView extends RecyclerView.ViewHolder {

        CardView cv_card;
        TextView tv_position;
        TextView tv_id;
        TextView tv_pinCode;
        TextView tv_deviceID;

        UsersAdapterItemView(View itemView) {

            super(itemView);
            cv_card = itemView.findViewById(R.id.cv_item_user);
            tv_position = itemView.findViewById(R.id.tv_item_user_position);
            tv_id = itemView.findViewById(R.id.tv_item_user_id);
            tv_pinCode = itemView.findViewById(R.id.tv_item_user_device_id);
            tv_deviceID = itemView.findViewById(R.id.tv_item_user_pin_code);
        }

    }//SheetsListAdapterItemView


    public int getItemCount() {
        if (users != null) {
            return users.size();
        }else return 0;

    }//getItemCount


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}//sheetsListAdapter
