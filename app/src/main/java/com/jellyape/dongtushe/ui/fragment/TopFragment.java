package com.jellyape.dongtushe.ui.fragment;

import com.jellyape.dongtushe.data.AlbumDetail;
import com.jellyape.dongtushe.data.PhotoManager;

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
