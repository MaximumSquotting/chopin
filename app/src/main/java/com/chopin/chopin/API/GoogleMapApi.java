package com.chopin.chopin.API;

import com.chopin.chopin.models.Offer;
import com.chopin.chopin.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class GoogleMapApi {

    public static String uid = "";
    public static String token = "";
    public static String client = "";
    private static APIInterface apiInterface;

    public static APIInterface getClient() {
        if (apiInterface == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("Accept", "application/json").build();
                                    return chain.proceed(request);
                                }
                            }).build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            String url = "http://maps.googleapis.com/";
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            apiInterface = client.create(APIInterface.class);
        }
        return apiInterface;
    }

    public interface APIInterface {

        // Offers
        @GET("maps/api/geocode/json?address={address}")
        Call<List<String>> getAddress(@Path("address") String address);

    }
}

