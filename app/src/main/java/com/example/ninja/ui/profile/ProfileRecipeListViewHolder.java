package com.example.ninja.ui.profile;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import com.squareup.picasso.Picasso;

public class ProfileRecipeListViewHolder extends RecyclerView.ViewHolder {

    public ProfileRecipeListAdapter.OnItemClickListener listener;
    TextView recipeName;
    TextView prepTime;
    TextView cookTime;
    TextView totalTime;
    ImageView recipeImg;
    int position;


    public ProfileRecipeListViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.Profile_RecipeList_Title);
        prepTime = itemView.findViewById(R.id.Profile_RecipeList_Prep);
        cookTime = itemView.findViewById(R.id.Profile_RecipeList_Cook);
        totalTime = itemView.findViewById(R.id.Profile_RecipeList_Total);
        recipeImg = itemView.findViewById(R.id.Profile_RecipeList_Img);
        recipeImg.setClipToOutline(true);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Recipe recipe, int position) {
        recipeName.setText(recipe.getTitle());
        prepTime.setText(String.valueOf(recipe.getPrepTime()));
        prepTime.setText(String.valueOf(recipe.getPrepTime()));
        cookTime.setText(String.valueOf(recipe.getCookTime()));
        totalTime.setText(String.valueOf(recipe.getCookTime() + recipe.getPrepTime()));

        recipeImg.setImageResource(R.drawable.plate);
        if (recipe.getImgURL() != null)
            Picasso.get().load(recipe.getImgURL()).placeholder(R.drawable.plate).centerCrop().fit().into(recipeImg);


        this.position = position;
    }
}
