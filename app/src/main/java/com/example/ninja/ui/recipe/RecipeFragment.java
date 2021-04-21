package com.example.ninja.ui.recipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ninja.R;
import com.example.ninja.model.Appliance;
import com.example.ninja.model.ApplianceModel;
import com.example.ninja.model.Category;
import com.example.ninja.model.CategoryModel;
import com.example.ninja.model.Recipe;
import com.example.ninja.model.RecipeModel;
import com.example.ninja.ui.browse.BrowseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    String recipeID;
    ImageView recipeImg;
    TextView recipeName;
    TextView prepTime;
    TextView cookTime;
    TextView totalTime;
    TextView category;
    TextView appliance;
    TextView instructions;
    TextView madeBy;
    Button editBtn;
    Button deleteBtn;
    SwipeRefreshLayout swipeRefresh;
    ProgressBar spinner;
    Recipe recipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeID = RecipeFragmentArgs.fromBundle(getArguments()).getRecipeID();
        recipeViewModel.setRecipeId(recipeID);
        Log.d("TAG","in RecipeFragment , Recipe ID:  " + recipeID);


        spinner = root.findViewById(R.id.RecipeFragment_Spinner);
        recipeImg = root.findViewById(R.id.RecipeFragm_Img);
        recipeName = root.findViewById(R.id.RecipeFragm_Recipe_Name);
        prepTime = root.findViewById(R.id.RecipeFragm_Prep_Time);
        cookTime = root.findViewById(R.id.RecipeFragm_Cook_Time);
        totalTime = root.findViewById(R.id.RecipeFragm_Total_Time);
        category = root.findViewById(R.id.RecipeFragm_Category);
        appliance = root.findViewById(R.id.RecipeFragm_Appliance);
        instructions = root.findViewById(R.id.RecipeFragm_Instructions);
        madeBy = root.findViewById(R.id.RecipeFragm_MadeBy);
        editBtn = root.findViewById(R.id.RecipeFragm_Edit_Button);
        deleteBtn  = root.findViewById(R.id.RecipeFragm_Del_Button);
        swipeRefresh = root.findViewById(R.id.RecipeFragm_SwipeRefresh);
        editBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Yep...", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                spinner.setVisibility(View.VISIBLE);
                                Navigation.findNavController(view)
                                        .popBackStack(R.id.navigation_home,false);
                                RecipeModel.instance.delRecipe(recipe, new RecipeModel.DelRecipeListener() {
                                    @Override
                                    public void onComplete() {
                                    }
                                });
                            }
                        })
                        .setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                builder.create();
                builder.show();
            }});

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","RecipeFragment Goin 2 Create/EDIT Fragment With Recipe: " + recipe.getTitle());
                Navigation.findNavController(root).navigate(RecipeFragmentDirections.actionNavigationRecipeToNavigationCreate(
                        recipe.getCookTime(),recipe.getPrepTime(),recipe.getTitle(),recipe.getCategoryID(),recipe.getApplianceID(),
                        recipe.getInstructions(),recipe.getId(),recipe.getImgURL(),appliance.getText().toString(),category.getText().toString()
                ));
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                refreshData();

            }
        });


        recipeViewModel.getApplianceList().observe(getViewLifecycleOwner(), new Observer<List<Appliance>>() {
            @Override
            public void onChanged(List<Appliance> app) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF CAT LIST TO HOME FRAGMENT!>>>");
            }
        });
        recipeViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> cat) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF APP LIST TO HOME FRAGMENT!>>>");
            }
        });
        recipeViewModel.getRecipe().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe rec) {
                Log.d("TAG", "<<<<NEW LIVEDATA OF RECIPE LIST TO HOME FRAGMENT!>>>");
            }
        });
        refreshData();
        return root;

    }




    void refreshData(){
        Log.d("TAG", "<<<<Recipe Fragment Refreshing Data>>>");

        RecipeModel.instance.refreshGetRecipe(recipeID, new RecipeModel.RefreshGetRecipeListener() {
            @Override
            public void onComplete() {
                recipe = recipeViewModel.getRecipe().getValue();

                CategoryModel.instance.refreshGetAllCategories(new CategoryModel.RefreshGetAllCategoriesListener() {
                    @Override
                    public void onComplete() {
                        String name =recipeViewModel.getCategoryList().getValue().get(Integer.parseInt(recipe.getCategoryID())-1).getName();
                        category.setText(name);
                    }
                });
                ApplianceModel.instance.refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener(){
                    @Override
                    public void onComplete() {
                        String name2 =recipeViewModel.getApplianceList().getValue().get(Integer.parseInt(recipe.getApplianceID())-1).getName();
                        appliance.setText(name2);
                    }

                });

                recipeName.setText(recipe.getTitle());
                prepTime.setText(String.valueOf(recipe.getPrepTime()));;
                cookTime.setText(String.valueOf(recipe.getCookTime()));
                totalTime.setText(String.valueOf(recipe.getTotalTime()));
                instructions.setText(recipe.getInstructions());
                recipeImg.setImageResource(R.drawable.plate);
                if (recipe.getImgURL() != null)
                    Picasso.get().load(recipe.getImgURL()).placeholder(R.drawable.plate).into(recipeImg);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUserEmail = user.getEmail();
                if(recipe.getUserCreatorId().equals(currentUserEmail)){
                    madeBy.setVisibility(View.INVISIBLE);
                    editBtn.setVisibility(View.VISIBLE);;
                    deleteBtn.setVisibility(View.VISIBLE);;
                }
                else
                madeBy.append(recipe.getUserCreatorId());
                swipeRefresh.setRefreshing(false);

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getTitle());
            }
        });
    }
}