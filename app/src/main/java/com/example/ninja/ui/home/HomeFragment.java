package com.example.ninja.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    //ViewModel declaration
    private HomeViewModel homeViewModel;
    private List<Recipe> recipes;
    RecyclerView recipeRecyclerView;

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

        //List settings
        recipeRecyclerView = root.findViewById(R.id.HomeRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recipeRecyclerView.setLayoutManager(layoutManager);



        //get recipes data setting
        Recipe rec = new Recipe();
        rec.setId("1");
        rec.setTitle("DAVID RECIPE");
        rec.setPrepTime(1);
        rec.setCookTime(222);
        recipes = new LinkedList<Recipe>();


        recipes.add(rec);


        HomeRecipeListAdapter adapter = new HomeRecipeListAdapter(getLayoutInflater());
        adapter.data = recipes;
        recipeRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new HomeRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","row was clicked " + position);

            }
        });




        return root;
    }
}