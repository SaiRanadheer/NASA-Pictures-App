package com.sairanadheer.nasapicturesapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sairanadheer.nasapicturesapp.R;

import java.util.List;

public class ImageDetailPagerAdapter extends RecyclerView.Adapter<ImageDetailPagerAdapter.ImageDetailViewHolder> {

    private Context mContext;
    private List<String> imageURLs;

    public ImageDetailPagerAdapter(Context mContext, List<String> imageURLs) {
        this.mContext = mContext;
        this.imageURLs = imageURLs;
    }

    @NonNull
    @Override
    public ImageDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image_detail, parent, false);
        return new ImageDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDetailViewHolder holder, int position) {
        final String imageURL = imageURLs.get(position);
        if (!TextUtils.isEmpty(imageURL)) {
            RequestOptions defaultOptions = new RequestOptions();
            defaultOptions.transform(new RoundedCorners(1));
            defaultOptions.error(R.color.colorWhite);

            RequestOptions cachingOptions = new RequestOptions();
            cachingOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(mContext).applyDefaultRequestOptions(defaultOptions)
                    .load(imageURL)
                    .apply(cachingOptions)
                    .into(holder.imageDetail);
        }
    }

    @Override
    public int getItemCount() {
        return imageURLs == null ? 0 : imageURLs.size();
    }

    public class ImageDetailViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView imageDetail;
        public ImageDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDetail = itemView.findViewById(R.id.imageDetail);
        }
    }
}
