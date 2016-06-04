package ua.kostenko.mydictionary.core.webpart.services.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.OnResultCallback;
import ua.kostenko.mydictionary.core.webpart.services.RestApi;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

import static ua.kostenko.mydictionary.core.local.database.dao.implementation.DaoUtils.createUnit;

public class TranslateServiceImpl implements TranslateService<Unit> {
    private static final String TAG = TranslateServiceImpl.class.getSimpleName();
    @Inject Retrofit retrofit;

    public TranslateServiceImpl() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void translate(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String text,
                          @NonNull final OnResultCallback<Unit> onResultCallback) {
        RestApi restApi = retrofit.create(RestApi.class);
        Call<TranslateServiceResponse> call = restApi.getTranslation(from.getLangCode(), to.getLangCode(), text);
        call.enqueue(new OnResponseCallback(onResultCallback, text));
    }

    @Override
    public Unit translateSync(@NonNull Languages from, @NonNull Languages to, @NonNull String text) {
        RestApi restApi = retrofit.create(RestApi.class);
        Call<TranslateServiceResponse> call = restApi.getTranslation(from.getLangCode(), to.getLangCode(), text);
        Unit result;
        try {
            Response<TranslateServiceResponse> execute = call.execute();
            TranslateServiceResponse body = execute.body();
            result = createUnit(body, text);
        } catch (IOException e) {
            result = new Unit(text, "");
            Log.w(TAG, String.format("Error occur in Request to the server. Word: %s", text), e);
        }
        return result;
    }
}
