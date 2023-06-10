package com.pedro.melisearchsampleapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.pedro.melisearchsampleapp.R;
import com.pedro.melisearchsampleapp.databinding.ProductListContentBinding;
import com.pedro.melisearchsampleapp.fragments.ProductDetailFragment;
import com.pedro.melisearchsampleapp.fragments.ProductListFragment;
import com.pedro.melisearchsampleapp.model.Product;
import com.squareup.picasso.Picasso;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {
    private static final Logger logger = LoggerFactory.getLogger(ProductsRecyclerViewAdapter.class);
    private final List<Product> mValues;
    private final View mItemDetailFragmentContainer;

    private final Context mContext;

    public ProductsRecyclerViewAdapter(List<Product> items,
                                       View itemDetailFragmentContainer,
                                       Context context) {
        mValues = items;
        mItemDetailFragmentContainer = itemDetailFragmentContainer;
        mContext = context;
    }

    @Override
    public ProductsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ProductListContentBinding binding =
                ProductListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductsRecyclerViewAdapter.ViewHolder(binding);

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
            arguments.putString(ProductDetailFragment.ARG_ITEM_ID, item.getId());
            if (mItemDetailFragmentContainer != null) {
                Navigation.findNavController(mItemDetailFragmentContainer)
                        .navigate(R.id.fragment_product_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
            }
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
