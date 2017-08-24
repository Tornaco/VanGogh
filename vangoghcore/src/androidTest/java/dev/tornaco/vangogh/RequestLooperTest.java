package dev.tornaco.vangogh;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.newstand.logger.Logger;

import java.util.concurrent.CountDownLatch;

import dev.tornaco.vangogh.media.ImageSource;
import dev.tornaco.vangogh.queue.Request;
import dev.tornaco.vangogh.queue.RequestDispatcher;
import dev.tornaco.vangogh.queue.RequestLooper;
import dev.tornaco.vangogh.queue.Seq;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */

@RunWith(AndroidJUnit4.class)
public class RequestLooperTest {

    private static final int REQUEST_COUNT = 100;

    private CountDownLatch latch;

    @Before
    public void setup() {
        latch = new CountDownLatch(REQUEST_COUNT);
    }

    @Test
    public void testSeqFIFO() throws InterruptedException {

        RequestLooper requestLooper = RequestLooper.newInstance(new DummyDispatch(), Seq.FIFO);

        for (int i = 0; i < REQUEST_COUNT; i++) {
            Request r = Request.builder().requestTimeMills(System.currentTimeMillis())
                    .alias("r-" + i)
                    .imageSource(new ImageSource()).build();
            requestLooper.onNewRequest(r);
        }

        latch.await();
    }

    @Test
    public void testSeqFILO() throws InterruptedException {

        RequestLooper requestLooper = RequestLooper.newInstance(new DummyDispatch(), Seq.FILO);

        for (int i = 0; i < REQUEST_COUNT; i++) {
            Request r = Request.builder().requestTimeMills(System.currentTimeMillis())
                    .alias("r-" + i)
                    .imageSource(new ImageSource()).build();
            requestLooper.onNewRequest(r);
        }

        latch.await();
    }


    class DummyDispatch implements RequestDispatcher {
        @Override
        public boolean dispatch(@NonNull Request request) {
            try {
                Thread.sleep(500);
                Logger.i("dispatch: %s", request);
            } catch (InterruptedException ignored) {

            } finally {
                latch.countDown();
            }
            return true;
        }
    }
}
