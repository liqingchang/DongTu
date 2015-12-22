package com.android.dongtu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.android.dongtu.PhotoActivity;
import com.android.dongtu.R;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.adapter.AlbumDetailAdapter;
import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.Photo;

import java.util.List;

/**
 * 排列显示单张图片抽象类
 * Created by kuroterry on 15/12/22.
 */
public abstract class AbstractDetailFragment extends AbstractAlbumFragment {
    public static final String TAG = AbstractDetailFragment.class.getSimpleName();

    protected AlbumDetail albumDetail;

    public abstract AlbumDetail initAlbum();

    @Override
    public Object load() {
        if(albumDetail == null) {
            albumDetail = initAlbum();
            return albumDetail.getPics(0);
        } else {
            return albumDetail.getPics(albumDetail.getPosition());
        }
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
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                        Pair.create(view.findViewById(R.id.imv_cover), transitionName)
//                );
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoActivity.KEY_DETAIL, albumDetail);
                bundle.putInt(PhotoActivity.KEY_POSITION, position);
                intent.putExtras(bundle);
//                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                startActivity(intent);
            }
        });

    }

    @Override
    public void loadMore(Message message) {
        List<Photo> data = (List<Photo>) message.obj;
        if(data.size() > 0) {
            adapter.add(data);
        }
    }
}
