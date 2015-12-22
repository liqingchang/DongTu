package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.AlbumSummary;

/**
 * 专辑详情页面
 * Created by kuroterry on 15/12/2.
 */
public class AlbumDetailFragment extends AbstractDetailFragment {

    private static final String ARG_ALBUMSUMMARY = "arg_albumsummary";

    private AlbumSummary albumSummary;

    public static AlbumDetailFragment instance(AlbumSummary albumSummary) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ALBUMSUMMARY, albumSummary);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            albumSummary = (AlbumSummary) bundle.getSerializable(ARG_ALBUMSUMMARY);
        }
    }

    @Override
    public AlbumDetail initAlbum() {
        return abstractLoader.loadAlbumDetail(albumSummary);
    }

}
