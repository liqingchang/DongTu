package com.android.dongtu.data;

import java.util.List;

/**
 * Created by kuroterry on 15/11/28.
 */
public abstract class AbstractLoader {

    private static final int DEFAULT_COUNT = 8;

    public abstract Albums loadAlbumSummary(String lastId);

    public abstract Albums loadAlbumSummary(String lastId, int size);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary);

    public abstract boolean setLike(Photo photo);

    public abstract AlbumDetail loadLike();

    public int getDefaultCount(){
        return DEFAULT_COUNT;
    }

}
