package com.somi.ordersroot.admin.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.AdminActivity;
import com.somi.ordersroot.admin.AdminActivityListener;
import com.somi.ordersroot.admin.license.License;

import java.util.ArrayList;


public class UsersFragment extends Fragment implements View.OnClickListener, AdminActivityListener {


    private RecyclerView rv_users;
    private UsersAdapter usersAdapter;

    private FirestoreAdminManager firestoreAdminManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_main, container, false);

        rv_users = rootView.findViewById(R.id.rv_admin_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_users.setLayoutManager(layoutManager);

        usersAdapter = new UsersAdapter();
        rv_users.setAdapter(usersAdapter);

        AdminActivity activity = (AdminActivity) getActivity();
        activity.setListenerForUser(this);

        return rootView;

    }//onCreateView

    public void onClick(View view) {

    }//onClick


    public void onAdminDataUpdated(Admin admin) {}//onAdminDataUpdated
    public void onLicensesDataUpdated(ArrayList<License> licenses) {}//onLicensesDataUpdated


    public void onUsersDataUpdated(ArrayList<User> users) {

        getActivity().runOnUiThread(() -> {

            if(usersAdapter == null) return;

            usersAdapter.updateData(users);


        });

    }//onUsersDataUpdated


}//AuthFragment

