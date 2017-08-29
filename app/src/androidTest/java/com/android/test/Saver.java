package com.android.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;

import static java.lang.Thread.sleep;

/**
 * Created by guohao4 on 2017/8/29.
 * Email: Tornaco@163.com
 */

public class Saver {

    @Test
    public void save() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject download = device.findObject(new UiSelector().resourceId("com.lovebizhi.wallpaper:id/btDown"));

        UiScrollable scrollable = new UiScrollable(new UiSelector().resourceId("com.lovebizhi.wallpaper:id/pull_to_refresh_scrollview"));

        while (true) {
            if (download.waitForExists(5000)) {
                download.click();

                sleep(5000);
                scrollable.swipeLeft(100);
            }
        }
    }
}
