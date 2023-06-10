package com.pedro.melisearchsampleapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.adapter.ProductsRecyclerViewAdapter;
import com.pedro.melisearchsampleapp.api.ApiCallback;
import com.pedro.melisearchsampleapp.databinding.FragmentProductListBinding;

import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Fragmento del Buscador. Permite introducir la clave de búsqueda e
 * invocar al end-point que recupera los productos del servidor
 */
public class ProductListFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(ProductListFragment.class);

    /**
     * Diálogo de progreso que se muestra durante las operaciones asincrónicas
     */
    ProgressDialog mProgressDoalog;

    /**
     * Componente de búsqueda
     */
    private SearchView mSearchView;

    /**
     * RecyvlerView para mostrar el listado de productos
     */
    private RecyclerView mRecyclerView;

    /**
     * TextView que indica que no hay elementos para mostrar
     */
    private TextView mEmptyView;

    private FragmentProductListBinding mBinding;

    /**
     * ViewModel asociado al fragmento. Administra el listado de productos recuperado del servidor
     */
    @Inject
    SearchResultsViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);

        logger.info("Fragment view created");

        mBinding = FragmentProductListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = mBinding.itemList;
        mSearchView = mBinding.itemSearch;
        mEmptyView = mBinding.emptyView;

        final View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        // Se intenta cargar la búsqueda anterior (en caso de que haya alguna). De esta forma no se pierde
        // el estado de la vista cuando el fragmento es recreado (por ejemplo al rotar el dispositivo)
        loadPreviousSearchValues(itemDetailFragmentContainer);

        // Evento para lanzar la búsqueda
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                executeSearch(query, itemDetailFragmentContainer);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // No se busca en el text change por tema de performance, solamente con la acción de buscar se lanza la búsqueda
                return false;
            }
        });
    }

    /**
     * Intenta (si es posible) cargar la búsqueda anterior (en caso de que haya alguna). De esta forma no se pierde
     * el estado de la vista cuando el fragmento es recreado (por ejemplo al rotar el dispositivo)
     * @param itemDetailFragmentContainer
     */
    private void loadPreviousSearchValues(View itemDetailFragmentContainer) {
        if (mViewModel != null && mViewModel.getSearchResultList() != null && mViewModel.getSearchResultList().hasResults()) {
            setupRecyclerView(mRecyclerView, itemDetailFragmentContainer, mViewModel.getSearchResultList(), mEmptyView);
            mSearchView.setQuery(mViewModel.getSearchValue(), false);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mSearchView.setIconified(false);
        }
    }

    /**
     * Ejecuta una búsqueda dada una palabra clave
     * @param value Texto por el que se va a buscar
     * @param itemDetailFragmentContainer
     */
    private void executeSearch(String value, View itemDetailFragmentContainer) {
        logger.info("Executing search by " + value);

        mProgressDoalog = new ProgressDialog(ProductListFragment.this.getActivity());
        mProgressDoalog.setMessage(getString(R.string.loading));
        mProgressDoalog.show();
        mViewModel.searchProducts(value, new ApiCallback() {
            @Override
            public void onResponse() {
                mProgressDoalog.dismiss();
                setupRecyclerView(mRecyclerView, itemDetailFragmentContainer, mViewModel.getSearchResultList(), mEmptyView);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                logger.error("Error en la búsqueda " + t.getMessage());
                mProgressDoalog.dismiss();
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

    /**
     * Permite configurar el RecyclerVIew con los productos encontrados
     * @param recyclerView Recyvler a configurar
     * @param itemDetailFragmentContainer
     * @param resultList Listado de productos a mostrar
     * @param emptyView Vista que se muestra en caso de qu no haya productos a mostrar
     */
    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer,
            ProductSearchResultList resultList,
            TextView emptyView
    ) {

        if (resultList != null && resultList.hasResults()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(new ProductsRecyclerViewAdapter(
                    resultList.getResults(),
                    itemDetailFragmentContainer,
                    getActivity()
            ));
        } else {
            showAlertDialog(getString(R.string.empty_search_result), getActivity());
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        logger.info("Fragment view destroyed");
        super.onDestroyView();
        mBinding = null;
    }
}