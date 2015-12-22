package com.android.dongtu.ui.fragment;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.PhotoManager;

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
    public AlbumDetail initAlbum() {
        return PhotoManager.getInstance().getAllLike();
    }

}
