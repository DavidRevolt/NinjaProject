package com.example.ninja.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ninja.MyApplication;

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
        modelSql.delAppliance(appliance, new ApplianceModelSQL.DelApplianceListener() {
            @Override
            public void onComplete() {
                refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });

            }
        });
    }




    public interface AddApplianceListener{
        void onComplete();
    }
    public void addAppliance(final Appliance appliance, final AddApplianceListener listener){
        modelFirebase.addAppliance(appliance, new ApplianceModel.AddApplianceListener() {
            @Override
            public void onComplete() {
                refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener(){
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }



    public interface UpdateApplianceListener extends AddApplianceListener{
        void onComplete();
    }
    public void updateAppliance(final Appliance appliance, final UpdateApplianceListener listener){
        modelFirebase.updateAppliance(appliance, new ApplianceModel.UpdateApplianceListener() {
            @Override
            public void onComplete() {
                refreshGetAllAppliances(new ApplianceModel.RefreshGetAllAppliancesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }





    //GET ALL Appliance
    LiveData<List<Appliance>> AppliancesList;
    public interface GetAllAppliancesListener {
        void onComplete(List<Appliance> data);
    }
    public LiveData<List<Appliance>> GetAllAppliances(){
        if (AppliancesList == null){
            AppliancesList = modelSql.getAllAppliances();
            refreshGetAllAppliances(null);
        }
        return AppliancesList;
    }


    //Refresh AppliancesList^^
    public interface RefreshGetAllAppliancesListener{
        void onComplete();
    }


    public void refreshGetAllAppliances(RefreshGetAllAppliancesListener listener){
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpAppList",0);

        modelFirebase.getAllAppliances(lastUpdated, new GetAllAppliancesListener() {
            @Override
            public void onComplete(List<Appliance> data) {
                long lastUp = 0;
                for (Appliance rec: data) {
                    modelSql.addAppliance(rec,null);
                    if (rec.getLastUpdated()>lastUp){
                        lastUp = rec.getLastUpdated();
                    }
                }
                sp.edit().putLong("lastUpAppList", lastUp).commit();
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }






    //GET APPLIANCE
    LiveData<Appliance> appliance;
    public interface GetApplianceListener{
        void onComplete(Appliance appliance);
    }
    public LiveData<Appliance> getAppliance(String id){
        appliance = modelSql.getAppliance(id);
        refreshGetAppliance(id, null);
        return appliance;
    }

    //Refresh Category^^
    public interface RefreshGetApplianceListener{
        void onComplete();
    }
    public void refreshGetAppliance(String id, RefreshGetApplianceListener listener){
        modelFirebase.getAppliance(id, new GetApplianceListener() {
            @Override
            public void onComplete(Appliance data) {
                modelSql.addAppliance(data,null);
                if(listener != null) {
                    listener.onComplete();
                }
            }
        });
    }
}
