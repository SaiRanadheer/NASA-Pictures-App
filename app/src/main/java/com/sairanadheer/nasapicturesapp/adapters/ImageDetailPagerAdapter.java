package com.sairanadheer.nasapicturesapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sairanadheer.nasapicturesapp.R;
import com.sairanadheer.nasapicturesapp.ui.ImageDetailFragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageDetailPagerAdapter extends RecyclerView.Adapter<ImageDetailPagerAdapter.ImageDetailViewHolder> {

    private Context mContext;
    private JSONArray imagesData;
    private ImageDetailFragment imageDetailFragmentDialog;

    public ImageDetailPagerAdapter(Context mContext, JSONArray imagesData, ImageDetailFragment imageDetailFragmentDialog) {
        this.mContext = mContext;
        this.imagesData = imagesData;
        this.imageDetailFragmentDialog = imageDetailFragmentDialog;
    }

    @NonNull
    @Override
    public ImageDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image_detail, parent, false);
        return new ImageDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDetailViewHolder holder, int position) {
        try {
            JSONObject imageData = imagesData.getJSONObject(position);
            final String imageURL = imageData.getString("url");
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
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(holder.imageDetail);

                holder.imageTitle.setText(imageData.getString("title"));
                String imageDescription = imageData.getString("explanation");
                if (imageDescription.length() > 100) {
                    holder.imageDesc.setText(imageDescription.substring(0, 97).concat("..."));
                    holder.expandText.setText("Read More");
                } else {
                    holder.imageDesc.setText(imageDescription);
                    holder.expandText.setVisibility(View.GONE);
                }

                holder.expandText.setOnClickListener(view -> {
                    if(holder.expandText.getText().equals("Read More")) {
                        holder.imageDesc.setText(imageDescription);
                        holder.expandText.setText("Read Less");
                    } else {
                        holder.imageDesc.setText(imageDescription.substring(0,97).concat("..."));
                        holder.expandText.setText("Read More");
                    }
                });
            }
        } catch (Exception e) {
            showAlertDialog();
        }
    }

    @Override
    public int getItemCount() {
        return imagesData == null ? 0 : imagesData.length();
    }

    public class ImageDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppCompatImageView imageDetail;
        private AppCompatImageView backBtn;
        private AppCompatTextView imageTitle;
        private AppCompatTextView imageDesc;
        private AppCompatTextView expandText;

        public ImageDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDetail = itemView.findViewById(R.id.imageDetail);
            backBtn = itemView.findViewById(R.id.backBtn);
            imageTitle = itemView.findViewById(R.id.imageTitle);
            imageDesc = itemView.findViewById(R.id.imageDesc);
            expandText = itemView.findViewById(R.id.expandText);
            expandText.setPaintFlags(expandText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            backBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == backBtn.getId()) {
                imageDetailFragmentDialog.dismiss();
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("ERROR");
        builder.setMessage("Something went wrong.Please try again later");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
