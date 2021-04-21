package com.example.ninja.ui.browse;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ninja.R;
import com.example.ninja.model.Recipe;
import java.util.List;


public class BrowseRecipeListAdapter extends RecyclerView.Adapter<BrowseRecipeListViewHolder>{


    //Members
    public LiveData<List<Recipe>> data;
    LayoutInflater inflater;
    private BrowseRecipeListAdapter.OnItemClickListener listener;


    //Constructor
    public BrowseRecipeListAdapter(LayoutInflater inflater){
        this.inflater = inflater;

    }

    //Click
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(BrowseRecipeListAdapter.OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public BrowseRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.browse_recipe_list_row,null);
        BrowseRecipeListViewHolder holder = new BrowseRecipeListViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseRecipeListViewHolder holder, int position) {
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
