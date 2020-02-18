package com.sairanadheer.nasapicturesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.sairanadheer.nasapicturesapp.R;
import com.sairanadheer.nasapicturesapp.databinding.FragmentImageDetailBinding;
import com.sairanadheer.nasapicturesapp.adapters.ImageDetailPagerAdapter;

import java.util.List;

public class ImageDetailFragment extends DialogFragment {

    private FragmentImageDetailBinding mImageDetailBinding;
    private ViewPager2 imagePager;
    private List<String> imageURLs;
    private int imagePosition;

    public ImageDetailFragment(List<String> imageURLs, int imagePosition) {
        this.imageURLs = imageURLs;
        this.imagePosition = imagePosition;
    }

    public static ImageDetailFragment newInstance(List<String> imageURLs, int imagePosition){
        return new ImageDetailFragment(imageURLs, imagePosition);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mImageDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail, container, false);
        configureViewElements();
        bindData();
        return mImageDetailBinding.getRoot();
    }

    private void bindData() {
        ImageDetailPagerAdapter imageDetailAdapter = new ImageDetailPagerAdapter(getContext(), imageURLs);
        imagePager.setAdapter(imageDetailAdapter);
        imagePager.setCurrentItem(imagePosition, false);
    }

    private void configureViewElements() {
        imagePager = mImageDetailBinding.imagePager;
    }
}
