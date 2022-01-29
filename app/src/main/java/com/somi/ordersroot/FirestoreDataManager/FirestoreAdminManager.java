package com.somi.ordersroot.FirestoreDataManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.license.License;

import java.util.ArrayList;

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



    }
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