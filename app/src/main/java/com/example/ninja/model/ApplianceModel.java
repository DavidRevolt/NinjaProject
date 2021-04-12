package com.example.ninja.model;

import android.os.AsyncTask;

import java.util.List;

public class ApplianceModel {
    public final static ApplianceModel instance = new ApplianceModel();
    private ApplianceModel(){}



    public interface DelApplianceListener{
        void onComplete();
    }
    public void delAppliance(final Appliance appliance, final DelApplianceListener listener){
        ApplianceModelFirebase.delAppliance(appliance,listener);
    }


    public interface AddApplianceListener{
        void onComplete();
    }
    public void addAppliance(final Appliance appliance, final AddApplianceListener listener){
        ApplianceModelFirebase.addAppliance(appliance,listener);
    }


    public interface UpdateApplianceListener{
        void onComplete();
    }
    public void updateAppliance(final Appliance appliance, final UpdateApplianceListener listener){
        ApplianceModelFirebase.updateAppliance(appliance,listener);
    }


    public interface GetAllAppliances{
        void onComplete(List<Appliance> data);
    }
    public void GetAllAppliances(final GetAllAppliances listener){
        ApplianceModelFirebase.GetAllAppliances(listener);
    }


    public interface GetApplianceRecipesListener{
        void onComplete(List<ApplianceWithRecipes> data);
    }
    public void getAllApplianceRecipes(final String id, final GetApplianceRecipesListener listener){
        ApplianceModelFirebase.getAllApplianceRecipes(id,listener);
    }
}
