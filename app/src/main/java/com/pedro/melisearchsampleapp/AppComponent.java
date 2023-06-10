package com.pedro.melisearchsampleapp;

import com.pedro.melisearchsampleapp.modules.ApiModule;
import com.pedro.melisearchsampleapp.modules.AppModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidInjectionModule.class, AppModule.class, ApiModule.class})
public interface AppComponent extends AndroidInjector<MeliSearchSampleApplication> {
}