package com.pedro.melisearchsampleapp.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.adapter.ProductsRecyclerViewAdapter;
import com.pedro.melisearchsampleapp.api.ApiCallback;
import com.pedro.melisearchsampleapp.databinding.FragmentProductListBinding;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.viewmodels.ViewModelProviderFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Fragmento del Buscador. Permite introducir la clave de búsqueda e invocar al
 * end-point a través del ViewModel para obtener el listado de productos
 */
public class ProductListFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(ProductListFragment.class);

    /**
     * Diálogo de progreso que se muestra durante las operaciones asincrónicas
     */
    ProgressDialog mProgressDialog;

    /**
     * Componente de búsqueda
     */
    private SearchView mSearchView;

    /**
     * RecyvlerView para mostrar el listado de productos
     */
    private RecyclerView mRecyclerView;

    /**
     * Adapter asociado al RecyclerView que muestra la lista de productos encontrados
     */
    ProductsRecyclerViewAdapter mProductsRecyclerViewAdapter;

    /**
     * TextView que indica que no hay elementos para mostrar
     */
    private TextView mEmptyView;

    private FragmentProductListBinding mBinding;

    /**
     * ViewModel asociado a la actividad principal y que comparten los fragmentos de la app.
     * Contiene el listado de productos recuperado del servidor y la clave de búsqueda.
     */
    private SearchResultsViewModel mViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);

        // Se obtiene el ViewModel de la Activity
        mViewModel = viewModelProviderFactory.provideSearchResultsViewModel(getActivity());

        logger.info("Fragment view created");

        mBinding = FragmentProductListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = mBinding.itemList;
        mSearchView = mBinding.itemSearch;
        mEmptyView = mBinding.emptyView;

        // Configura el RecyclerView (aspectos visuales como separadores, se configura el adapter, etc.)
        setupRecyclerView();

        // Configura el observer que mantiene sincronizada la lista de productos con el ViewModel (usando LiveData)
        configureViewModelObserver();

        // Se hace un análisis inicial para saber si hay productos a mostrar, o se debe mostrar el placeholder
        checkSearchResultIsEmpty();

        // Configura el evento del SearchView para invocar al método de ejecutar una búsqueda del ViewModel
        setUpSearchEngine();
    }

    /**
     * Configura el mecanismo de disparo de la búsqueda en el SearchView
     */
    private void setUpSearchEngine() {
        // Se configura el evento correspondiente del SearchView para lanzar una búsqueda
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Se ejecuta la búsqueda
                executeSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // No se lanza la búsqueda en el evento text change del SearchView por tema de performance,
                // pues se estaría encuestando al servidor cada vez que se cambia el texto
                return false;
            }
        });
    }

    /**
     * Permite configurar el observer que va a mantener sincronizada la lista de productos con el ViewModel
     */
    private void configureViewModelObserver() {
        // Se utiliza LiveData para mantener sincronizada la lista de resultados de búsqueda con el RecyclerView
        // aunque como no se está usando paginado, se podría asignar directamente la lista al adapter cuando se
        // realiza la consulta, ya que la lista no va a cambiar si no es con otra consulta
        Observer<ProductSearchResultList> productSearchResulListObserver = resultList -> {
            if (resultList != null && resultList.hasResults()) {
                // Hay productos para mostrar, muestro el RecyclerView y oculto el placeholder
                mRecyclerView.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
            } else {
                // No hay productos para mostrar, muestro el placeholder y oculto el RecyclerView
                mRecyclerView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
            }
            // Actualizo el listado de elementos en el adapter
            if (resultList != null) {
                mProductsRecyclerViewAdapter.updateProductsList(resultList.getResults());
            }
        };
        // Configuro el Observer para ser notificado cuando cambia el listado de productos en el ViewModel
        mViewModel.getSearchResultList().observe(getViewLifecycleOwner(), productSearchResulListObserver);
    }

    /**
     * Método para configurar el RecyvlerView
     */
    private void setupRecyclerView() {
        // Configurar separadores del RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        try {
            dividerItemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.yellow_light, requireActivity().getTheme())));
        } catch (IllegalStateException ex) {
            logger.error("El fragmento no está asociado a una actividad.");
        }
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // Se oculta inicialmente la vista que se muestra si el listado de productos está vacío
        mEmptyView.setVisibility(View.GONE);
        // Se crea el adapter para el RecyclerView
        mProductsRecyclerViewAdapter = new ProductsRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mProductsRecyclerViewAdapter);
    }

    /**
     * Comprueba si existen productos para mostrar, y en correspondencia muestra el placeholder de la lista
     */
    private void checkSearchResultIsEmpty() {
        if (mViewModel.getSearchResultList().getValue() == null || !mViewModel.getSearchResultList().getValue().hasResults()) {
            mEmptyView.setVisibility(View.VISIBLE);
            mSearchView.setIconified(false);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mSearchView.setIconified(true);
        }
    }

    /**
     * Ejecuta una búsqueda dada una palabra clave. Consulta al end-point correspondiente
     * a través del ViewModel. Muestra un diálogo de progreso mientras dura la consulta
     * y da feedback al usuario en caso de que no se encuentren productos o se produzca
     * algún error (como problemas de conexión por ejemplo)
     * @param value Texto por el que se va a buscar
     */
    private void executeSearch(String value) {
        logger.info("Executing search by " + value);

        // Mostrar diálogo de progreso
        mProgressDialog = new ProgressDialog(ProductListFragment.this.getActivity());
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.show();
        // Invicar al método de buscar productos del ViewModel
        mViewModel.searchProducts(value, new ApiCallback() {
            /**
             * La llamada al servicio fue exitosa (no es necesario recibir el response, ya que la lista
             * será actualizada con el LiveData del ViewModel)
             */
            @Override
            public void onResponse() {
                mProgressDialog.dismiss();
                // Si no hay elementos para buscar debe ser que no se encontraron productos para la palabra de
                // búsqueda, por tanto notifico al usuario
                if (mProductsRecyclerViewAdapter.getItemCount() == 0) {
                    showAlertDialog(getString(R.string.empty_search_result), getActivity());
                }
            }

            /**
             * Se produjo un error al consultar al servicio
             * @param t Error que se produjo
             */
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                logger.error("Error en la búsqueda " + t.getMessage());
                mProgressDialog.dismiss();
                // Se notifica al usuario acerca del error ocurrido
                if (t instanceof IOException) {
                    // Reportar error de conexión
                    showErrorDialog(getString(R.string.error_search_net), getActivity());
                } else {
                    // Reportar cualquier otro error
                    showErrorDialog(getString(R.string.error_search), getActivity());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        logger.info("Fragment view destroyed");
        super.onDestroyView();
        mBinding = null;
    }
}