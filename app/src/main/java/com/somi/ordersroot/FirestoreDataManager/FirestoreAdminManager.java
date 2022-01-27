package com.somi.ordersroot.FirestoreDataManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.somi.ordersroot.R;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.data.User;

import java.util.ArrayList;

public class FirestoreAdminManager {


    private FirestoreAdminManagerListener listener;



    public void fetchAdminData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                admin.setTitle(value.getString("title"));
                admin.setCity(value.getString("city"));
                admin.setAddress(value.getString("address"));

                if(listener != null)listener.onAdminDataRetrieved(admin);

            } else {
                if(listener != null)listener.onDataRetrieveError("No Data!");
                return;
            }

        });

    }

    public void fetchUsersData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)return;

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference  = firebaseFirestore.collection("admins").document(currentUser.getUid());


        documentReference.addSnapshotListener((value, error) -> {


            CollectionReference collectionReference = documentReference.collection("users");
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



    }

    public void setListener(FirestoreAdminManagerListener firestoreAdminManagerListener) {
        listener = firestoreAdminManagerListener;
    }//setListener



}//FirestoreUsersManager