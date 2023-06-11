package com.pedro.melisearchsampleapp.modules;

import com.pedro.melisearchsampleapp.utils.Constants;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * Dagger Module para configurar la inyección de las clases de la API.
 */
@Module
public class ApiModule {
    /**
     * Provee la instancia de Retrofit para las llamadas a los end-points
     * @return Instancia de retrofit ya configurada con la URL base.
     */
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
