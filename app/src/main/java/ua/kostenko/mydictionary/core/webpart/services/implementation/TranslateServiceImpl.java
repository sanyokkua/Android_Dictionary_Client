package ua.kostenko.mydictionary.core.webpart.services.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.core.webpart.enums.Languages;
import ua.kostenko.mydictionary.core.webpart.services.TranslateService;

public class TranslateServiceImpl implements TranslateService {
    private static final String TAG = TranslateServiceImpl.class.getSimpleName();
    private static final String ADDRESS = "http://webdictionary-kostenko.rhcloud.com/api/rest/units/%s/%s/%s";
    private static String result = "";
    @Inject OkHttpClient okHttpClient;

    public TranslateServiceImpl() {
        App.getAppComponent().inject(this);
    }

    @Override
    public String translate(@NonNull Languages from, @NonNull Languages to, @NonNull String unit) {
        Request request = new Request.Builder()
                .url(String.format(ADDRESS, from, to, unit))
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error due async request to the server", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
            }
        });
        return result;
    }
}
