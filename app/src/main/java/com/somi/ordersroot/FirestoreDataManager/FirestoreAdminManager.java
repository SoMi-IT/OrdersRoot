package com.somi.ordersroot.FirestoreDataManager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreAdminManager {


    private FirebaseAuth mAuth;
    private FirestoreAdminManagerListener listener;



    public FirestoreAdminManager() {
        mAuth = FirebaseAuth.getInstance();

    }

    public FirebaseAuth getMAuth() {

        return mAuth;

    }//getMAuth

    public void fetchAdminData() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());

        documentReference.addSnapshotListener((value, error) -> {


            if (error != null) {
                if(listener != null)listener.onDataRetrieveError(error.getMessage());
                return;
            }

            if (value != null && value.exists()) {

                Admin admin = new Admin();
                admin.setId(currentUser.getUid());
                admin.setEmail(currentUser.getEmail());

                if(value.getString("title") == null) admin.setTitle("");
                else admin.setTitle(value.getString("title"));

                if(value.getString("city") == null) admin.setCity("");
                else  admin.setCity(value.getString("city"));

                if(value.getString("address") == null) admin.setAddress("");
                else admin.setAddress(value.getString("address"));

                if(listener != null)listener.onAdminDataRetrieved(admin);

            } else {
                if(listener != null)listener.onDataRetrieveError("No Data!");
                return;
            }

        });


    }

    public void fetchLicensesData() {


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference documentReference  = firebaseFirestore.collection("licenses");


        documentReference.addSnapshotListener((value, error) -> {

            if (error != null) {
                if(listener != null)listener.onDataRetrieveError(error.getMessage());
                return;
            }

            if (value != null && !value.isEmpty()) {
                ArrayList<License> licenses = new ArrayList<>();

                for (int i=0; i<value.getDocuments().size(); i++){

                    if(value.getDocuments().get(i).getString("adminId") != null && value.getDocuments().get(i).getString("adminId").equals(currentUser.getUid())) {

                        License license = new License(value.getDocuments().get(i).getId());

                        if(value.getDocuments().get(i).getString("licenseName") == null) license.setLicenseName("");
                        else license.setLicenseName(value.getDocuments().get(i).getString("licenseName"));

                        if(value.getDocuments().get(i).getString("deviceId") == null) license.setDeviceId("");
                        else license.setDeviceId(value.getDocuments().get(i).getString("deviceId"));

                        licenses.add(license);

                    }

                }

                if(listener != null)listener.onLicensesDataRetrieved(licenses);

                } else {
                    if(listener != null)listener.onDataRetrieveError("No users Data!");

                }

            });


    }//fetchLicensesData


    public void fetchUsersData() {


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference documentReference  = firebaseFirestore.collection("users");


        documentReference.addSnapshotListener((value, error) -> {

            if (error != null) {
                if(listener != null)listener.onDataRetrieveError(error.getMessage());
                return;
            }

            if (value != null && !value.isEmpty()) {
                ArrayList<User> users = new ArrayList<>();

                for (int i=0; i<value.getDocuments().size(); i++){

                    if(value.getDocuments().get(i).getString("adminId") != null && value.getDocuments().get(i).getString("adminId").equals(currentUser.getUid())) {

                        User user = new User(value.getDocuments().get(i).getId());

                        if(value.getDocuments().get(i).getString("userName") == null) user.setUserName("");
                        else user.setUserName(value.getDocuments().get(i).getString("userName"));

                        if(value.getDocuments().get(i).getString("pinCode") == null) user.setPinCode("");
                        else user.setPinCode(value.getDocuments().get(i).getString("pinCode"));

                        users.add(user);

                    }

                }

                if(listener != null)listener.onUsersDataRetrieved(users);

            } else {
                if(listener != null)listener.onDataRetrieveError("No users Data!");

            }

        });


    }//fetchLicensesData


    public void updateLicenseData(License license) {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("licenses").document(license.getLicenseId());

        Map<String, Object> fflicense = new HashMap<>();
        fflicense.put("licenseName", license.getLicenseName());
        fflicense.put("deviceId", license.getDeviceId());


        documentReference.update(fflicense)
                .addOnFailureListener(e -> {
                    if(listener != null)listener.onDataRetrieveError("Error: " + e.getMessage());
                });



    }//fetchLicensesData

