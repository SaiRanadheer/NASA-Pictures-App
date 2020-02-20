package com.sairanadheer.nasapicturesapp.ui;

import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.sairanadheer.nasapicturesapp.MainActivity;
import com.sairanadheer.nasapicturesapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class ImagesGridFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        LinearLayoutCompat mainActivityContainer = mActivity.findViewById(R.id.mainActivityContainer);
        assertNotNull(mainActivityContainer);

        ImagesGridFragment imagesGridFragment = ImagesGridFragment.newInstance();
        imagesGridFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_NoActionBar);
        imagesGridFragment.show(mActivity.getSupportFragmentManager(), "ImagesGridFragment");

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        View view = Objects.requireNonNull(imagesGridFragment.getView()).findViewById(R.id.imagesFeedView);
        assertNotNull(view);
    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}