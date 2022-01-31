package com.somi.ordersroot.auth;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.somi.ordersroot.FirestoreDataManager.FirestoreAdminManagerListener;
import com.somi.ordersroot.admin.Admin;
import com.somi.ordersroot.admin.AdminActivity;
import com.somi.ordersroot.admin.license.License;
import com.somi.ordersroot.admin.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseAuthManager {


    private FirebaseAuth mAuth;
    private FirebaseAuthManagerListener listener;



    public FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();

    }

    public FirebaseAuth getMAuth() {

        return mAuth;

    }//getMAuth

    public void logInAsAdmin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) listener.onAdminLogged();
            else  listener.onAuthError("Authentication failed : " + task.getException());

        });

    }//logInAsAdmin


    public void logInAsUser(String pinCode, String deviceID) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = firebaseFirestore.collection("licenses");

        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

            for (int i = 0; i < documents.size(); i++) {

                Log.d("XXX", "LICENZA: " + documents.get(i).getId() + "Admin: " + documents.get(i).getString("adminId"));

                if (documents.get(i).getString("deviceId") != null && documents.get(i).getString("deviceId").equals(deviceID)) {

                    Log.d("XXX", "Trovato: " + documents.get(i).getId());

                    if(documents.get(i).getString("adminId") != null)findUserByAdmin(documents.get(i).getString("adminId"), pinCode);
                    else listener.onAuthError("This device is not associated");
                    break;

                }
            }

            Log.d("XXX", "-----------");

            listener.onAuthError("Authentication failed : No user found");


        });

    }//logInAsAdmin


    private void findUserByAdmin(String adminId, String pinCode) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        CollectionReference collectionReference2 = firebaseFirestore.collection("users");
        collectionReference2.get().addOnSuccessListener(queryDocumentSnapshots1 -> {

            List<DocumentSnapshot> documents2 = queryDocumentSnapshots1.getDocuments();

            for (int j = 0; j < documents2.size(); j++) {

                Log.d("XXX", "USER: " + documents2.get(j).getId() + "Admin: " + documents2.get(j).getString("adminId") + "  -> PinCode:" + documents2.get(j).getString("pinCode"));

                if (documents2.get(j).getString("adminId") != null && adminId.equals(documents2.get(j).getString("adminId"))) {

                    Log.d("XXX", "^ Questa licenza è del nostro admin");

                    if(documents2.get(j).getString("pinCode") != null && pinCode.equals(documents2.get(j).getString("pinCode"))) {
                        Log.d("XXX", "Trovato: " + documents2.get(j).getId());

                        listener.onUserLogged();
                        break;
                        //f6badbeee9ebc79d
                    }



                }
            }

            Log.d("XXX", "--------------------------------------------");

        });
    }


    public void setListener(FirebaseAuthManagerListener firebaseAuthManagerListener) {
        listener = firebaseAuthManagerListener;
    }//setListener



}//FirestoreUsersManager