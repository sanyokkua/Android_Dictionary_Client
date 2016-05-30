package ua.kostenko.mydictionary.dependencies;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
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
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://webdictionary-kostenko.rhcloud.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public TranslateService<Unit> provideTranslateService() {
        return new TranslateServiceImpl();
    }
}
