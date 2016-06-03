package ua.kostenko.mydictionary.core.webpart.services.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.List;

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
                Unit unit = createUnit(body, text);
                onResultCallback.onResult(unit);
            }

            @Override
            public void onFailure(Call<TranslateServiceResponse> call, Throwable t) {
                onResultCallback.onResult(new Unit(text, ""));
            }
        });
    }

    @NonNull
    private Unit createUnit(TranslateServiceResponse body, @NonNull String text) {
        return new Unit(text, body.getTranslation(), body.getAdditionalTranslations(), "", 0, getTechnologies(body));
    }

    private String getTechnologies(TranslateServiceResponse body) {
        StringBuilder builder = new StringBuilder();
        List<String> technologies = body.getTechnologies();
        for (String technology : technologies) {
            builder.append(technology);
            builder.append(";\n\n");
        }
        return builder.toString();
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
