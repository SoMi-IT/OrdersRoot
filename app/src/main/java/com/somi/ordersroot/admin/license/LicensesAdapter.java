package com.somi.ordersroot.admin.license;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.somi.ordersroot.R;

import java.util.ArrayList;


public class LicensesAdapter extends RecyclerView.Adapter<LicensesAdapter.LicensesAdapterItemView> {


    private ArrayList<License> licenses;
    private ArrayList<License> newTempLicenses;
    private LicensesAdapterListener listener;


    public LicensesAdapter() {
        if(licenses == null) licenses = new ArrayList<>();

    }//constructor


    public void setListener(LicensesAdapterListener _listener) {
        listener = _listener;

    }//setListener


    public void updateAllData(ArrayList<License> _licenses) {

        newTempLicenses = _licenses;

        if(licenses == null || licenses.size() == 0) {
            licenses = newTempLicenses;
            notifyDataSetChanged();

        }else {

            checkAndUpdateOrDeleteData();

            for (int i = 0; i < newTempLicenses.size(); i++) {

                licenses.add(newTempLicenses.get(i));
                notifyItemInserted(licenses.size() - 1);
                notifyItemRangeChanged(i, licenses.size());

            }

        }

    }//updateData


    private License findDataInNewData(License license) {

        for (int i = 0; i < newTempLicenses.size(); i++) {

            if (newTempLicenses.get(i).getLicenseId().equals(license.getLicenseId())) {

                License licenseFounded = newTempLicenses.get(i);
                newTempLicenses.remove(i);
                return licenseFounded;

            }

        }

        return null;

    }//findDataInNewData


    private void checkAndUpdateOrDeleteData() {

        for (int i = 0; i < licenses.size(); i++) {
            License tempLicense = findDataInNewData(licenses.get(i));

            if(tempLicense != null){
                if(!licenses.get(i).getDeviceId().equals(tempLicense.getDeviceId()) || !licenses.get(i).getLicenseName().equals(tempLicense.getLicenseName())) {

                    licenses.set(i, tempLicense);
                    notifyItemChanged(i);
                }

            }else {
                licenses.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, licenses.size());
            }

        }

    }//checkAndUpdateOrDeleteData


    public ArrayList<License> getData() {
        return licenses;

    }//getData


    public License getDataById(String objectId) {
        if (licenses == null) return null;
        for (License data: licenses) {
            if (data.getLicenseId().equals(objectId)) return data;
        }

        return null;

    }//getDataById


    public void refreshView(String objectId) {

        if (licenses == null) return;

        for (int i = 0; i < licenses.size(); i++) {

            if (licenses.get(i).getLicenseId().equals(objectId)) {

                notifyItemChanged(i);
                break;

            }

        }

    }//refreshView


    public int getItemCount() {
        if (licenses != null) {
            return licenses.size();
        }else return 0;

    }//getItemCount


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    public LicensesAdapterItemView onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_license, parent, false);

        return new LicensesAdapterItemView(v);

    }//onCreateViewHolder


    public void onBindViewHolder(LicensesAdapterItemView holder, int position) {

        holder.itemView.setTag(licenses.get(position));

        License license = licenses.get(position);

        holder.tv_position.setText(position+"");
        holder.tv_id.setText("*****");
        holder.tv_name.setText(license.getLicenseName());
        holder.tv_deviceID.setText(license.getDeviceId());

        holder.iv_show.setOnClickListener(v -> {
            if(holder.tv_id.getText().equals("*****"))holder.tv_id.setText(license.getLicenseId());
            else holder.tv_id.setText("*****");
        });

        holder.iv_edit.setOnClickListener(v -> {
            if (listener != null) listener.onItemClicked(license);
        });


    }//onBindViewHolder


    public static class LicensesAdapterItemView extends RecyclerView.ViewHolder {

        TextView tv_position;
        TextView tv_id;
        TextView tv_name;
        TextView tv_deviceID;
        ImageView iv_show, iv_edit;

        LicensesAdapterItemView(View itemView) {

            super(itemView);
            tv_position = itemView.findViewById(R.id.tv_item_license_position);
            tv_id = itemView.findViewById(R.id.tv_item_license_id);
            tv_name = itemView.findViewById(R.id.tv_item_license_name);
            tv_deviceID = itemView.findViewById(R.id.tv_item_license_device_id);
            iv_show = itemView.findViewById(R.id.iv_item_license_show);
            iv_edit = itemView.findViewById(R.id.iv_item_license_edit);

        }

    }//UsersAdapterItemView


}//LicensesAdapter