package com.android.dongtu.data;

import java.util.List;

/**
 * Created by kuroterry on 15/11/28.
 */
public interface ILoader {

    public List<AlbumSummary> loadAlbumSummary();

    public List<AlbumSummary> loadAlbumSummary(int size);

    public AlbumDetail loadAlbumDetail(AlbumSummary albumSummary);

}
