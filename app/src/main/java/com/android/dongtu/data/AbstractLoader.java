package com.android.dongtu.data;

/**
 * Created by kuroterry on 15/11/28.
 */
public abstract class AbstractLoader {

    private static final int DEFAULT_COUNT = 8;

    public abstract Albums loadAlbumSummary(String lastId);

    public abstract Albums loadAlbumSummary(String lastId, int size);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary);

    public int getDefaultCount(){
        return DEFAULT_COUNT;
    }

}
