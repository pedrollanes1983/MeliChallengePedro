package com.pedro.melisearchsampleapp;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.pedro.melisearchsampleapp.fragments.ProductListFragment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Pruebas unitarias del fragmento del listado de productos
 *
 *  NOTA: Por problemas de tiempo, no fue posible implementar la mayoría de las pruebas unitarias y de integración
 *  que podrían ser relevantes. Sólo se implementaron unas pocas pruebas básicas.
 *
 *  Entiendo que sería importante implementar pruebas unitarias a los servicios de retrofit, al mencanismo de navegación
 *  entre fragmentos, al ViewModel que comparten los fragmentos, etc.
 *
 *  Una prueba interesante sería por ejemplo, utilizar un mock de la clase de la API, y verificar que se esté invocando
 *  una sola vez cuando el usuario ejecuta una búsqueda.
 */
@RunWith(AndroidJUnit4.class)
public class ProductListFragmentTest {
    @IdRes
    private final int theme = R.style.Theme_MeliSearchSampleApp;
    @InjectMocks
    private  FragmentScenario<ProductListFragment> scenario;

    /**
     * Comprueba que la actividad contiene un RecyclerView
     * @throws Exception
     */
    @Test
    public void testFragmentMusCOntainRecyclerView() throws Exception
    {
        Bundle args = new Bundle();
        scenario = FragmentScenario.launchInContainer(ProductListFragment.class, args, theme, Lifecycle.State.STARTED);

        scenario.onFragment(fragment -> {
            assertNotNull( fragment.requireView().findViewById(R.id.item_list) );
            assertNotNull( fragment.requireView().findViewById(R.id.item_list) instanceof RecyclerView);
        });
    }

    /**
     * Comprueba que la actividad se crea correctamente
     * @throws Exception
     */
    @Test
    public void testSearchViewMustNotBeNull() {

        Bundle args = new Bundle();
        scenario = FragmentScenario.launchInContainer(ProductListFragment.class, args, theme, Lifecycle.State.STARTED);

        scenario.onFragment(fragment -> {
            assertNotNull(fragment.requireView().findViewById(R.id.item_search));
        });
    }

    @Test
    public void testSearchViewQueryDisplaysTextInControl() {

        Bundle args = new Bundle();
        scenario = FragmentScenario.launchInContainer(ProductListFragment.class, args, theme, Lifecycle.State.STARTED);
        scenario.onFragment(fragment -> {
            SearchView sv = fragment.requireView().findViewById(R.id.item_search);
            sv.setQuery("Macbook", true);
            assertEquals(sv.getQuery().toString(), "Macbook");
        });
    }

    @Test
    public void testSearchViewIsVisibleInLayout() {

        Bundle args = new Bundle();
        scenario = FragmentScenario.launchInContainer(ProductListFragment.class, args, theme, Lifecycle.State.STARTED);
        scenario.onFragment(fragment -> {
            SearchView sv = fragment.requireView().findViewById(R.id.item_search);
            assertEquals(sv.getVisibility(), View.VISIBLE);
        });
    }
}