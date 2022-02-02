package com.somi.ordersroot.tables;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.user.User;
import com.somi.ordersroot.admin.user.UserEditDialog;
import com.somi.ordersroot.admin.user.UserEditDialogListener;
import com.somi.ordersroot.admin.user.UsersAdapter;
import com.somi.ordersroot.admin.user.UsersAdapterListener;

import java.util.ArrayList;


public class TablesFragment extends Fragment implements UsersAdapterListener, UserEditDialogListener {


    private RecyclerView rv_users;
    private UsersAdapter usersAdapter;

    private UserEditDialogListener listener;
    private UserEditDialog userEditDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_main, container, false);

        rv_users = rootView.findViewById(R.id.rv_admin_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_users.setLayoutManager(layoutManager);

        usersAdapter = new UsersAdapter();
        usersAdapter.setListener(this);
        rv_users.setAdapter(usersAdapter);

        return rootView;

    }//onCreateView


    public void setListener(UserEditDialogListener userEditDialogListener) {
        listener = userEditDialogListener;
    }//setListener


    public void usersDataUpdated(ArrayList<User> users) {

        getActivity().runOnUiThread(() -> {

            if(usersAdapter == null) return;

            usersAdapter.updateAllData(users);

        });

    }//onUsersDataUpdated


    @Override
    public void onItemClicked(User user) {
        userEditDialog = new UserEditDialog(getActivity(), user);
        userEditDialog.setUserEditDialogListener(this);
        userEditDialog.show();
    }

    @Override
    public void onUserEdited(User user) {
        if(listener != null)listener.onUserEdited(user);
        if(usersAdapter != null)usersAdapter.refreshView(user.getUserId());
    }


}//AuthFragment

