package com.sairanadheer.nasapicturesapp.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sairanadheer.nasapicturesapp.R;

import java.util.List;

public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridAdapter.ImagesGridViewHolder> {

    private final int VIEW_TYPE_IMAGE = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private Context context;
    private List<String> imageURLs;

    public ImagesGridAdapter(Context context, List<String> imageURLs) {
        this.context = context;
        this.imageURLs = imageURLs;
    }

    @NonNull
    @Override
    public ImagesGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_IMAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_images_grid, parent, false);
            return new ImagesGridViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loader, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesGridViewHolder holder, int position) {

        populateItemRows(holder, position);

    }

    private void populateItemRows(ImagesGridViewHolder holder, int position) {
        final String imageURL = imageURLs.get(position);
        if (!TextUtils.isEmpty(imageURL)) {
            RequestOptions defaultOptions = new RequestOptions();
            defaultOptions.transform(new RoundedCorners(1));
            defaultOptions.error(R.color.colorWhite);

            RequestOptions cachingOptions = new RequestOptions();
            cachingOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context).applyDefaultRequestOptions(defaultOptions)
                    .load(imageURLs.get(position))
                    .apply(cachingOptions)
                    .thumbnail(0.1f)
                    .into(holder.gridImage);
        }
    }

    @Override
    public int getItemCount() {
        return imageURLs == null ? 0 : imageURLs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return imageURLs.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_IMAGE;
    }

    class ImagesGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView gridImage;

        ImagesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridImage);
            gridImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == gridImage.getId()) {

            }
        }
    }

    private class LoadingViewHolder extends ImagesGridViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
