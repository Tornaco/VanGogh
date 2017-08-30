package dev.tornaco.vangogh.sample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import dev.tornaco.vangogh.Vangogh;
import dev.tornaco.vangogh.display.CircleImageEffect;
import dev.tornaco.vangogh.display.appliers.FadeInApplier;
import dev.tornaco.vangogh.display.appliers.ScaleInBottomApplier;
import dev.tornaco.vangogh.display.appliers.ScaleInXYApplier;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

//                                Vangogh.linkScrollState(listView);
//
//                                holder.getImageView().setImageBitmap(null);
//                                holder.getImageView().setImageDrawable(null);

                                Vangogh.with(getApplicationContext())
                                        .load(photos.get(position).getPath())
                                        .effect(new CircleImageEffect())
                                        .applier(new ScaleInXYApplier())
                                        .skipMemoryCache(false)
                                        .skipDiskCache(false)
                                        .placeHolder(0)
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
    }

    static final class ViewHolder {
        private ImageView imageView;

        public ViewHolder(View rootView) {
            this.imageView = (ImageView) rootView.findViewById(R.id.imageView);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

}
