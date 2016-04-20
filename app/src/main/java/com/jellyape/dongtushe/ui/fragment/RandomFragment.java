package com.jellyape.dongtushe.ui.fragment;

import com.jellyape.dongtushe.data.AlbumDetail;
import com.jellyape.dongtushe.data.PhotoManager;

/**
 * 收藏页面
 * Created by kuroterry on 15/12/12.
 */
public class RandomFragment extends AbstractDetailFragment{

    public static final String TAG = RandomFragment.class.getSimpleName();

    public static RandomFragment instance() {
        RandomFragment fragment = new RandomFragment();
        return fragment;
    }

    @Override
    public AlbumDetail loadAlbum(int skip, int max) {
        return PhotoManager.getInstance().getRandom(skip, max);
    }

}
