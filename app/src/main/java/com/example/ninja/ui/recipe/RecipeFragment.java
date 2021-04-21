package com.example.ninja.ui.recipe;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import java.util.List;

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
    TextView prepTitle;
    TextView cookTitle;
    TextView totalTitle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeID = RecipeFragmentArgs.fromBundle(getArguments()).getRecipeID();
        recipeViewModel.setRecipeId(recipeID);


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
        prepTitle = root.findViewById(R.id.RecipeFragm_PrepTitle);;
        cookTitle = root.findViewById(R.id.RecipeFragm_Cook_Title);;
        totalTitle = root.findViewById(R.id.RecipeFragm_Total_Title);;

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
                            }
                        });

                builder.create();
                builder.show();
            }});

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
        recipeViewModel.getCategoryList().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> cat) {
            }
        });
        recipeViewModel.getRecipe().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe rec) {
            }
        });
        refreshData();
        return root;

    }




    void refreshData(){

        RecipeModel.instance.refreshGetRecipe(recipeID, new RecipeModel.RefreshGetRecipeListener() {
            @Override
            public void onComplete() {

                recipe = recipeViewModel.getRecipe().getValue();

                if(recipe != null)
                {
                    spinner.setVisibility(View.VISIBLE);
                    CategoryModel.instance.refreshGetAllCategories(new CategoryModel.RefreshGetAllCategoriesListener() {
                        @Override
                        public void onComplete() {
                            if(!recipeViewModel.getCategoryList().getValue().isEmpty()) {
                                String name = recipeViewModel.getCategoryList().getValue().get(Integer.parseInt(recipe.getCategoryID()) - 1).getName();
                                category.setText(name);
                                spinner.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    spinner.setVisibility(View.VISIBLE);
                    ApplianceModel.instance.refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener(){
                        @Override
                        public void onComplete() {
                            if(!recipeViewModel.getApplianceList().getValue().isEmpty())
                            {
                                String name2 =recipeViewModel.getApplianceList().getValue().get(Integer.parseInt(recipe.getApplianceID())-1).getName();
                                appliance.setText(name2);
                                spinner.setVisibility(View.INVISIBLE);
                            }

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
                        madeBy.setText("Made By: " + recipe.getUserCreatorId());
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getTitle());
                }

                else
                {
                    madeBy.setVisibility(View.INVISIBLE);
                    category.setVisibility(View.INVISIBLE);
                    appliance.setVisibility(View.INVISIBLE);
                    recipeName.setText("Recipe Not Found :(");
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recipe 404");
                    prepTime.setVisibility(View.INVISIBLE);
                    cookTime.setVisibility(View.INVISIBLE);
                    totalTime.setVisibility(View.INVISIBLE);
                    instructions.setVisibility(View.INVISIBLE);
                    prepTitle.setVisibility(View.INVISIBLE);
                    cookTitle.setVisibility(View.INVISIBLE);
                    totalTitle.setVisibility(View.INVISIBLE);
                }
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}