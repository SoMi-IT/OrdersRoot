package com.somi.ordersroot.admin.license;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.somi.ordersroot.R;


public class LicenseEditDialog extends Dialog implements View.OnClickListener {


    private LicenseEditDialogListener listener;

    private TextView tv_licenseId;
    private EditText et_deviceId;
    private ImageButton ib_scan;
    private Button b_confirm, b_dismiss;

    public LicenseEditDialog(Activity _context, License license) {

        super(_context);


        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_license_edit);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        tv_licenseId = findViewById(R.id.tv_dialog_license_edit_id);
        et_deviceId = findViewById(R.id.et_dialog_license_edit_device_id);

        ib_scan = findViewById(R.id.ib_dialog_license_edit_qr_scan);
        ib_scan.setOnClickListener(this);
        b_confirm = findViewById(R.id.b_dialog_license_edit_confirm);
        b_confirm.setOnClickListener(this);
        b_dismiss = findViewById(R.id.b_dialog_license_edit_dismiss);
        b_dismiss.setOnClickListener(this);

        if (license != null) {

            if (license.getLicenseId() != null) tv_licenseId.setText(license.getLicenseId());

            if (license.getDeviceId() != null) et_deviceId.setText(license.getDeviceId());

        }


    }//CheckPasswordDialog


    public void setLicenseEditDialogListener(LicenseEditDialogListener licenseEditDialogListener) {
        listener = licenseEditDialogListener;

    }//setLicenseEditDialogListener


    public void setDeviceId(String deviceId){
        if(et_deviceId != null) et_deviceId.setText(deviceId);

    }//setDeviceId

    public void onClick(View view) {

        if(view == b_dismiss)dismiss();
        else if(view == b_confirm) {

        }else if(view == ib_scan) {
            if(listener != null)listener.onQrScanRequest();
        }
    }



}//InfoDialog