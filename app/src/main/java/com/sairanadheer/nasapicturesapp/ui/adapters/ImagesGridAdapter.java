package com.sairanadheer.nasapicturesapp.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sairanadheer.nasapicturesapp.R;
import com.sairanadheer.nasapicturesapp.ui.ImageDetailFragment;

import java.util.List;

public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridAdapter.ImagesGridViewHolder> {

    private final int VIEW_TYPE_IMAGE = 0;
    private final int VIEW_TYPE_LOADING = 1;


    private Context mContext;
    private List<String> imageURLs;

    public ImagesGridAdapter(Context mContext, List<String> imageURLs) {
        this.mContext = mContext;
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
    public void onBindViewHolder(@NonNull ImagesGridViewHolder holder, final int position) {

        populateItemRows(holder, position);

        holder.gridImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageDetailFragment imageDetailFragment = ImageDetailFragment.newInstance(imageURLs, position);
                imageDetailFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_NoActionBar);
                imageDetailFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), "ImagesGridFragment");
            }
        });

    }

    private void populateItemRows(ImagesGridViewHolder holder, int position) {
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

    class ImagesGridViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView gridImage;

        ImagesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridImage);
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
