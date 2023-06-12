package com.pedro.melisearchsampleapp.navigation;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.pedro.melisearchsampleapp.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementa el mecanismo de navegar a los diferentes fragmentos de la app
 */
@Singleton
public class NavigationManager {
    @Inject
    public NavigationManager() {
    }

    /**
     * Permite configurar el macanismo de navegación de la app. Debe invocarse desde la actividad que contendrá
     * los fragmentos
     * @param activity Actividad principal de la app
     */
    public void setupAppNavigation(AppCompatActivity activity) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_product_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
    }

    /**
     * Permite navegar al fragmento indicado.
     * Los posibles fragmentos están representados en el enum EnumNavigationFragment
     * @param navigationFragment Enum que representa al fragmento al que se navega
     * @param view Vista desde la que se está navegando
     * @param arguments Argumentos para pasar la nuevo fragmento
     *
     * @see EnumNavigationFragment
     */
    public void navigateToFragment(EnumNavigationFragment navigationFragment, View view, Bundle arguments) {
        switch (navigationFragment) {
            case PRODUCT_DETAILS:
                Navigation.findNavController(view).navigate(R.id.show_item_detail, arguments);
                break;
        }
    }
}
