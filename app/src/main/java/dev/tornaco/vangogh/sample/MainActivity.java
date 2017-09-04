package dev.tornaco.vangogh.sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import dev.tornaco.vangogh.Vangogh;
import dev.tornaco.vangogh.VangoghConfig;
import dev.tornaco.vangogh.display.CircleImageEffect;
import dev.tornaco.vangogh.display.appliers.FadeOutFadeInApplier;
import dev.tornaco.vangogh.loader.Loader;
import dev.tornaco.vangogh.loader.LoaderObserver;
import dev.tornaco.vangogh.loader.LoaderObserverAdapter;
import dev.tornaco.vangogh.media.Image;
import dev.tornaco.vangogh.media.ImageSource;

public class MainActivity extends AppCompatActivity {

    private GridView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);

//                intent.setAction(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel://1234444"));
//                startActivity(intent);

//                intent.setAction(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("smsto:1222"));
//                startActivity(intent);

                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("key1", 12);
                intent.putExtra("key2", true);
                intent.putExtra("key3", "Hello");
                Photo photo = new Photo("XXX", "/sdcard/xxx.png");
                intent.putExtra("key4", photo);

                startActivity(intent);
            }
        });

        findViewById(R.id.button_pause)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vangogh.pause();
                    }
                });
        findViewById(R.id.button_clear)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vangogh.clearPendingRequests();
                    }
                });
        findViewById(R.id.button_resume)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Vangogh.resume();
                    }
                });

        setupView();
    }

    private void setupView() {
        listView = (GridView) findViewById(R.id.list);

        listView.setNumColumns(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Photo> photos = new PhotoLoader(getApplicationContext())
                        .load();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return photos.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return position;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                ViewHolder holder;
                                if (convertView == null) {
                                    convertView = LayoutInflater.from(getApplicationContext())
                                            .inflate(R.layout.list_item, parent, false);
                                    holder = new ViewHolder(convertView);
                                    convertView.setTag(holder);
                                } else {
                                    holder = (ViewHolder) convertView.getTag();
                                }

                                holder.getTextView().setText(photos.get(position).getName());

                                // Vangogh.linkScrollState(listView);
//
//                                holder.getImageView().setImageBitmap(null);
//                                holder.getImageView().setImageDrawable(null);

                                Vangogh.with(getApplicationContext(), VangoghConfig.builder()
                                        .context(getApplicationContext()).requestPoolSize(1)
                                        .diskCacheDir(new File(getCacheDir().getPath() + File.separator + "disk_cache"))
                                        .memCachePoolSize(64)
                                        .build())
                                        .load(photos.get(position).getPath())
                                        .effect(new CircleImageEffect())
                                        .applier(new FadeOutFadeInApplier())
                                        .skipMemoryCache(true)
                                        .skipDiskCache(true)
                                        .usingLoader(new CustomLoader())
                                        .placeHolder(R.drawable.ic_home_black_24dp)
                                        .fallback(R.mipmap.ic_launcher_round)
                                        .observer(new LoaderObserverAdapter())
                                        .into(holder.getImageView());

                                return convertView;
                            }
                        });
                    }
                });
            }
        }).start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Vangogh.cancelAllRequest(true);
    }

    static final class ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View rootView) {
            this.imageView = (ImageView) rootView.findViewById(R.id.imageView);
            this.textView = (TextView) rootView.findViewById(R.id.textView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    class CustomLoader implements Loader<Image> {

        @Nullable
        @Override
        public Image load(@NonNull ImageSource source, @Nullable LoaderObserver observer) {
            return null;
        }

        @Override
        public int priority() {
            return 3;
        }
    }
}
