package com.example.ninja.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class RecipeModelFirebase {


    public void delRecipe(Recipe recipe, RecipeModel.DelRecipeListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes").document(recipe.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }


    public void addRecipe(Recipe recipe, RecipeModel.AddRecipeListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes").document(recipe.getId())
                .set(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","Recipe added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed adding Recipe");
                listener.onComplete();
            }
        });
    }

    public void updateRecipe(Recipe recipe, RecipeModel.UpdateRecipeListener listener) {
        addRecipe(recipe,listener);
    }

    public void GetAllRecipes(RecipeModel.GetAllRecipes listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recipe> data = new LinkedList<Recipe>();
                if (task.isSuccessful()){
                    Log.d("TAG", "<<<<GET ALL RECIPES FROM FIREBASE>>>");
                    for (DocumentSnapshot document:task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        Recipe rec = document.toObject(Recipe.class);
                        data.add(rec);
                    }
                }
                listener.onComplete(data);
            }
        });

    }


    public void getRecipe(String id, RecipeModel.GetRecipeListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("recipes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Recipe recipe = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        recipe = task.getResult().toObject(Recipe.class);
                    }
                }
                listener.onComplete(recipe);
            }
        });
    }
}
