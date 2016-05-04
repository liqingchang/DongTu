package com.android.dongtu.data;

import java.util.List;

/**
 * Created by kuroterry on 15/11/28.
 */
public abstract class AbstractLoader {

    private static final int DEFAULT_COUNT = 8;

    public abstract List<AlbumSummary> loadAlbumSummary();

    public abstract List<AlbumSummary> loadAlbumSummary(int size);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary);

    public int getDefaultCount(){
        return DEFAULT_COUNT;
    }

}
