package ua.kostenko.mydictionary.core.webpart.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ua.kostenko.mydictionary.core.webpart.services.implementation.TranslateServiceResponse;

public interface RestApi {
    @GET("api/rest/units/{from}/{to}/{text}")
    Call<TranslateServiceResponse> getTranslation(@Path("from") String from, @Path("to") String to, @Path("text") String text);
}
