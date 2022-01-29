package com.somi.ordersroot.admin.license;


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
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;


public class LicensesFragment extends Fragment implements View.OnClickListener, AdminActivityListener {


    private RecyclerView rv_licenses;
    private LicensesAdapter licensesAdapter;

    private FirestoreAdminManager firestoreAdminManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_licenses, container, false);

        rv_licenses = rootView.findViewById(R.id.rv_admin_licenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_licenses.setLayoutManager(layoutManager);

        licensesAdapter = new LicensesAdapter();
        rv_licenses.setAdapter(licensesAdapter);

        AdminActivity activity = (AdminActivity) getActivity();
        activity.setListenerForLicenses(this);

        return rootView;

    }//onCreateView

    public void onClick(View view) {

    }//onClick


    public void onAdminDataUpdated(Admin admin) {}//onAdminDataUpdated
    public void onUsersDataUpdated(ArrayList<User> users) {}//onUsersDataUpdated


    public void onLicensesDataUpdated(ArrayList<License> licenses) {

        getActivity().runOnUiThread(() -> {

            if(licensesAdapter == null) return;

            licensesAdapter.updateData(licenses);


        });

    }//onLicensesDataUpdated


}//LicensesFragment

