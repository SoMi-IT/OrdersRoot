package com.somi.ordersroot.auth;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.somi.ordersroot.R;


public class AuthFragment extends Fragment implements View.OnClickListener {


    private AuthFragmentListener listener;

    private EditText et_firstField, et_secondField, et_pin;
    private FloatingActionButton b_confirm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_auth, container, false);

        et_firstField = rootView.findViewById(R.id.et_auth_first_field);
        et_secondField = rootView.findViewById(R.id.et_auth_second_field);
        et_pin = rootView.findViewById(R.id.et_auth_pin);

        b_confirm = rootView.findViewById(R.id.b_auth_confirm);
        b_confirm.setOnClickListener(this);

        ImageView iv_qr = rootView.findViewById(R.id.iv_auth_qr);
        iv_qr.setImageBitmap(generateQrCode(getDeviceId(getActivity())));

        return rootView;

    }//onCreateView


    private void initAuth(){

        String emailString = et_firstField.getText().toString();
        String pswString = et_secondField.getText().toString();
        String pinString = et_pin.getText().toString();

        boolean hasEmail = emailString.length() != 0;
        boolean hasPsw = pswString.length() != 0;
        boolean hasPin = pinString.length() != 0;

        et_firstField.setError(null);
        et_secondField.setError(null);
        et_pin.setError(null);

        if(!hasEmail && !hasPsw && !hasPin) {

            et_firstField.setError("type your email!");
            et_secondField.setError("type your password!");
            et_pin.setError("type your pin!");
            return;

        }else if(hasEmail && !hasPsw) {
            et_secondField.setError("type your password!");
            return;

        }else if(!hasEmail && hasPsw) {
            et_firstField.setError("type your email!");
            return;

        }else if(hasEmail && hasPsw) {
            listener.onAuthStartRequested(emailString, pswString);

        }else if(hasPin) {
            listener.onAuthStartRequested(pinString, null);

        }


        et_firstField.setError(null);
        et_secondField.setError(null);
        et_pin.setError(null);

    }//initAuth


    private Bitmap generateQrCode(String s){

        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = writer.encode(s, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    final TypedValue value1 = new TypedValue ();
                    getActivity().getTheme ().resolveAttribute (R.attr.colorOnPrimary, value1, true);

                    final TypedValue value2 = new TypedValue ();
                    getActivity().getTheme ().resolveAttribute (R.attr.colorOnSecondary, value2, true);

                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? value2.data: value1.data);
                }
            }
            return bmp;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;

        }

    }//generateQrCode


    private static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (mTelephony.getMeid() != null) deviceId = mTelephony.getMeid();
            else deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        }

        return deviceId;

    }//getDeviceId


    public void setListener(AuthFragmentListener _listener){
        listener = _listener;
    }//setListener


    public void onClick(View view) {
        if (view == b_confirm) initAuth();
    }//onClick



}//AuthFragment

