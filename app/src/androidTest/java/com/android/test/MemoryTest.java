package com.android.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.GridView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.tornaco.vangogh.sample.MainActivity;

/**
 * Created by guohao4 on 2017/8/29.
 * Email: Tornaco@163.com
 */
@RunWith(AndroidJUnit4.class)
public class MemoryTest {

    private static final int TEST_CYCLE = 24;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNoOOM() throws UiObjectNotFoundException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiScrollable scrollable = new UiScrollable(new UiSelector().className(GridView.class));
        scrollable.waitForExists(5000);

        for (int i = 0; i < TEST_CYCLE; i++) {
            scrollable.flingToEnd(1000);
            scrollable.flingToBeginning(1000);
        }
    }
}
