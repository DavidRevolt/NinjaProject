package com.example.ninja.ui.create;

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

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        View root =  inflater.inflate(R.layout.fragment_create, container, false);

        Log.d("TAG","in CreateFragment");

        return root;
    }



}