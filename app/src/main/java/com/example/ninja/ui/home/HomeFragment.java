package com.example.ninja.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import com.example.ninja.model.RecipeModelSQL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<Recipe> recipes;
    RecyclerView recipeRecyclerView;
    ProgressBar homeProgressBar;
    HomeRecipeListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //starts ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /* final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        homeProgressBar = root.findViewById(R.id.homeProgressBar);
        homeProgressBar.setVisibility(View.INVISIBLE);
        recipes = new LinkedList<Recipe>();


        //List settings
        recipeRecyclerView = root.findViewById(R.id.HomeRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recipeRecyclerView.setLayoutManager(layoutManager);
        adapter = new HomeRecipeListAdapter(getLayoutInflater());
        adapter.data = recipes;
        recipeRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new HomeRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","row was clicked " + position);

            }
        });

        reloadData();
        return root;
    }




    void reloadData(){
        homeProgressBar.setVisibility(View.VISIBLE);
        RecipeModel.instance.GetAllRecipes(new RecipeModel.GetAllRecipes() {
            @Override
            public void onComplete(List<Recipe> data) {
                recipes=data;
                homeProgressBar.setVisibility(View.INVISIBLE);
                adapter.data = recipes;
                adapter.notifyDataSetChanged();
            }
        });
    }
}