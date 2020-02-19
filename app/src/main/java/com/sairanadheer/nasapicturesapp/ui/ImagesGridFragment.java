package com.sairanadheer.nasapicturesapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sairanadheer.nasapicturesapp.R;
import com.sairanadheer.nasapicturesapp.databinding.FragmentImagesGridBinding;
import com.sairanadheer.nasapicturesapp.adapters.ImagesGridAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ImagesGridFragment extends DialogFragment {

    private FragmentImagesGridBinding mImagesGridBinding;
    private RecyclerView imagesFeedView;
    private AppCompatTextView noImagesMessage;

    public static ImagesGridFragment newInstance() {
        return new ImagesGridFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImagesGridBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_images_grid, container, false);
        configureViewElements();
        retrieveImages();
        return mImagesGridBinding.getRoot();
    }

    private void configureViewElements() {
        imagesFeedView = mImagesGridBinding.imagesFeedView;
        imagesFeedView.setHasFixedSize(true);
        imagesFeedView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        noImagesMessage = mImagesGridBinding.noImagesMessage;
    }

    private void retrieveImages() {
        String json;
        try {
            InputStream stream = Objects.requireNonNull(getContext()).getAssets().open("data.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray imagesData = new JSONArray(json);

            JSONObject[] imageDataObjects = new JSONObject[imagesData.length()];

            for(int i = 0; i < imagesData.length(); i++){
                imageDataObjects[i] = imagesData.getJSONObject(i);
            }

            Arrays.sort(imageDataObjects, Collections.reverseOrder( (JSONObject object1, JSONObject object2) -> {
                try {
                    return (object1.getString("date").compareTo(object2.getString("date")));
                } catch (JSONException e) {
                    showAlertDialog();
                }
                return 0;
            }));

            JSONArray sortedImagesData = new JSONArray(imageDataObjects);

            if (imagesData.length() == 0) {
                noImagesMessage.setVisibility(View.VISIBLE);
                imagesFeedView.setVisibility(View.GONE);
            } else {
                noImagesMessage.setVisibility(View.GONE);
                imagesFeedView.setVisibility(View.VISIBLE);
                ImagesGridAdapter imagesGridAdapter = new ImagesGridAdapter(getContext(), sortedImagesData);
                imagesFeedView.setAdapter(imagesGridAdapter);
            }

        } catch (Exception e) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
