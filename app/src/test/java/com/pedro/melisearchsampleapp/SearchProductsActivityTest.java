package com.pedro.melisearchsampleapp;

import androidx.recyclerview.widget.RecyclerView;
import com.pedro.melisearchsampleapp.activity.SearchProductsActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Pruebas unitarias de la actividad principal
 *
 * NOTA: Por problemas de tiempo, no fue posible implementar la mayoría de las pruebas unitarias y de integración
 * que podrían ser relevantes. Sólo se implementaron unas pocas pruebas básicas
 *
 * Entiendo que sería importante implementar pruebas unitarias a los servicios de retrofit, al mencanismo de navegación
 * entre fragmentos, al ViewModel que comparten los fragmentos, etc.
 *
 * Una prueba interesante sería por ejemplo, utilizar un mock de la clase de la API, y verificar que se esté invocando
 * una sola vez cuando el usuario ejecuta una búsqueda.
 */
@RunWith(RobolectricTestRunner.class)
public class SearchProductsActivityTest {
    private SearchProductsActivity activity;
    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(SearchProductsActivity.class)
                .create()
                .resume()
                .get();
    }

    /**
     * Comprueba que la actividad se crea correctamente
     * @throws Exception
     */
    @Test
    public void activityMustNotNull() throws Exception
    {
        assertNotNull( activity );
    }

    /**
     * Comprueba que la actividad contiene un RecyclerView
     * @throws Exception
     */
    @Test
    public void testActivityMustContainRecyclerView() throws Exception
    {
        assertNotNull( activity.findViewById(R.id.item_list) );
        assertNotNull( activity.findViewById(R.id.item_list) instanceof RecyclerView );
    }

    /**
     * Comprueba que el RecyclerView de la actividad está inicialmente vacío
     * @throws Exception
     */
    @Test
    public void testActivityRecyclerViewAdapterMustBeEmptyAtBegining() throws Exception
    {
        assertEquals( 0, ((RecyclerView)activity.findViewById(R.id.item_list)).getAdapter().getItemCount() );
    }
}