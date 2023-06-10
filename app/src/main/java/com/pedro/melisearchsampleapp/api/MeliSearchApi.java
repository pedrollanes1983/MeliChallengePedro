package com.pedro.melisearchsampleapp.api;

import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Definición de los endpoint que se van a consumir en la app
 */
public interface MeliSearchApi {

    /**
     * Método para realizar una búsqueda de productos
     * @param searchText Cadena de caracteres que se va a utilizar como texto de búsqueda
     * NOTA: se debería implementar un paginado, y solicitar el listado con un límite
     * @return Listado de productos encontrados para la clave de búsqueda
     */
    @GET("sites/MLA/search")
    Call<ProductSearchResultList> searchItems(@Query("q") String searchText);
}
