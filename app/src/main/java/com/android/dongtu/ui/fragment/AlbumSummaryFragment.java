package com.android.dongtu.ui.fragment;

import android.os.Message;
import android.view.View;

import com.android.dongtu.MainActivity;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.adapter.AlbumSummaryAdapter;
import com.android.dongtu.data.AlbumSummary;

import java.util.List;

/**
 * Created by kuroterry on 15/12/2.
 */
public class AlbumSummaryFragment extends AbstractAlbumFragment {

    @Override
    public Object load() {
        isLoading = true;
        Object object = abstractLoader.loadAlbumSummary();
        isLoading = false;
        return object;
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
                callback.onFragmentCallback(MainActivity.MAIN_ALBUMDETAIL,view,  ((List<AlbumSummary>) adapter.getData()).get(position));
            }
        });
    }

    @Override
    public void loadMore(Message message) {
        List<AlbumSummary> data = (List<AlbumSummary>) message.obj;
        adapter.add(data);
    }

}
