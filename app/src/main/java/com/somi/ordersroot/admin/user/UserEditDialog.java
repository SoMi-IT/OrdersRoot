package com.somi.ordersroot.admin.user;


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
import android.widget.TextView;

import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.license.LicenseEditDialogListener;


public class UserEditDialog extends Dialog implements View.OnClickListener {


    private UserEditDialogListener listener;

    private User user;

    private TextView tv_userId;
    private EditText et_pinCode, et_userName;
    private Button b_confirm, b_dismiss;


    public UserEditDialog(Activity _context, User _user) {

        super(_context);
        user = _user;

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);

        this.getWindow().setBackgroundDrawable(inset);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_user_edit);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        tv_userId = findViewById(R.id.tv_dialog_user_edit_id);
        et_pinCode = findViewById(R.id.et_dialog_user_edit_pin_code);
        et_userName = findViewById(R.id.et_dialog_user_edit_user_name);

        b_confirm = findViewById(R.id.b_dialog_user_edit_confirm);
        b_confirm.setOnClickListener(this);
        b_dismiss = findViewById(R.id.b_dialog_user_edit_dismiss);
        b_dismiss.setOnClickListener(this);

        if (user != null) {

            if (user.getUserId() != null) tv_userId.setText(user.getUserId());

            if (user.getPinCode() != null) et_pinCode.setText(user.getPinCode());

            if (user.getUserName() != null) et_userName.setText(user.getUserName());

        }


    }//UserEditDialog


    public void setUserEditDialogListener(UserEditDialogListener userEditDialogListener) {
        listener = userEditDialogListener;

    }//setUserEditDialogListener


    public void onClick(View view) {

        if(view == b_dismiss)dismiss();
        else if(view == b_confirm) {

            if(et_userName != null) user.setUserName(et_userName.getText().toString());

            if(et_pinCode != null) {
                if(et_pinCode.getText().length() <= 4) {
                    et_pinCode.setError("PinCode length must be equal or greater than 4");
                    return;
                } else  {
                    et_pinCode.setError(null);
                    user.setPinCode(et_pinCode.getText().toString());
                }

            }

            if(listener != null)listener.onUserEdited(user);
            dismiss();

        }

    }


}//UserEditDialog