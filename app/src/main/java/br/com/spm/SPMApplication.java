package br.com.spm;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.com.spm.di.component.AppComponent;
import br.com.spm.di.component.DaggerAppComponent;
import br.com.spm.di.module.AppModule;
import br.com.spm.model.SPMDataBase;

public class SPMApplication extends Application {

    private static SPMApplication instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeInjector();
        getDataBase();
    }

    public static SPMApplication getInstance() {
        return instance;
    }

    public void initializeInjector() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public SPMDataBase getDataBase() {
        return SPMDataBase.getInstance(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
