package com.example.ninja.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ninja.model.Recipe;

import java.util.List;

public class HomeViewModel extends ViewModel {

    //Data
    private MutableLiveData<String> mText;
    private List<Recipe> recipeList;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}