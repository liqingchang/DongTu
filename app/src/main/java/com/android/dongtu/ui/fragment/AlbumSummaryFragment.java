package com.android.dongtu.ui.fragment;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.android.dongtu.MainActivity;
import com.android.dongtu.adapter.AlbumSummaryAdapter;
import com.android.dongtu.data.AlbumSummary;

import java.util.List;

/**
 * 下拉刷新 底部加载 动态加载图片
 * Created by kuroterry on 15/11/15.
 */
public class AlbumSummaryFragment extends AbstractAlbumFragment {

    @Override
    public Object load() {
        return iLoader.loadAlbumSummary();
    }

    @Override
    public void initAdapter() {
        adapter = new AlbumSummaryAdapter();
    }

    @Override
    public void afterInit(View view) {
        grvPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callback.onFragmentCallback(MainActivity.MAIN_ALBUMDETAIL, adapter.getItem(i));
            }
        });
    }

    @Override
    public void loadMore(Message message) {
        List<AlbumSummary> data = (List<AlbumSummary>) message.obj;
        if (data == null || data.size() == 0) {
            loadMoreContainer.loadMoreFinish(true, false);
        } else {
            adapter.add(data);
            loadMoreContainer.loadMoreFinish(true, true);
        }

    }

}
