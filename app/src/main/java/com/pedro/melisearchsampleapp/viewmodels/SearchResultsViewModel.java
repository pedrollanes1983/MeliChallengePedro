package com.pedro.melisearchsampleapp.viewmodels;

import androidx.lifecycle.ViewModel;
import com.pedro.melisearchsampleapp.api.MeliSearchApi;
import com.pedro.melisearchsampleapp.api.ApiCallback;
import com.pedro.melisearchsampleapp.model.Product;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchResultsViewModel extends ViewModel {
    @Inject
    Retrofit retrofit;

    @Inject
    public SearchResultsViewModel() {
    }

    private String searchValue;
    private ProductSearchResultList searchResultList;

    public ProductSearchResultList getSearchResultList() {
        return searchResultList;
    }

    public Product getById(String id) {
        return searchResultList.getById(id);
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void searchProducts(String value, ApiCallback searchCallback) {
        searchValue = value;
        MeliSearchApi service = retrofit.create(MeliSearchApi.class);
        Call<ProductSearchResultList> call = service.searchItems(searchValue);
        call.enqueue(new Callback<ProductSearchResultList>() {
            @Override
            public void onResponse(Call<ProductSearchResultList> call, Response<ProductSearchResultList> response) {
                searchResultList = response.body();
                searchCallback.onResponse();
            }

            @Override
            public void onFailure(Call<ProductSearchResultList> call, Throwable t) {
                searchCallback.onFailure(t);
            }
        });
    }

}