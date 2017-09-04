package dev.tornaco.vangogh.sample;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by guohao4 on 2017/8/29.
 * Email: Tornaco@163.com
 */
@AllArgsConstructor
public class PhotoLoader {

    @Getter
    private Context context;

    public List<Photo> load() {
        final List<Photo> records = new ArrayList<>();
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE};
        String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
        String[] selectionArgs = {"image/jpeg"};
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
        consumeCursor(createCursor(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder),
                new Consumer<Cursor>() {
                    @Override
                    public void accept(@NonNull Cursor cursor) {
                        Photo record = recordFromCursor(cursor);
                        if (record != null) records.add(record);
                    }
                });
        records.addAll(loadVideos());
        return records;
    }

    public List<Photo> loadVideos() {
        final List<Photo> records = new ArrayList<>();
        consumeCursor(createCursor(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Video.Media.DATE_MODIFIED + " desc"), new Consumer<Cursor>() {
            @Override
            public void accept(@NonNull Cursor cursor) {
                Photo record = recordVideoFromCursor(cursor);
                if (record != null) records.add(record);
            }
        });
        return records;
    }

    Cursor createCursor(@NonNull Uri uri,
                        @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        ContentResolver cr = getContext().getContentResolver();
        return cr.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    void consumeCursor(Cursor cursor, Consumer<Cursor> cursorConsumer) {
        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                    .moveToNext()) {
                cursorConsumer.accept(cursor);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private Photo recordVideoFromCursor(Cursor cursor) {

        Photo record = new Photo();

        int id = cursor.getInt(cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
        String title = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
        String album = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
        String artist = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
        String displayName = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
        String mimeType = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
        String path = cursor
                .getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        long duration = cursor
                .getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
        long size = cursor
                .getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

        record.setPath(path);
        record.setName(new File(path).getName()); // Fix file name.
        return record;
    }


    private Photo recordFromCursor(Cursor cursor) {
        Photo r = new Photo();
        r.setName(cursor.getString(cursor
                .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
        r.setPath(cursor.getString(cursor
                .getColumnIndex(MediaStore.Images.Media.DATA)));
        return r;
    }
}
