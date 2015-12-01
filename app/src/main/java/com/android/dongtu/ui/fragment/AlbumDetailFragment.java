package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.android.dongtu.adapter.AlbumDetailAdapter;
import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.AlbumSummary;

import java.util.List;

/**
 * Created by kuroterry on 15/11/30.
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
        return iLoader.loadAlbumDetail(albumSummary);
    }

    @Override
    public void initAdapter() {
        adapter = new AlbumDetailAdapter();
    }

    @Override
    public void afterInit(View view) {
        grvPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void loadMore(Message message) {
        AlbumDetail albumDetail = (AlbumDetail) message.obj;
        List<String> data = albumDetail.getPics();
        adapter.add(data);
        loadMoreContainer.loadMoreFinish(true, false);
    }
}
