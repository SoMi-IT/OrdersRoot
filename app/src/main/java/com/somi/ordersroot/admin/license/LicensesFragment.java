package com.somi.ordersroot.admin.license;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManager;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.AdminActivity;
import com.somi.ordersroot.admin.AdminActivityListener;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;


public class LicensesFragment extends Fragment implements View.OnClickListener, AdminActivityListener, LicensesAdapterListener, LicenseEditDialogListener {


    private RecyclerView rv_licenses;
    private LicensesAdapter licensesAdapter;

    private FirestoreAdminManager firestoreAdminManager;
    private LicenseEditDialog licenseEditDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_licenses, container, false);

        rv_licenses = rootView.findViewById(R.id.rv_admin_licenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_licenses.setLayoutManager(layoutManager);

        licensesAdapter = new LicensesAdapter();
        licensesAdapter.setListener(this);
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


    @Override
    public void onItemClicked(License license) {

        licenseEditDialog = new LicenseEditDialog(getActivity(), license);
        licenseEditDialog.setLicenseEditDialogListener(this);
        licenseEditDialog.show();

    }//onItemClicked

    public void onQrScanRequest() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        barcodeLauncher.launch(options);

    }//onQrScanRequest

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    if(licenseEditDialog != null && licenseEditDialog.isShowing())licenseEditDialog.setDeviceId(result.getContents());
                }

    });


}//LicensesFragment

