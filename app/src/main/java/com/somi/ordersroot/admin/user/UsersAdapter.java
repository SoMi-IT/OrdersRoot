package com.somi.ordersroot.admin.user;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.ordersroot.R;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterItemView> {


    private ArrayList<User> users;
    private ArrayList<User> newTempUsers;
    private UsersAdapterListener listener;


    public UsersAdapter() {
        if(users == null) users = new ArrayList<>();

    }//constructor


    public void setListener(UsersAdapterListener _userAdapterListener) {
        listener = _userAdapterListener;

    }//setListener


    public void updateAllData(ArrayList<User> _users) {

        newTempUsers = _users;

        if(users == null || users.size() == 0) {
            users = newTempUsers;
            notifyDataSetChanged();

        }else {

            checkAndUpdateOrDeleteData();

            for (int i = 0; i < newTempUsers.size(); i++) {

                users.add(newTempUsers.get(i));
                notifyItemInserted(users.size() - 1);
                notifyItemRangeChanged(i, users.size());

            }

        }

    }//updateData


    private User findDataInNewData(User user) {

        for (int i = 0; i < newTempUsers.size(); i++) {

            if (newTempUsers.get(i).getUserId().equals(user.getUserId())) {

                User userFounded = newTempUsers.get(i);
                newTempUsers.remove(i);
                return userFounded;

            }

        }

        return null;

    }//findDataInNewData


    private void checkAndUpdateOrDeleteData() {

        for (int i = 0; i < users.size(); i++) {
            User tempUser = findDataInNewData(users.get(i));

            if(tempUser != null){
                if(!users.get(i).getPinCode().equals(tempUser.getPinCode()) || !users.get(i).getUserName().equals(tempUser.getUserName())) {

                    users.set(i, tempUser);
                    notifyItemChanged(i);
                }

            }else {
                users.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, users.size());
            }

        }

    }//checkAndUpdateOrDeleteData


    public ArrayList<User> getData() {
        return users;

    }//getData


    public User getDataById(String objectId) {
        if (users == null) return null;
        for (User data: users) {
            if (data.getUserId().equals(objectId)) return data;
        }

        return null;

    }//getDataById


    public void refreshView(String objectId) {

        if (users == null) return;

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUserId().equals(objectId)) {

                notifyItemChanged(i);
                break;

            }

        }

    }//refreshView


    public int getItemCount() {
        if (users != null) {
            return users.size();
        }else return 0;

    }//getItemCount



    public User getUserById(String objectId) {
        if (users == null) return null;
        for (User process: users) {
            if (process.getUserId().equals(objectId)) return process;
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    public UsersAdapterItemView onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        return new UsersAdapterItemView(v);

    }//onCreateViewHolder


    public void onBindViewHolder(UsersAdapterItemView holder, int position) {

        holder.itemView.setTag(users.get(position));

        User user = users.get(position);

        holder.tv_position.setText(position+"");
        holder.tv_pinCode.setText("*****");
        holder.tv_name.setText(user.getUserName());
        holder.tv_id.setText(user.getUserId());

        holder.iv_show.setOnClickListener(v -> {
            if(holder.tv_pinCode.getText().equals("*****"))holder.tv_pinCode.setText(user.getPinCode());
            else holder.tv_pinCode.setText("*****");
        });

        holder.iv_edit.setOnClickListener(v -> {
            if (listener != null) listener.onItemClicked(user);
        });


    }//onBindViewHolder


    public static class UsersAdapterItemView extends RecyclerView.ViewHolder {

        TextView tv_position;
        TextView tv_id;
        TextView tv_name;
        TextView tv_pinCode;
        ImageView iv_show, iv_edit;

        UsersAdapterItemView(View itemView) {

            super(itemView);
            tv_position = itemView.findViewById(R.id.tv_item_user_position);
            tv_id = itemView.findViewById(R.id.tv_item_user_id);
            tv_name = itemView.findViewById(R.id.tv_item_user_name);
            tv_pinCode = itemView.findViewById(R.id.tv_item_user_pin_code);
            iv_show = itemView.findViewById(R.id.iv_item_user_show);
            iv_edit = itemView.findViewById(R.id.iv_item_user_edit);
        }

    }//SheetsListAdapterItemView


}//sheetsListAdapter