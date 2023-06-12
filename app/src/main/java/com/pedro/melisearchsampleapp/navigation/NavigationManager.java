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

    public void setupAppNavigation(AppCompatActivity activity) {
        NavHostFragment navHostFragment = (NavHostFragment) activity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_product_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
    }

    public void navigateToFragment(EnumNavigationFragment navigationFragment, View view, Bundle arguments) {
        switch (navigationFragment) {
            case PRODUCT_DETAILS:
                Navigation.findNavController(view).navigate(R.id.show_item_detail, arguments);
                break;
        }
    }
}
