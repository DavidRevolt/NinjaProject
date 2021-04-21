package com.example.ninja.ui.home;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recipeRecyclerView;
    HomeRecipeListAdapter adapter;
    SwipeRefreshLayout swipeRefresh;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //starts ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Swipe2Refresh
        swipeRefresh = root.findViewById(R.id.HomeSwipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                refreshData();

            }
        });

        //List settings
        recipeRecyclerView = root.findViewById(R.id.HomeRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recipeRecyclerView.setLayoutManager(layoutManager);
        adapter = new HomeRecipeListAdapter(getLayoutInflater());
        adapter.data = homeViewModel.getRecipeList();

        //Navigation2Recipe
        adapter.setOnClickListener(new HomeRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String recipeID = homeViewModel.getRecipeList().getValue().get(position).getId();
                Log.d("TAG","HomeFragment Goin 2 Recipe Fragment With RecipeId: " + recipeID);
                Navigation.findNavController(root).navigate(HomeFragmentDirections.actionGlobalNavigationRecipe(recipeID));
            }
        });


        homeViewModel.getRecipeList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF RECIPE LIST TO HOME FRAGMENT!>>>");
                adapter.notifyDataSetChanged();
            }
        });
        refreshData();
        recipeRecyclerView.setAdapter(adapter);
        return root;
    }


    void refreshData(){
        Log.d("TAG", "<<<<Home Fragment Refreshing Data>>>");
        RecipeModel.instance.refreshGetAllRecipes(new RecipeModel.RefreshGetAllRecipesListener() {
            @Override
            public void onComplete() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

}

