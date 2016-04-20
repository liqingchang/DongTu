package com.jellyape.dongtushe.data;

/**
 * Created by kuroterry on 15/11/28.
 */
public abstract class AbstractLoader implements ISubject{

    private static final int DEFAULT_COUNT = 8;

    public abstract Albums loadAlbumSummary(int skip, int max);

    public abstract Albums loadAlbumSummary(int skip);

    public abstract Albums loadAlbumSummary();

    public abstract Albums loadAlbumSummary(String lastId);

    public abstract Albums loadAlbumSummary(String lastId, int size);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary, int skip, int max);

    public abstract AlbumDetail loadAlbumDetail(AlbumSummary albumSummary, int skip);

    public abstract boolean setLike(Photo photo);

    public abstract AlbumDetail loadLike();

    public abstract AlbumDetail loadLike(int skip, int max);

    public abstract AlbumDetail loadRandom();

    public abstract AlbumDetail loadRandom(int skip, int max);

    public abstract AlbumDetail loadTop();

    public abstract AlbumDetail loadTop(int skip, int max);

    public int getDefaultCount(){
        return DEFAULT_COUNT;
    }

}
