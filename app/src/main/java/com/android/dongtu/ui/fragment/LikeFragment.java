package com.android.dongtu.ui.fragment;

import android.os.Bundle;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.BaseLoader;
import com.android.dongtu.data.IObserver;
import com.android.dongtu.data.PhotoManager;

/**
 * &#x6536;&#x85cf;&#x9875;&#x9762;
 * Created by kuroterry on 15/12/12.
 */
public class LikeFragment extends AbstractDetailFragment implements IObserver {

    public static final String TAG = LikeFragment.class.getSimpleName();

    public static LikeFragment instance() {
        LikeFragment fragment = new LikeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (abstractLoader != null) {
            abstractLoader.addObserver(this);
        }
    }

    @Override
    public void onDestroy() {
        if (abstractLoader != null) {
            abstractLoader.removeObserver(this);
        }
        super.onDestroy();
    }

    @Override
    public AlbumDetail loadAlbum(int skip, int max) {
        return PhotoManager.getInstance().getAllLike(skip, max);
    }

    @Override
    public void update() {
        albumDetail = null;
        reload();
    }
}
