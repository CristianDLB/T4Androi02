package com.example.t4androi02.Factor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFactor {

    public static Retrofit Links(){
        return new Retrofit.Builder()
                .baseUrl("https://628f6c570e69410599dc230c.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
