package com.tlnacl.letschat;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.tlnacl.letschat.di.components.AppComponent;
import com.tlnacl.letschat.di.components.DaggerAppComponent;
import com.tlnacl.letschat.di.modules.ApplicationModule;

/**
 * Created by tlnacl on 14/03/17.
 */

public class AndroidApplication  extends Application {
    private static AppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        Stetho.initializeWithDefaults(this);
    }

    private void initializeInjector() {
        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