/*
    public void fetchLicensesData() {


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());


        documentReference.addSnapshotListener((value, error) -> {

            CollectionReference collectionReference = documentReference.collection("licenses");
            collectionReference.addSnapshotListener((value1, error1) -> {
                if (error1 != null) {
                    if(listener != null)listener.onDataRetrieveError(error1.getMessage());
                    return;
                }

                if (value1 != null && !value1.isEmpty()) {
                    ArrayList<License> licenses = new ArrayList<>();

                    for (int i=0; i<value1.getDocuments().size(); i++){

                        License license = new License(value1.getDocuments().get(i).getId());

                        if(value1.getDocuments().get(i).getString("licenseName") == null) license.setLicenseName("");
                        else license.setLicenseName(value1.getDocuments().get(i).getString("licenseName"));

                        if(value1.getDocuments().get(i).getString("deviceId") == null) license.setDeviceId("");
                        else license.setDeviceId(value1.getDocuments().get(i).getString("deviceId"));

                        licenses.add(license);

                    }

                    if(listener != null)listener.onLicensesDataRetrieved(licenses);

                } else {
                    if(listener != null)listener.onDataRetrieveError("No users Data!");

                }

            });

        });



    }//fetchLicensesData
*/
/*
    public void updateLicenseData(License license) {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());


        documentReference.addSnapshotListener((value, error) -> {


            Map<String, Object> fflicense = new HashMap<>();
            fflicense.put("licenseName", license.getLicenseName());
            fflicense.put("deviceId", license.getDeviceId());


            documentReference.collection("licenses").document(license.getLicenseId())
                    .update(fflicense)
                    .addOnFailureListener(e -> {
                        if(listener != null)listener.onDataRetrieveError("Error: " + e.getMessage());
                    });


            });



    }//fetchLicensesData
*/

/*
    public void fetchLicensesData() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());


        documentReference.addSnapshotListener((value, error) -> {


            CollectionReference collectionReference = documentReference.collection("licenses");
            collectionReference.addSnapshotListener((value1, error1) -> {

                if (error1 != null) {
                    if(listener != null)listener.onDataRetrieveError(error1.getMessage());
                    return;
                }

                if (value1 != null && !value1.isEmpty()) {

                    ArrayList<License> licenses = new ArrayList<>();

                    for (int i=0; i<value1.getDocuments().size(); i++){

                        License license = new License(value1.getDocuments().get(i).getId());

                        if(value1.getDocuments().get(i).getString("deviceId") == null) license.setDeviceId("");
                        else license.setDeviceId(value1.getDocuments().get(i).getString("deviceId"));

                        licenses.add(license);

                    }

                    if(listener != null)listener.onLicensesDataRetrieved(licenses);

                } else {
                    if(listener != null)listener.onDataRetrieveError("No users Data!");

                }

            });

        });



    }//fetchLicensesData
    */
/*


    public void fetchLicensesData() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());


        documentReference.addSnapshotListener((value, error) -> {


            CollectionReference collectionReference = documentReference.collection("licenses");
            collectionReference.addSnapshotListener((value1, error1) -> {

                if (error1 != null) {
                    if(listener != null)listener.onDataRetrieveError(error1.getMessage());
                    return;
                }

                if (value1 != null && !value1.isEmpty()) {

                    ArrayList<User> users = new ArrayList<>();

                    for (int i=0; i<value1.getDocuments().size(); i++){

                        users.add(new User(value1.getDocuments().get(i).getId(), value1.getDocuments().get(i).getString("deviceId"), value1.getDocuments().get(i).getString("pinCode")));

                    }

                    if(listener != null)listener.onUsersDataRetrieved(users);

                } else {
                    if(listener != null)listener.onDataRetrieveError("No users Data!");

                }

            });

        });



    }*/

    public void setListener(FirestoreAdminManagerListener firestoreAdminManagerListener) {
        listener = firestoreAdminManagerListener;
    }//setListener



}//FirestoreUsersManager