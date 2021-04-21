package com.example.ninja.ui.profile;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import com.example.ninja.ui.home.HomeFragmentDirections;
import com.example.ninja.ui.home.HomeRecipeListAdapter;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    RecyclerView recipeRecyclerView;
    ProfileRecipeListAdapter adapter;
    SwipeRefreshLayout swipeRefresh;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //starts ViewModel
        profileViewModel =new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        //Swipe2Refresh
        swipeRefresh = root.findViewById(R.id.ProfileSwipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                refreshData();

            }
        });


        //List settings
        recipeRecyclerView = root.findViewById(R.id.ProfileRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recipeRecyclerView.setLayoutManager(layoutManager);

        adapter = new ProfileRecipeListAdapter(getLayoutInflater());
        adapter.data = profileViewModel.getRecipeList();

        //Navigation2Recipe
        adapter.setOnClickListener(new ProfileRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String recipeID = profileViewModel.getRecipeList().getValue().get(position).getId();
                Log.d("TAG","ProfileFragment Goin 2 Recipe Fragment With RecipeId: " + recipeID);
                Navigation.findNavController(root).navigate(ProfileFragmentDirections.actionGlobalNavigationRecipe(recipeID));
            }
        });


        profileViewModel.getRecipeList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF RECIPE LIST TO Profile FRAGMENT!>>>");
                adapter.notifyDataSetChanged();
            }
        });
        refreshData();
        recipeRecyclerView.setAdapter(adapter);
        return root;
    }
    void refreshData(){
        Log.d("TAG", "<<<<Profile Fragment Refreshing Data>>>");
        RecipeModel.instance.refreshGetAllUserRecipes(new RecipeModel.RefreshGetAllUserRecipesListener() {
            @Override
            public void onComplete() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

}