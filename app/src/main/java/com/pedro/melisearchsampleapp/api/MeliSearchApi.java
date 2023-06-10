package com.pedro.melisearchsampleapp.api;

import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeliSearchApi {

    @GET("sites/MLA/search")
    Call<ProductSearchResultList> searchItems(@Query("q") String searchText);
}
