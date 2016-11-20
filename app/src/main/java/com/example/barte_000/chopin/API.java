package com.example.barte_000.chopin;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by barte_000 on 19.11.2016.
 */



public class API {

    private static APIInterface apiInterface;
    private static String url = "http://frelia.org:3001/api/v1/offers/";
    public static String uid = "";
    public static String token = "";
    public static String client = "";

    public static APIInterface getClient() {
        if (apiInterface == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("Accept", "Application/JSON")
                                            .addHeader("uid", uid)
                                            .addHeader("client", client)
                                            .addHeader("access-token",token).build();
                                    return chain.proceed(request);
                                }
                            }).build();

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
        @GET("/api/v1/offers/")
        Call<List<Offer>> getAllOffers();

        @GET("/offers/{offer_id}")
        Call<Offer> getOffer(@Path("offer_id") Integer offer_id);

        @POST("/api/v1/offers/")
        Call<Offer> sendOffer(@Body Offer offer);

        @FormUrlEncoded
        @POST("/api/v1/auth/sign_in/")
        Call <User> getToken(@Field("email") String email, @Field("password") String password);


    }
}

