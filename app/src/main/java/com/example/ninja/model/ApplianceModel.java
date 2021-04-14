package com.example.ninja.model;

import android.os.AsyncTask;

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


    public interface GetAllAppliances{
        void onComplete(List<Appliance> data);
    }
    public void GetAllAppliances(final GetAllAppliances listener){
        modelFirebase.GetAllAppliances(listener);
    }


    public interface GetApplianceRecipesListener{
        void onComplete(List<Recipe> data);
    }
    public void getAllApplianceRecipes(final String id, final GetApplianceRecipesListener listener){
        modelFirebase.getAllApplianceRecipes(id,listener);
    }

    public interface GetApplianceListener{
        void onComplete(Appliance appliance);
    }
    public void getAppliance(String id, GetApplianceListener listener){
        modelFirebase.getAppliance( id,  listener);
    }
}
