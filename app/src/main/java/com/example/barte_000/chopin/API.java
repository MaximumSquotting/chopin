package com.example.barte_000.chopin;

import android.app.usage.UsageEvents;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by barte_000 on 19.11.2016.
 */

public class API {

    private static APIInterface apiInterface;
    private static String url = "frelia.org:3000/api/v1/offer";

    public static APIInterface getClient() {
        if (apiInterface == null) {
            Interceptor acceptJSON = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors().add(acceptJSON);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

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
        @GET("/Offers")
        Call<List<Offer>> getAllOffers();

        @GET("/offers/{offer_id}")
        Call<Offer> getOffer(@Path("offer_id") Integer offer_id);
    }
}

