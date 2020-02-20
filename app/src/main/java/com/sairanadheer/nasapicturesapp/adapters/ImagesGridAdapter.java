package com.sairanadheer.nasapicturesapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONArray;
import org.json.JSONException;

public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridAdapter.ImagesGridViewHolder> {

    private Context mContext;
    private JSONArray imagesData;

    public ImagesGridAdapter(Context mContext, JSONArray imagesData) {
        this.mContext = mContext;
        this.imagesData = imagesData;
    }

    @NonNull
    @Override
    public ImagesGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_images_grid, parent, false);
        return new ImagesGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesGridViewHolder holder, final int position) {

        populateItemRows(holder, position);

        holder.gridImage.setOnClickListener(view -> {
            ImageDetailFragment imageDetailFragment = ImageDetailFragment.newInstance(imagesData, position);
            imageDetailFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_NoActionBar);
            imageDetailFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "Image Detail Fragment");
        });

    }

    private void populateItemRows(ImagesGridViewHolder holder, int position) {
        try {
            final String imageURL = imagesData.getJSONObject(position).getString("url");
            if (!TextUtils.isEmpty(imageURL)) {
                RequestOptions defaultOptions = new RequestOptions()
                        .transform(new RoundedCorners(1))
                        .error(R.color.colorWhite);

                RequestOptions cachingOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(mContext).applyDefaultRequestOptions(defaultOptions)
                        .load(imageURL)
                        .apply(cachingOptions)
                        .placeholder(mContext.getDrawable(R.drawable.loading_animation))
                        .thumbnail(0.1f)
                        .into(holder.gridImage);
            }
        } catch (JSONException e) {
            showAlertDialog();
        }
    }

    @Override
    public int getItemCount() {
        return imagesData == null ? 0 : imagesData.length();
    }


    class ImagesGridViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView gridImage;

        ImagesGridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridImage);
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("ERROR");
        builder.setMessage("Something went wrong.Please try again later");
        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }
}
