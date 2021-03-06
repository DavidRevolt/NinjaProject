package com.example.ninja.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
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
                .set(recipe.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete();
            }
        });
    }


    public void updateRecipe(Recipe recipe, RecipeModel.UpdateRecipeListener listener) {
        addRecipe(recipe,listener);
    }




    public void getAllRecipes(long lastUpdated, RecipeModel.GetAllRecipesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated,0);
        db.collection("recipes").whereGreaterThanOrEqualTo("lastUpdated",ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recipe> data = new LinkedList<Recipe>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot document:task.getResult()) {
                        Recipe rec = new Recipe();
                        rec.fromMap(document.getData());
                        data.add(rec);
                    }
                }
                listener.onComplete(data);
            }
        });
    }



    public void getAllUserRecipes(long lastUpdated, String uid, RecipeModel.GetAllUserRecipesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated,0);
        db.collection("recipes").whereEqualTo("userCreatorId",uid).whereGreaterThanOrEqualTo("lastUpdated",ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Recipe> data = new LinkedList<Recipe>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot document:task.getResult()) {
                        Recipe rec = new Recipe();
                        rec.fromMap(document.getData());
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
                    if (doc.exists()) {
                        recipe = new Recipe();
                        recipe.fromMap(doc.getData());
                    }
                }
                listener.onComplete(recipe);
            }
        });
    }



    public void uploadImage(Bitmap imageBmp, String name, final RecipeModel.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }


}
