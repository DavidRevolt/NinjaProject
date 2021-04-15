package com.example.ninja.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ninja.R;
import com.example.ninja.model.Appliance;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recipeRecyclerView;
    ProgressBar homeProgressBar;
    HomeRecipeListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //starts ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeProgressBar = root.findViewById(R.id.homeProgressBar);
        homeProgressBar.setVisibility(View.INVISIBLE);

        //List settings
        recipeRecyclerView = root.findViewById(R.id.HomeRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recipeRecyclerView.setLayoutManager(layoutManager);

        adapter = new HomeRecipeListAdapter(getLayoutInflater());
        adapter.data = homeViewModel.getRecipeList();


        homeViewModel.getRecipeList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d("TAG", "<<<<LIVEDATA CHANGED!>>>");
                adapter.notifyDataSetChanged();
            }
        });
        refreshData();
        recipeRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new HomeRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","row was clicked " + position);

            }
        });




        return root;
    }


    void refreshData(){
        Log.d("TAG", "<<<<REFRESHING>>>");
        homeProgressBar.setVisibility(View.VISIBLE);
        RecipeModel.instance.refreshGetAllRecipes(new RecipeModel.refreshGetAllRecipesListener() {
            @Override
            public void onComplete() {
                homeProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }





}