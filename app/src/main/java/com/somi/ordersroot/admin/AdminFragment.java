package com.somi.ordersroot.admin;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManagerListener;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.data.User;
import com.somi.ordersroot.auth.AuthFragmentListener;

import java.util.ArrayList;


public class AdminFragment extends Fragment implements View.OnClickListener, AdminActivityListener {


    private RecyclerView rv_users;
    private UsersAdapter usersAdapter;

    private FirestoreAdminManager firestoreAdminManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_main, container, false);

        rv_users = rootView.findViewById(R.id.rv_admin_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_users.setLayoutManager(layoutManager);

        usersAdapter = new UsersAdapter(getActivity());
        rv_users.setAdapter(usersAdapter);

        AdminActivity activity = (AdminActivity) getActivity();
        activity.setListenerForMain(this);

        return rootView;

    }//onCreateView

    public void onClick(View view) {

    }//onClick


    public void onAdminDataUpdated(Admin admin) {}//onAdminDataUpdated


    public void onUsersDataUpdated(ArrayList<User> users) {



        getActivity().runOnUiThread(() -> {

            if(usersAdapter == null) return;

            usersAdapter.updateUsers(users);


        });

    }//onUsersDataUpdated


}//AuthFragment

