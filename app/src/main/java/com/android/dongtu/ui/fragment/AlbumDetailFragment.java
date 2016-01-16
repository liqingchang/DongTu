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
    public AlbumDetail loadAlbum(int skip, int max) {
        return abstractLoader.loadAlbumDetail(albumSummary);
    }

    @Override
    public Object load() {
        if (albumDetail == null) {
            albumDetail = loadAlbum(0, abstractLoader.getDefaultCount());
            return albumDetail.getPics(0);
        } else {
            return albumDetail.getPics(albumDetail.getPosition());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            albumSummary = (AlbumSummary) bundle.getSerializable(ARG_ALBUMSUMMARY);
        }
    }

}
