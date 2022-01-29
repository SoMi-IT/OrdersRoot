package com.somi.ordersroot.admin.user;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public void updateData(ArrayList<User> _users) {

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
            User tempLicense = findDataInNewData(users.get(i));

            if(tempLicense != null){

                if(!users.get(i).getDeviceId().equals(tempLicense.getDeviceId()) || !users.get(i).getPinCode().equals(tempLicense.getPinCode())) {
                    users.set(i, tempLicense);
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





}//sheetsListAdapter
