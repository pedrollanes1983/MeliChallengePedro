package com.pedro.melisearchsampleapp.modules;

import com.pedro.melisearchsampleapp.activity.SearchProductsActivity;
import com.pedro.melisearchsampleapp.fragments.ProductDetailFragment;
import com.pedro.melisearchsampleapp.fragments.ProductListFragment;
import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface AppModule {
    @ContributesAndroidInjector
    abstract ProductListFragment contributeProductListFragmentInjector();

    @ContributesAndroidInjector
    abstract ProductDetailFragment contributeProductDetailFragmentInjector();

    @ContributesAndroidInjector
    abstract SearchProductsActivity contributeSearchProductsActivityInjector();

    @ContributesAndroidInjector
    abstract SearchResultsViewModel contributeSearchResultsViewModelInjector();

}
