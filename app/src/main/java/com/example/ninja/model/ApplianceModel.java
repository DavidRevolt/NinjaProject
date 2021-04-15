package com.example.ninja.model;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ApplianceModel {
    public final static ApplianceModel instance = new ApplianceModel();
    private ApplianceModel(){}

    ApplianceModelFirebase modelFirebase = new ApplianceModelFirebase();
    ApplianceModelSQL modelSql = new ApplianceModelSQL();



    public interface DelApplianceListener{
        void onComplete();
    }
    public void delAppliance(final Appliance appliance, final DelApplianceListener listener){
        modelFirebase.delAppliance(appliance,listener);
    }




    public interface AddApplianceListener{
        void onComplete();
    }
    public void addAppliance(final Appliance appliance, final AddApplianceListener listener){
        modelFirebase.addAppliance(appliance,listener);
    }



    public interface UpdateApplianceListener extends AddApplianceListener{
        void onComplete();
    }
    public void updateAppliance(final Appliance appliance, final UpdateApplianceListener listener){
        modelFirebase.updateAppliance(appliance,listener);
    }





    //GET ALL Appliances
    MutableLiveData<List<Appliance>> appliancesList = new MutableLiveData<List<Appliance>>();
    public interface GetAllAppliancesListener{
        void onComplete(List<Appliance> data);
    }
    public MutableLiveData<List<Appliance>> GetAllAppliances( ){
        return appliancesList;
    }

    //Refresh AppliancesList^^
    public interface refreshGetAllAppliancesListener{
        void onComplete();
    }
    public void refreshGetAllAppliances(refreshGetAllAppliancesListener listener){
        modelFirebase.GetAllAppliances(new GetAllAppliancesListener() {
            @Override
            public void onComplete(List<Appliance> data) {
                appliancesList.setValue(data);
                listener.onComplete();
            }
        });
    }




    //GET ALL Appliance Recipes
    MutableLiveData<List<Recipe>> applianceRecipes = new MutableLiveData<List<Recipe>>();
    public interface GetApplianceRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public MutableLiveData<List<Recipe>> getAllApplianceRecipes(final String id){
        return applianceRecipes;
    }

    //Refresh AplianceRecipes^^
    public interface refreshGetAllApplianceRecipesListener{
        void onComplete();
    }
    public void refreshGetAllUsers(String id , refreshGetAllApplianceRecipesListener listener){
        modelFirebase.getAllApplianceRecipes(id,new GetApplianceRecipesListener() {
            @Override
            public void onComplete(List<Recipe> data) {
                applianceRecipes.setValue(data);
                listener.onComplete();
            }
        });
    }





    //GET APPLIANCE
    MutableLiveData<Appliance> appliance = new MutableLiveData<Appliance>();
    public interface GetApplianceListener{
        void onComplete(Appliance appliance);
    }
    public MutableLiveData<Appliance> getAppliance(String id){
        modelFirebase.getAppliance(id, new GetApplianceListener() {
            @Override
            public void onComplete(Appliance data) {
                appliance.setValue(data);
            }
        });
        return appliance;
    }
}
