package com.example.ninja.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class ApplianceModelFirebase {


    public void delAppliance(Appliance appliance, ApplianceModel.DelApplianceListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("appliances").document(appliance.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }


    public void addAppliance(Appliance appliance, ApplianceModel.AddApplianceListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("appliances").document(appliance.getId())
                .set(appliance.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","appliance added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed adding appliance");
                listener.onComplete();
            }
        });
    }

    public void updateAppliance(Appliance appliance, ApplianceModel.UpdateApplianceListener listener) {
        addAppliance(appliance,listener);
    }

    public void getAllAppliances(long lastUpdated,ApplianceModel.GetAllAppliancesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("appliances").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Appliance> data = new LinkedList<Appliance>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Appliance app = new Appliance();
                        app.fromMap(doc.getData());
                        data.add(app);
                    }
                }
                listener.onComplete(data);
            }
        });
    }


    public void getAppliance(String id, ApplianceModel.GetApplianceListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("appliances").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Appliance appliance = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        appliance = new Appliance();
                        appliance.fromMap(doc.getData());
                        //appliance = task.getResult().toObject(Appliance.class);
                    }
                }
                listener.onComplete(appliance);
            }
        });
    }
}
