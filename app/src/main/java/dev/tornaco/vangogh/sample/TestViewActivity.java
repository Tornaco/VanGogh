package dev.tornaco.vangogh.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by guohao4 on 2017/8/30.
 * Email: Tornaco@163.com
 */

public class TestViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        int e1 = extra.getInt("key1");
        boolean e2 = extra.getBoolean("key2");
        String e3 = extra.getString("key3");

        Photo photo = extra.getParcelable("key4");

        TextView textView = new TextView(this);
        textView.setText("EXTRA-" + e1 + e2 + e3+photo.getPath());

        setContentView(textView);
    }
}
