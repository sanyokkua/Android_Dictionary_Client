package ua.kostenko.mydictionary.dependencies;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;
import ua.kostenko.mydictionary.core.webpart.services.implementation.TranslateServiceImpl;

@Module
public class DaggerServicesModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public TranslateService provideTranslateService() {
        return new TranslateServiceImpl();
    }
}
