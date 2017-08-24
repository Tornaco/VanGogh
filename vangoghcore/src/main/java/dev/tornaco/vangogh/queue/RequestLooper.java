package dev.tornaco.vangogh.queue;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guohao4 on 2017/8/24.
 * Email: Tornaco@163.com
 */
public class RequestLooper {

    private static final AtomicInteger LOOP_ID = new AtomicInteger(0);

    private static final int MSG_HANDLE_NEW_REQUEST = 0x1;

    private HandlerThread hr;

    private final Handler handler;
    private final Seq seq;
    private final RequestDispatcher dispatcher;

    private RequestLooper(RequestDispatcher dispatcher, Seq seq) {
        this.hr = new HandlerThread("RequestLooper#" + LOOP_ID.getAndIncrement());
        this.hr.start();
        Looper looper = hr.getLooper();
        this.handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                RequestLooper.this.handleMessage(msg);
            }
        };
        this.seq = seq;
        this.dispatcher = dispatcher;
    }

    public static RequestLooper newInstance(RequestDispatcher dispatcher) {
        return newInstance(dispatcher, Seq.FIFO);
    }

    public static RequestLooper newInstance(RequestDispatcher dispatcher, Seq seq) {
        Assert.assertNotNull("RequestDispatcher is null", dispatcher);
        Assert.assertNotNull("Seq is null", seq);
        return new RequestLooper(dispatcher, seq);
    }

    public void onNewRequest(@NonNull Request request) {
        switch (this.seq) {
            case FIFO:
                this.handler.sendMessage(this.handler.obtainMessage(MSG_HANDLE_NEW_REQUEST, request));
                break;
            case FILO:
                this.handler.sendMessageAtFrontOfQueue(this.handler.obtainMessage(MSG_HANDLE_NEW_REQUEST, request));
                break;
        }
    }

    public boolean quit() {
        return hr.quit();
    }

    public boolean quitSafely() {
        return hr.quitSafely();
    }

    private void handleMessage(Message message) {
        this.dispatcher.dispatch((Request) message.obj);
    }
}
