package com.tlnacl.letschat.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.tlnacl.letschat.ApiAdapter;
import com.tlnacl.letschat.MainActivity;
import com.tlnacl.letschat.di.modules.ApplicationModule;
import com.tlnacl.letschat.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tom.t on 26/10/16.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    Context context();
    SharedPreferences preferences();

    ApiAdapter apiAdapter();
}
