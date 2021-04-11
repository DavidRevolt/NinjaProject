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
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.applianceDao().delete(appliance);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddApplianceListener{
        void onComplete();
    }
    public void addAppliance(final Appliance appliance, final AddApplianceListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.applianceDao().insertAll(appliance);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface UpdateApplianceListener{
        void onComplete();
    }
    public void updateAppliance(final Appliance appliance, final UpdateApplianceListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.applianceDao().updateAppliance(appliance);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }


    public interface GetApplianceRecipesListener{
        void onComplete(List<ApplianceWithRecipes> data);
    }
    public void getAllApplianceRecipes(final Appliance appliance, final GetApplianceRecipesListener listener){
        class MyAsyncTask extends AsyncTask{
            List<ApplianceWithRecipes> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDb.db.applianceDao().getApplianceWithRecipes(appliance.getId());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

}
