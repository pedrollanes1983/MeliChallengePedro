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
import com.pedro.melisearchsampleapp.api.SearchCallback;
import com.pedro.melisearchsampleapp.databinding.FragmentProductListBinding;

import com.pedro.melisearchsampleapp.model.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ProductDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProductListFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(ProductListFragment.class);

    ProgressDialog mProgressDoalog;

    private SearchView mSearchView;

    private RecyclerView mRecyclerView;

    private TextView mEmptyView;

    private FragmentProductListBinding mBinding;

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

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        final View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        loadPreviousSearchValues(itemDetailFragmentContainer);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                executeSearch(query, itemDetailFragmentContainer);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void loadPreviousSearchValues(View itemDetailFragmentContainer) {
        if (mViewModel != null && mViewModel.getSearchResultList() != null && mViewModel.getSearchResultList().hasResults()) {
            setupRecyclerView(mRecyclerView, itemDetailFragmentContainer, mViewModel.getSearchResultList(), mEmptyView);
            mSearchView.setQuery(mViewModel.getSearchValue(), false);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mSearchView.setIconified(false);
        }
    }

    private void executeSearch(String value, View itemDetailFragmentContainer) {
        logger.info("Executing search by " + value);

        mProgressDoalog = new ProgressDialog(ProductListFragment.this.getActivity());
        mProgressDoalog.setMessage(getString(R.string.loading));
        mProgressDoalog.show();
        mViewModel.searchProducts(value, new SearchCallback() {
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
                showErrorDialog(getString(R.string.error_search), getActivity());
            }
        });
    }

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