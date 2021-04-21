package com.example.ninja.ui.profile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import java.util.List;

public class ProfileRecipeListAdapter extends RecyclerView.Adapter<ProfileRecipeListViewHolder> {

    //Members
    public LiveData<List<Recipe>> data;
    LayoutInflater inflater;
    private ProfileRecipeListAdapter.OnItemClickListener listener;

    //Constructor
    public ProfileRecipeListAdapter(LayoutInflater inflater){
        this.inflater = inflater;

    }

    //Click
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(ProfileRecipeListAdapter.OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProfileRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.profile_recipe_list_row,null);
        ProfileRecipeListViewHolder holder = new ProfileRecipeListViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecipeListViewHolder holder, int position) {
        Recipe recipe = data.getValue().get(position);
        holder.bindData(recipe,position);
    }

    @Override
    public int getItemCount() {
        if (data.getValue() == null){
            return 0;
        }
        return data.getValue().size();
    }



}
