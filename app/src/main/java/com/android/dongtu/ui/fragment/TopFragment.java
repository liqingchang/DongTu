package com.android.dongtu.ui.fragment;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.PhotoManager;

/**
 * 收藏页面
 * Created by kuroterry on 15/12/12.
 */
public class TopFragment extends AbstractDetailFragment{

    public static final String TAG = TopFragment.class.getSimpleName();

    public static TopFragment instance() {
        TopFragment fragment = new TopFragment();
        return fragment;
    }

    @Override
    public AlbumDetail loadAlbum(int skip, int max) {
        return PhotoManager.getInstance().getRanking(skip, max);
    }

}
