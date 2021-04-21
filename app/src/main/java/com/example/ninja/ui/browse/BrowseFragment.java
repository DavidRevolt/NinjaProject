package com.example.ninja.ui.browse;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import java.util.List;

public class BrowseFragment extends Fragment {

    private BrowseViewModel browseViewModel;
    RecyclerView recipeRecyclerView;
    BrowseRecipeListAdapter adapter;
    SwipeRefreshLayout swipeRefresh;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_browse, container, false);

        swipeRefresh = root.findViewById(R.id.BrowseSwipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                refreshData();

            }
        });

        //List settings
        recipeRecyclerView = root.findViewById(R.id.BrowseRecipeList);
        recipeRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recipeRecyclerView.setLayoutManager(layoutManager);
        adapter = new BrowseRecipeListAdapter(getLayoutInflater());
        adapter.data = browseViewModel.getRecipeList();

        //Navigation2Recipe
        adapter.setOnClickListener(new BrowseRecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String recipeID = browseViewModel.getRecipeList().getValue().get(position).getId();
                Log.d("TAG","BrowseFragment Goin 2 Recipe Fragment With RecipeId: " + recipeID);
                Navigation.findNavController(root).navigate(BrowseFragmentDirections.actionGlobalNavigationRecipe(recipeID));
            }
        });

        browseViewModel.getRecipeList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF RECIPE LIST TO Browse FRAGMENT!>>>");
                adapter.notifyDataSetChanged();
            }
        });
        refreshData();
        recipeRecyclerView.setAdapter(adapter);
        return root;
    }


    void refreshData(){
        Log.d("TAG", "<<<<Browse Fragment Refreshing Data>>>");
        RecipeModel.instance.refreshGetAllRecipes(new RecipeModel.RefreshGetAllRecipesListener() {
            @Override
            public void onComplete() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

}