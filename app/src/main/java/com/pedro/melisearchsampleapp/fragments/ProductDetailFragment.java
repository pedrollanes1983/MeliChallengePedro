package com.pedro.melisearchsampleapp.fragments;

import android.os.Bundle;

import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.model.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.model.Product;
import com.pedro.melisearchsampleapp.databinding.FragmentProductDetailBinding;
import com.squareup.picasso.Picasso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Fragmento utilizado para mostrar los detalles de un producto
 */
public class ProductDetailFragment extends Fragment {
    private static final Logger logger = LoggerFactory.getLogger(ProductListFragment.class);

    /**
     * Identificador del producto del cual se mostrarán los detalles
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * Producto a mostrar detalles
     */
    private Product product;
    private ImageView mThumbView;
    private TextView mTitleView;
    private TextView mPriceView;
    private TextView mCurrencyView;
    private TextView mAvailableCount;

    @Inject
    SearchResultsViewModel viewModel;

    private FragmentProductDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);

        logger.info("Fragment view created");

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mTitleView = binding.title;
        mThumbView = binding.image;
        mCurrencyView = binding.currency;
        mPriceView = binding.price;
        mAvailableCount = binding.availableCount;

        product = viewModel.getById(getArguments().get(ProductDetailFragment.ARG_ITEM_ID).toString());

        getActivity().setTitle(product.getTitle());

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        logger.info("Fragment view destroyed");
    }

    private void updateContent() {
        if (product != null) {
            this.mTitleView.setText(product.getTitle());
            this.mPriceView.setText(product.getPriceFormatted());
            this.mCurrencyView.setText(String.format("(%s)", product.getCurrencyId()));
            this.mAvailableCount.setText(product.getAvailableQuantity().toString());
            Picasso.with(getActivity()).load(product.getThumbnail())
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(mThumbView);

        }
    }
}