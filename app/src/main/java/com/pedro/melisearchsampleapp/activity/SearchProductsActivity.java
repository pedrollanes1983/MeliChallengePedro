package com.pedro.melisearchsampleapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.databinding.SearchProductsActivityBinding;
import com.pedro.melisearchsampleapp.navigation.NavigationManager;
import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

/**
 * Actividad principal de la aplicación. En ella se muestran los fragmentos del
 * buscador y de los detalles de un producto.
 */
public class SearchProductsActivity extends AppCompatActivity {

    /**
     * View model que contiene la lista de productos de la búsqueda. Además, se encarga de gestionar las
     * búsquedas.
     * Este ViewModel lo comparten los dos fragmentos de la app, aunque el owner es la actividad
     */
    private SearchResultsViewModel mViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);

        // Obtener y asociar el ViewModel a la actividad
        mViewModel = viewModelProviderFactory.provideSearchResultsViewModel(this);

        // Crear los bindings
        SearchProductsActivityBinding binding = SearchProductsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la navegación
        navigationManager.setupAppNavigation(this);

        // Solicitar permisos al usuario
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_product_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}