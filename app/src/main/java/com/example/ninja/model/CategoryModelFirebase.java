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

public class CategoryModelFirebase {


    public void delCategory(Category category, CategoryModel.DelCategoryListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").document(category.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }


    public void addCategory(Category category, CategoryModel.AddCategoryListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").document(category.getId())
                .set(category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","Category added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed adding Category");
                listener.onComplete();
            }
        });
    }

    public void updateCategory(Category category, CategoryModel.UpdateCategoryListener listener) {
        addCategory(category,listener);
    }

    public void GetAllCategories(CategoryModel.GetAllCategoriesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Category> data = new LinkedList<Category>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Category cat = doc.toObject(Category.class);
                        data.add(cat);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void getAllCategoryRecipes(String id, CategoryModel.GetCategoryRecipesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").whereEqualTo("categoryID",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recipe> data = new LinkedList<Recipe>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Recipe rec = doc.toObject(Recipe.class);
                        data.add(rec);
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void getCategory(String id, CategoryModel.GetCategoryListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Category category = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        category = task.getResult().toObject(Category.class);
                    }
                }
                listener.onComplete(category);
            }
        });
    }
}
