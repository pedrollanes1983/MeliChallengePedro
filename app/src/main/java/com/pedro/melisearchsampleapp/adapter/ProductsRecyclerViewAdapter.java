package com.pedro.melisearchsampleapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.databinding.ProductListContentBinding;
import com.pedro.melisearchsampleapp.fragments.ProductDetailFragment;
import com.pedro.melisearchsampleapp.model.Product;
import com.squareup.picasso.Picasso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador del RecyclerView para mostrar el listado de productos encontrados en al búsqueda
 */
public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {
    private static final Logger logger = LoggerFactory.getLogger(ProductsRecyclerViewAdapter.class);
    private List<Product> mValues;

    private final Context mContext;

    public ProductsRecyclerViewAdapter(Context context) {
        mContext = context;
        mValues = new ArrayList<>();
    }

    @Override
    public ProductsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ProductListContentBinding binding =
                ProductListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductsRecyclerViewAdapter.ViewHolder(binding);

    }

    public void updateProductsList(List<Product> products) {
        this.mValues.clear();
        this.mValues.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ProductsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mPriceView.setText(mValues.get(position).getPriceFormatted());
        holder.mCurrencyView.setText(String.format("(%s)", mValues.get(position).getCurrencyId()));
        Picasso.with(mContext).load(mValues.get(position).getThumbnail())
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(holder.mThumbView);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(itemView -> {
            Product item =
                    (Product) itemView.getTag();
            Bundle arguments = new Bundle();
            // Se utiliza un argumento para conocer el id del producto que se va a mostrar en la pantalla de detalles
            arguments.putString(ProductDetailFragment.ARG_ITEM_ID, item.getId());
            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTitleView;
        final TextView mPriceView;
        final TextView mCurrencyView;
        final ImageView mThumbView;

        ViewHolder(ProductListContentBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.title;
            mPriceView = binding.price;
            mThumbView = binding.image;
            mCurrencyView = binding.currency;
        }

    }
}
