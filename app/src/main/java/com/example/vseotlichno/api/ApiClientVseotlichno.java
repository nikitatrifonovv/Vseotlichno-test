package com.example.vseotlichno.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientVseotlichno {

    private static ApiClientVseotlichno mInstance;

    public static ApiClientVseotlichno getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClientVseotlichno();
        }
        return mInstance;
    }

    private static final String BASE_URL = "https://vseotlichno.com/api/v1/";

    private final Retrofit mRetrofit;

    public ApiClientVseotlichno() {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public VseotlichnoAPI getJSONApi() {
        return mRetrofit.create(VseotlichnoAPI.class);
    }
}
