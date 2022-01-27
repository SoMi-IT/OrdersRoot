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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


public class PanelFragment extends Fragment implements View.OnClickListener,  AdminActivityListener {


    private PanelFragmentListener panelFragmentListener;

    private ImageView iv_logo;
    private TextView tv_title, tv_id, tv_mail, tv_city, tv_address;
    private Button b_manageDevices, b_manageRestaurant, b_ordersStatus, b_tableStatus, b_logout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_panel, container, false);

        iv_logo = rootView.findViewById(R.id.iv_admin_panel_admin_logo);
        tv_title = rootView.findViewById(R.id.tv_admin_panel_admin_name);
        tv_id = rootView.findViewById(R.id.tv_admin_panel_admin_id);
        tv_mail = rootView.findViewById(R.id.tv_admin_panel_admin_email);
        tv_city = rootView.findViewById(R.id.tv_admin_panel_admin_city);
        tv_address = rootView.findViewById(R.id.tv_admin_panel_admin_address);

        b_manageDevices = rootView.findViewById(R.id.b_admin_panel_first_option);
        b_manageDevices.setOnClickListener(this);
        b_manageRestaurant = rootView.findViewById(R.id.b_admin_panel_second_option);
        b_manageRestaurant.setOnClickListener(this);
        b_ordersStatus = rootView.findViewById(R.id.b_admin_panel_third_option);
        b_ordersStatus.setOnClickListener(this);
        b_tableStatus = rootView.findViewById(R.id.b_admin_panel_forth_option);
        b_tableStatus.setOnClickListener(this);
        b_logout = rootView.findViewById(R.id.b_admin_panel_logout);
        b_logout.setOnClickListener(this);

        AdminActivity activity = (AdminActivity) getActivity();
        activity.setListenerForPanel(this);

        return rootView;

    }//onCreateView


    public void setPanelFragmentListener(PanelFragmentListener _panelFragmentListener) {

        panelFragmentListener = _panelFragmentListener;

    }


    public void onClick(View view) {

        if (panelFragmentListener != null && view == b_logout) {
            panelFragmentListener.onLogout();
        }

    }//onClick


    public void onAdminDataUpdated(Admin admin) {

        getActivity().runOnUiThread(() -> {

            tv_title.setText(admin.getTitle());
            tv_id.setText(admin.getId());
            tv_mail.setText(admin.getEmail());
            tv_city.setText(admin.getCity());
            tv_address.setText(admin.getAddress());

        });

    }//onAdminDataUpdated


    public void onUsersDataUpdated(ArrayList<User> users) { }//onUsersDataUpdated


}//AuthFragment

