package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.android.dongtu.MainActivity;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.adapter.AlbumDetailAdapter;
import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.AlbumSummary;

import java.util.List;

/**
 * Created by kuroterry on 15/12/2.
 */
public class AlbumDetailFragment extends AbstractAlbumFragment {

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
    public Object load() {
        isLoading = true;
        Object object = abstractLoader.loadAlbumDetail(albumSummary);
        isLoading = false;
        return object;
    }

    @Override
    public void initAdapter() {
        adapter = new AlbumDetailAdapter();
    }

    @Override
    public void afterInit(View view) {
        adapter.setOnItemClickListener(new AbstractAlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callback.onFragmentCallback(MainActivity.MAIN_ALBUMPHOTO, adapter.getData());
            }
        });

    }

    @Override
    public void loadMore(Message message) {
        AlbumDetail albumDetail = (AlbumDetail) message.obj;
        List<String> data = albumDetail.getPics();
        adapter.add(data);
    }

}
