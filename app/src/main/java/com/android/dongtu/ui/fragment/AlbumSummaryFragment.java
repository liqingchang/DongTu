package com.android.dongtu.ui.fragment;

import android.os.Message;
import android.view.View;

import com.android.dongtu.MainActivity;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.adapter.AlbumSummaryAdapter;
import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.data.Albums;

import java.util.List;

/**
 * 专辑索引页面
 * Created by kuroterry on 15/12/2.
 */
public class AlbumSummaryFragment extends AbstractAlbumFragment {

    private Albums albums;

    public static AlbumSummaryFragment instance(){
        AlbumSummaryFragment fragment = new AlbumSummaryFragment();
        return fragment;
    }

    @Override
    public void initAdapter() {
        adapter = new AlbumSummaryAdapter();
    }

    @Override
    public void afterInit(View view) {
        adapter.setOnItemClickListener(new AbstractAlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callback.onFragmentCallback(MainActivity.MAIN_ALBUMDETAIL, view, ((List<AlbumSummary>) adapter.getData()).get(position));
            }
        });
//        adapter.setOnGetViewListener(new AlbumSummaryAdapter.OnGetViewListener() {
//            @Override
//            public void onBindView(int position) {
//                getMoreImagesIfNeeded(position, adapter.getItemCount());
//            }
//        });
    }

    @Override
    public Object load() {
        Albums loadAlbums;
        if(albums == null) {
            albums = abstractLoader.loadAlbumSummary(0, abstractLoader.getDefaultCount());
            loadAlbums = albums;
        } else {
            loadAlbums = abstractLoader.loadAlbumSummary(albums.getSize(), abstractLoader.getDefaultCount());
            albums.addAllAlbums(loadAlbums);
        }
        return loadAlbums;
    }

    @Override
    public void loadMore(Message message) {
        if (message.obj != null) {
            int startPosition = adapter.getItemCount();
            adapter.setData(albums.getAllAlbumSummaris());
            Albums loadAlbums = (Albums) message.obj;
            adapter.notifyItemRangeInserted(startPosition, loadAlbums.getSize());
        }
    }

}
