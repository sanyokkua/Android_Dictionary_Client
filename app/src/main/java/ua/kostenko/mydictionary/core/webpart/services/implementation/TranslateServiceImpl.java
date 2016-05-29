package ua.kostenko.mydictionary.core.webpart.services.implementation;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.OnResultCallback;
import ua.kostenko.mydictionary.core.webpart.services.RestApi;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

public class TranslateServiceImpl implements TranslateService<Unit> {
    private static final String TAG = TranslateServiceImpl.class.getSimpleName();
    @Inject Retrofit retrofit;

    public TranslateServiceImpl() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void translate(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String text,
                          final OnResultCallback<Unit> onResultCallback) {
        RestApi restApi = retrofit.create(RestApi.class);
        Call<TranslateServiceResponse> call = restApi.getTranslation(from.getLangCode(), to.getLangCode(), text);
        call.enqueue(new Callback<TranslateServiceResponse>() {
            @Override
            public void onResponse(Call<TranslateServiceResponse> call, Response<TranslateServiceResponse> response) {
                TranslateServiceResponse body = response.body();
                Unit unit = new Unit(text, body.getTranslation(), body.getAdditionalTranslations(), 0);
                onResultCallback.onResult(unit);
            }

            @Override
            public void onFailure(Call<TranslateServiceResponse> call, Throwable t) {
                onResultCallback.onResult(new Unit(text, ""));
            }
        });
    }
}
