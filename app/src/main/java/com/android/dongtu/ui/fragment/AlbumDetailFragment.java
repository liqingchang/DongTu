package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.android.dongtu.PhotoActivity;
import com.android.dongtu.R;
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
                String transitionName = getString(R.string.transition_photo);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(view.findViewById(R.id.imv_cover), transitionName)
                );
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                Bundle bundle = new Bundle();
                AlbumDetail albumDetail = new AlbumDetail();
                albumDetail.pics = (List<String>) adapter.getData();
                bundle.putSerializable(PhotoActivity.KEY_DETAIL, albumDetail);
                intent.putExtras(bundle);
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
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
