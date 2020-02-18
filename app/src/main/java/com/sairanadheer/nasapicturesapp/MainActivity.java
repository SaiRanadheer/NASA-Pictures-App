package com.sairanadheer.nasapicturesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import com.sairanadheer.nasapicturesapp.ui.ImagesGridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImagesGridFragment();
    }

    private void showImagesGridFragment() {
        ImagesGridFragment imagesGridFragment = ImagesGridFragment.newInstance();
        imagesGridFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_NoActionBar);
        imagesGridFragment.show(getSupportFragmentManager(), "ImagesGridFragment");
    }
}
