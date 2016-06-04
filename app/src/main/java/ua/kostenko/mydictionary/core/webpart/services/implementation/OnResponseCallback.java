package ua.kostenko.mydictionary.core.webpart.services.implementation;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.services.OnResultCallback;

import static ua.kostenko.mydictionary.core.commonutils.Utils.checkNotNull;
import static ua.kostenko.mydictionary.core.local.database.dao.implementation.DaoUtils.createUnit;

public class OnResponseCallback implements Callback<TranslateServiceResponse> {
    private OnResultCallback<Unit> onResultCallback;
    private String originalText;

    public OnResponseCallback(@NonNull final OnResultCallback<Unit> onResultCallback, @NonNull final String originalText) {
        checkNotNull(onResultCallback);
        checkNotNull(originalText);
        this.onResultCallback = onResultCallback;
        this.originalText = originalText;
    }

    @Override
    public void onResponse(Call<TranslateServiceResponse> call, Response<TranslateServiceResponse> response) {
        TranslateServiceResponse body = response.body();
        Unit unit = createUnit(body, originalText);
        onResultCallback.onResult(unit);
    }

    @Override
    public void onFailure(Call<TranslateServiceResponse> call, Throwable t) {
        onResultCallback.onResult(new Unit(originalText, ""));
    }
}
