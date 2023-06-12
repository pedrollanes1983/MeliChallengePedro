package com.pedro.melisearchsampleapp.modules;

import com.pedro.melisearchsampleapp.adapter.ProductsRecyclerViewAdapter;
import com.pedro.melisearchsampleapp.navigation.NavigationManager;
import com.pedro.melisearchsampleapp.activity.SearchProductsActivity;
import com.pedro.melisearchsampleapp.fragments.ProductDetailFragment;
import com.pedro.melisearchsampleapp.fragments.ProductListFragment;
import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.viewmodels.ViewModelProviderFactory;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dagger Module para configurar la inyección de las clases generales en la app
 */
@Module
public interface AppModule {
    @ContributesAndroidInjector
    ProductListFragment contributeProductListFragmentInjector();

    @ContributesAndroidInjector
    ProductDetailFragment contributeProductDetailFragmentInjector();

    @ContributesAndroidInjector
    SearchProductsActivity contributeSearchProductsActivityInjector();

    @ContributesAndroidInjector
    SearchResultsViewModel contributeSearchResultsViewModelInjector();

    @ContributesAndroidInjector
    ProductsRecyclerViewAdapter contributeProductsRecyclerViewAdapterInjector();

    @ContributesAndroidInjector
    ViewModelProviderFactory contributeViewModelProviderFactoryInjector();

    @ContributesAndroidInjector
    NavigationManager contributeNavigationManagerInjector();

}
