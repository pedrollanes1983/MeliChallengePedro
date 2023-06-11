package com.pedro.melisearchsampleapp.viewmodels;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory que va a proveer cada uno de los ViewModel que se utilicen en la app.
 * Permitiría que para las pruebas unitarias se pueda injectar un mock del factory e injectar mocks de los ViewModel.
 */
@Singleton
public class ViewModelProviderFactory {
    @Inject
    public ViewModelProviderFactory() {
    }

    /**
     * Obtiene el SearchResultsViewModel asociado al owner indicado (lo obtiene de la caché o lo crea de ser necesario)
     * @param owner El owner del ViewModel
     * @return La instancia del ViewModel para dicho owner
     */
    public SearchResultsViewModel provideSearchResultsViewModel(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(SearchResultsViewModel.class);
    }
}
