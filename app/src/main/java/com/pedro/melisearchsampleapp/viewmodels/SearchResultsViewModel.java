package com.pedro.melisearchsampleapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.api.ApiCallback;
import com.pedro.melisearchsampleapp.api.MeliSearchApi;
import com.pedro.melisearchsampleapp.model.Product;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;
import java.util.Objects;

/**
 * View model que contiene la lista de productos de la búsqueda. Además se encarga de gestionar las
 * búsquedas consultando al servidor a través de los métodos de la API.
 * Este ViewModel lo comparten los dos fragmentos de la app, de esta forma se mantiene el estado de las
 * pantallas cuando hay cambios de configuración, como en la rotación de pantalla
 */
public class SearchResultsViewModel extends ViewModel {
    @Inject
    Retrofit retrofit;

    @Inject
    public SearchResultsViewModel() {
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);
        searchResultList = new MutableLiveData<>();
    }

    /**
     * Frase de búsqueda para traer los productos del servidor
     */
    private String searchValue;
    /**
     * Resultado de la búsqueda
     */
    private final MutableLiveData<ProductSearchResultList> searchResultList;

    public MutableLiveData<ProductSearchResultList> getSearchResultList() {
        return searchResultList;
    }

    /**
     * Permite obtener un producto del listado dado su ID
     * @param id Identificador del producto
     * @return El producto encontrado, o NULL si no se encuentra ninguno
     */
    public Product getById(String id) {
        if (searchResultList != null)
            return Objects.requireNonNull(searchResultList.getValue()).getById(id);
        else
            return null;
    }

    public String getSearchValue() {
        return searchValue;
    }

    /**
     * Realiza una búsqueda de productos en el servidor para la palabra de búsqueda recibida
     * @param value Valor por el que se buscarán los productos
     * @param searchCallback Callback con los métodos que serán invodados para reportar el resultado del request
     */
    public void searchProducts(String value, ApiCallback searchCallback) {
        searchValue = value;
        MeliSearchApi service = retrofit.create(MeliSearchApi.class);
        Call<ProductSearchResultList> call = service.searchItems(searchValue);
        call.enqueue(new Callback<ProductSearchResultList>() {
            /**
             * El request terminó correctamente y se obtuvo una respuesta válida
             * @param call Instancia de la llamada
             * @param response Respuesta recibida
             */
            @Override
            public void onResponse(@NotNull Call<ProductSearchResultList> call, @NotNull Response<ProductSearchResultList> response) {
                searchResultList.setValue(response.body());
                searchCallback.onResponse();
            }

            /**
             * El request falló con algún error
             * @param call Instancia de la llamada
             * @param t Error reportado
             */
            @Override
            public void onFailure(@NotNull Call<ProductSearchResultList> call, @NotNull Throwable t) {
                searchCallback.onFailure(t);
            }
        });
    }

}
