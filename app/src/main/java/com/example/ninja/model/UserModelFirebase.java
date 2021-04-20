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

public class UserModelFirebase {


    public void getAllUserRecipes(String id, UserModel.GetUserRecipesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("userCreatorId",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recipe> data = new LinkedList<Recipe>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Recipe rec = new Recipe();
                        rec.fromMap(doc.getData());
                        //Recipe rec = doc.toObject(Recipe.class);
                        data.add(rec);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

}
