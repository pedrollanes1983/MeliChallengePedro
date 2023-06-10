package com.pedro.melisearchsampleapp;

import android.app.AlertDialog;
import android.app.Application;
import android.view.ContextThemeWrapper;
import com.pedro.melisearchsampleapp.fragments.ProductListFragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MeliSearchSampleApplication extends Application implements HasAndroidInjector {
    private static final Logger logger = LoggerFactory.getLogger(MeliSearchSampleApplication.class);
    @Inject DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    private static MeliSearchSampleApplication instance;

    public static MeliSearchSampleApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create().inject(this);
        instance = this;

        /**
         * Procesamiento global de excepciones en la app. Quedará registro en el log del error inesperado.
         * Se podría implementar mecanismo de reportar error, por ejemplo con Crashlytics de Firebase
         */
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Catch your exception
                logger.error("Error inesperado en la app", paramThrowable);
                // Without System.exit() this will not work.
                System.exit(2);
            }
        });
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
