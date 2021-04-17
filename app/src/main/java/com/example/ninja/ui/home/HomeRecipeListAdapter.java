package com.example.ninja.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ninja.R;
import com.example.ninja.model.Recipe;

import java.util.List;

public class HomeRecipeListAdapter extends RecyclerView.Adapter<HomeRecipeListViewHolder>{

    //Members
    public LiveData<List<Recipe>> data;
    LayoutInflater inflater;
    private OnItemClickListener listener;


    //Constructor
    public HomeRecipeListAdapter(LayoutInflater inflater){
        this.inflater = inflater;

    }

    //Click
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public HomeRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_recipe_list_row,null);
        HomeRecipeListViewHolder holder = new HomeRecipeListViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecipeListViewHolder holder, int position) {
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