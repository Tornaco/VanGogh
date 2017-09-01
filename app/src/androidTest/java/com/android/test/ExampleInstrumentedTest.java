package com.android.test;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.tornaco.vangogh.Vangogh;
import dev.tornaco.vangogh.display.CircleImageEffect;
import dev.tornaco.vangogh.display.appliers.FadeOutFadeInApplier;
import dev.tornaco.vangogh.loader.Loader;
import dev.tornaco.vangogh.loader.LoaderObserver;
import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;
import dev.tornaco.vangogh.sample.R;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.audioeffect", appContext.getPackageName());

        ImageView imageView = null;

        Vangogh.with(InstrumentationRegistry.getContext())
                .load(Uri.EMPTY)
                .placeHolder(R.drawable.ic_home_black_24dp)
                .fallback(R.drawable.ic_dashboard_black_24dp)
                .applier(new FadeOutFadeInApplier())
                .effect(new CircleImageEffect())
                .skipDiskCache(false)
                .skipMemoryCache(true)
                .usingLoader(new CustomLoader())
                .into(imageView);
    }

    /**
     * This is a custom loader sample.
     */
    class CustomLoader implements Loader<Image> {

        @Nullable
        @Override
        public Image load(@NonNull ImageSource source, @Nullable LoaderObserver observer) {
            return null;
        }

        @Override
        public int priority() {
            return 0;
        }
    }
}
