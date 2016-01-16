package com.android.dongtu.ui.fragment;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.PhotoManager;

/**
 * &#x6536;&#x85cf;&#x9875;&#x9762;
 * Created by kuroterry on 15/12/12.
 */
public class LikeFragment extends AbstractDetailFragment{

    public static final String TAG = LikeFragment.class.getSimpleName();

    public static LikeFragment instance() {
        LikeFragment fragment = new LikeFragment();
        return fragment;
    }

    @Override
    public AlbumDetail loadAlbum(int skip, int max) {
        return PhotoManager.getInstance().getAllLike(skip, max);
    }

}
