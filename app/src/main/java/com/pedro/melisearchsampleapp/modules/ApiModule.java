package com.pedro.melisearchsampleapp.modules;

import com.pedro.melisearchsampleapp.utils.Constants;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class ApiModule {
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
