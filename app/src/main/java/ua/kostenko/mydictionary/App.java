package ua.kostenko.mydictionary;

import android.app.Application;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import ua.kostenko.mydictionary.dependencies.DaggerAppComponent;
import ua.kostenko.mydictionary.dependencies.DaggerAppModule;
import ua.kostenko.mydictionary.dependencies.DaggerDaggerAppComponent;
import ua.kostenko.mydictionary.dependencies.DaggerDaoModule;
import ua.kostenko.mydictionary.dependencies.DaggerServicesModule;
import ua.kostenko.mydictionary.dependencies.DaggerUtilsModule;

public class App extends Application {
    public static DaggerAppComponent appComponent;

    public static DaggerAppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        appComponent = DaggerDaggerAppComponent.builder()
                .daggerAppModule(new DaggerAppModule(context))
                .daggerDaoModule(new DaggerDaoModule())
                .daggerUtilsModule(new DaggerUtilsModule())
                .daggerServicesModule(new DaggerServicesModule())
                .build();
    }

    @Override
    public void onTerminate() {
        OpenHelperManager.releaseHelper();
        super.onTerminate();
    }
}
