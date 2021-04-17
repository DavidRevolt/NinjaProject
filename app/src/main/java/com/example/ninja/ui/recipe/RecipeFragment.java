package com.example.ninja.ui.recipe;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ninja.R;
import com.example.ninja.ui.browse.BrowseViewModel;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        String recipeID = RecipeFragmentArgs.fromBundle(getArguments()).getRecipeID();
        Log.d("TAG","in RecipeFragment , Recipe ID:  " + recipeID);


        return root;
    }

}