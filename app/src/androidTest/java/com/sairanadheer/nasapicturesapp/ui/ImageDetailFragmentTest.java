package com.sairanadheer.nasapicturesapp.ui;

import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.sairanadheer.nasapicturesapp.MainActivity;
import com.sairanadheer.nasapicturesapp.R;
import com.sairanadheer.nasapicturesapp.adapters.ImagesGridAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.junit.Assert.*;

public class ImageDetailFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        String json;
        try {
            InputStream stream = mActivity.getAssets().open("data.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray imagesData = new JSONArray(json);

            JSONObject[] imageDataObjects = new JSONObject[imagesData.length()];

            for (int i = 0; i < imagesData.length(); i++) {
                imageDataObjects[i] = imagesData.getJSONObject(i);
            }

            Arrays.sort(imageDataObjects, Collections.reverseOrder((JSONObject object1, JSONObject object2) -> {
                try {
                    return (object1.getString("date").compareTo(object2.getString("date")));
                } catch (JSONException e) {
                }
                return 0;
            }));

            JSONArray sortedImagesData = new JSONArray(imageDataObjects);

            ImageDetailFragment imageDetailFragment = ImageDetailFragment.newInstance(sortedImagesData, 5);
            imageDetailFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_NoActionBar);
            imageDetailFragment.show(mActivity.getSupportFragmentManager(), "Image Detail Fragment");

            InstrumentationRegistry.getInstrumentation().waitForIdleSync();

            View view = Objects.requireNonNull(imageDetailFragment.getView()).findViewById(R.id.imagePager);
            assertNotNull(view);

        } catch (Exception e) {
        }


    }

    @After
    public void tearDown() {
        mActivity = null;
    }

}