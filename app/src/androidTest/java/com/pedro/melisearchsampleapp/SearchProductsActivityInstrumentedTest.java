package com.pedro.melisearchsampleapp;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Pruebas instrumentadas de la actividad principal
 *
 * NOTA: Por problemas de tiempo, no fue posible implementar pruebas instrumentadas
 * relevantes.
 */
@RunWith(AndroidJUnit4.class)
public class SearchProductsActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.pedro.melisearchsampleapp", appContext.getPackageName());
    }
}