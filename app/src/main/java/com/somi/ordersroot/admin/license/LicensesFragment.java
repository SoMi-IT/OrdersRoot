package com.somi.ordersroot.admin.license;


import android.os.Bundle;
import android.util.Log;
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


public class LicensesFragment extends Fragment implements LicensesAdapterListener, LicenseEditDialogListener {


    private RecyclerView rv_licenses;
    private LicensesAdapter licensesAdapter;

    private LicensesFragmentListener listener;
    private LicenseEditDialog licenseEditDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_admin_licenses, container, false);

        rv_licenses = rootView.findViewById(R.id.rv_admin_licenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_licenses.setLayoutManager(layoutManager);

        licensesAdapter = new LicensesAdapter();
        licensesAdapter.setListener(this);
        rv_licenses.setAdapter(licensesAdapter);

        return rootView;

    }//onCreateView


    public void setListener(LicensesFragmentListener licensesFragmentListener) {
        listener = licensesFragmentListener;
    }//setListener


    public void licensesDataUpdated(ArrayList<License> licenses) {

        getActivity().runOnUiThread(() -> {

            if(licensesAdapter == null) return;

            licensesAdapter.updateAllData(licenses);

        });

    }//licensesDataUpdated



    @Override
    public void onItemClicked(License license) {
        licenseEditDialog = new LicenseEditDialog(getActivity(), license);
        licenseEditDialog.setLicenseEditDialogListener(this);
        licenseEditDialog.show();

    }//onItemClicked

    public void onQrScanRequest() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);

    }//onQrScanRequest


    public void onLicenseEdited(License license) {
        if(listener != null)listener.onLicenseDataChanged(license);
        if(licensesAdapter != null)licensesAdapter.refreshView(license.getLicenseId());

    }//onLicenseEdited


    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    if(licenseEditDialog != null && licenseEditDialog.isShowing())licenseEditDialog.setDeviceId(result.getContents());
                }

    });


}//LicensesFragment

