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
import com.android.dongtu.data.PhotoManager;

import java.util.List;

/**
 * 收藏页面
 * Created by kuroterry on 15/12/12.
 */
public class LikeFragment extends AbstractAlbumFragment{

    public static final String TAG = LikeFragment.class.getSimpleName();

    private AlbumDetail albumDetail;

    public static LikeFragment instance() {
        LikeFragment fragment = new LikeFragment();
        return fragment;
    }

    @Override
    public Object load() {
        if(albumDetail == null) {
            albumDetail = PhotoManager.getInstance().getAllLike();
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
