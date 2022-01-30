package com.somi.ordersroot.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;


public class PanelFragment extends Fragment implements View.OnClickListener {


    private PanelFragmentListener panelFragmentListener;

    private ImageView iv_logo;
    private TextView tv_title, tv_id, tv_mail, tv_city, tv_address;
    private Button b_manageLicenses, b_manageUsers, b_manageRestaurant, b_ordersStatus, b_tableStatus, b_logout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_panel, container, false);

        iv_logo = rootView.findViewById(R.id.iv_admin_panel_admin_logo);
        tv_title = rootView.findViewById(R.id.tv_admin_panel_admin_name);
        tv_id = rootView.findViewById(R.id.tv_admin_panel_admin_id);
        tv_mail = rootView.findViewById(R.id.tv_admin_panel_admin_email);
        tv_city = rootView.findViewById(R.id.tv_admin_panel_admin_city);
        tv_address = rootView.findViewById(R.id.tv_admin_panel_admin_address);

        b_manageLicenses = rootView.findViewById(R.id.b_admin_panel_licenses);
        b_manageLicenses.setOnClickListener(this);
        b_manageUsers = rootView.findViewById(R.id.b_admin_panel_users);
        b_manageUsers.setOnClickListener(this);
        b_manageRestaurant = rootView.findViewById(R.id.b_admin_panel_second_option);
        b_manageRestaurant.setOnClickListener(this);
        b_ordersStatus = rootView.findViewById(R.id.b_admin_panel_third_option);
        b_ordersStatus.setOnClickListener(this);
        b_tableStatus = rootView.findViewById(R.id.b_admin_panel_forth_option);
        b_tableStatus.setOnClickListener(this);
        b_logout = rootView.findViewById(R.id.b_admin_panel_logout);
        b_logout.setOnClickListener(this);

        return rootView;

    }//onCreateView


    public void setPanelFragmentListener(PanelFragmentListener _panelFragmentListener) {

        panelFragmentListener = _panelFragmentListener;

    }


    public void onClick(View view) {

        if(panelFragmentListener == null) return;

        if (view == b_logout) {
            panelFragmentListener.onLogout();

        }else if (view == b_manageLicenses) {
            panelFragmentListener.onManageLicenses();

        }else if (view == b_manageUsers) {
            panelFragmentListener.onManageUsers();
        }

    }//onClick


    public void adminDataUpdated(Admin admin) {

        getActivity().runOnUiThread(() -> {

            tv_title.setText(admin.getTitle());
            tv_id.setText(admin.getId());
            tv_mail.setText(admin.getEmail());
            tv_city.setText(admin.getCity());
            tv_address.setText(admin.getAddress());

        });

    }//onAdminDataUpdated


    public void onUsersDataUpdated(ArrayList<User> users) { }//onUsersDataUpdated
    public void onLicensesDataUpdated(ArrayList<License> licenses) {}//onLicensesDataUpdated


}//AuthFragment

