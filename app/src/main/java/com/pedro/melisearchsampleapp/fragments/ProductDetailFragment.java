package com.pedro.melisearchsampleapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import com.pedro.melisearchsampleapp.MeliSearchSampleApplication;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.databinding.FragmentProductDetailBinding;
import com.pedro.melisearchsampleapp.model.Product;
import com.pedro.melisearchsampleapp.model.ProductSearchResultList;
import com.pedro.melisearchsampleapp.viewmodels.SearchResultsViewModel;
import com.pedro.melisearchsampleapp.viewmodels.ViewModelProviderFactory;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Locale;

/**
 * Fragmento utilizado para mostrar los detalles de un producto
 */
public class ProductDetailFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(ProductListFragment.class);

    /**
     * Identificador del producto del cual se mostrarán los detalles
     */
    public static final String ARG_ITEM_ID = "item_id";
    private ImageView mThumbView;
    private TextView mTitleView;
    private TextView mPriceView;
    private TextView mCurrencyView;
    private TextView mAvailableCount;
    private TextView mCondition;
    private TextView mStopTime;
    private TextView mSoldCount;

    /**
     * ViewModel asociado a la actividad principal y que comparten los fragmentos de la app.
     * Contiene el listado de productos recuperado del servidor y la clave de búsqueda.
     */
    private SearchResultsViewModel mViewModel;

    private FragmentProductDetailBinding binding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    /**
     * Constructor vacío obligatorio, que permite instanciar el fragmento cuando cambia la orientación del dispositivo
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MeliSearchSampleApplication.getApplication().androidInjector().inject(this);

        // Se obtiene el ViewModel de la Activity
        mViewModel = viewModelProviderFactory.provideSearchResultsViewModel(getActivity());

        logger.info("Fragment view created");

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mTitleView = binding.title;
        mThumbView = binding.image;
        mCurrencyView = binding.currency;
        mPriceView = binding.price;
        mAvailableCount = binding.availableCount;
        mSoldCount = binding.soldCount;
        mStopTime = binding.stopTime;
        mCondition = binding.condition;

        if (getArguments() != null) {
            // Indico al ViewModel que localize el producto para cargar los detalles
            mViewModel.findProductToDisplayDetails(getArguments().get(ProductDetailFragment.ARG_ITEM_ID).toString());
        }

        if (mViewModel.getProductToDisplayDetails() == null || mViewModel.getProductToDisplayDetails().getValue() == null) {
            reportProductNotFoundError();
            return rootView;
        }

        configureViewModelObserver();

        return rootView;
    }

    private void reportProductNotFoundError() {
        logger.error(getString(R.string.null_product_error));
        showErrorDialog(getString(R.string.null_product_error), getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        logger.info("Fragment view destroyed");
    }

    /**
     * Permite actualizar el contenido de los detalles del producto actual
     */
    private void updateContent(Product product) {
        if (mViewModel.getProductToDisplayDetails().getValue() != null) {
            this.mTitleView.setText(product.getTitle());
            this.mPriceView.setText(product.getPriceFormatted());
            this.mCurrencyView.setText(String.format("(%s)", product.getCurrencyId()));
            this.mAvailableCount.setText(String.format(Locale.getDefault(),"%d", product.getAvailableQuantity()));
            this.mSoldCount.setText(String.format(Locale.getDefault(),"%d", product.getSoldQuantity()));
            this.mCondition.setText(product.getCondition());
            this.mStopTime.setText(product.getStopTime());
            Picasso.with(getActivity()).load(product.getThumbnail())
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .into(mThumbView);

        }
    }

    private void configureViewModelObserver() {
        // Se utiliza LiveData para mantener sincronizada la lista de resultados de búsqueda con el RecyclerView
        // aunque como no se está usando paginado, se podría asignar directamente la lista al adapter cuando se
        // realiza la consulta, ya que la lista no va a cambiar si no es con otra consulta
        Observer<Product> productDetailsObserver = product -> {
            if (product != null) {
                // Se encontraron los detalles del producto a mostrar
                updateContent(product);
            } else {
                // No se encontró el producto para mostrar los detalles, reporto error
                reportProductNotFoundError();
            }
        };
        // Configuro el Observer para ser notificado cuando cambia el listado de productos en el ViewModel
        mViewModel.getProductToDisplayDetails().observe(getViewLifecycleOwner(), productDetailsObserver);
    }
}