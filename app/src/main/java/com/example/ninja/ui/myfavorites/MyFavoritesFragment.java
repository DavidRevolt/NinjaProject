package com.example.ninja.ui.myfavorites;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ninja.R;

public class MyFavoritesFragment extends Fragment {

    private MyFavoritesViewModel myFavoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myFavoritesViewModel = new ViewModelProvider(this).get(MyFavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_favorites, container, false);

        Log.d("TAG","in MyFavoritesFragment");

        return root;
    }


}