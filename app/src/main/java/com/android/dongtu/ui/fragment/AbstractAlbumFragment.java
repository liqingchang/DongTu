package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dongtu.FragmentCallback;
import com.android.dongtu.GridSpacingItemDecoration;
import com.android.dongtu.R;
import com.android.dongtu.ThreadManager;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.adapter.AlbumSummaryAdapter;
import com.android.dongtu.data.AbstractLoader;
import com.android.dongtu.data.BaseLoader;
import com.android.dongtu.util.Logger;

import java.lang.ref.WeakReference;

import in.srain.cube.util.LocalDisplay;

/**
 * Material Design风格的GridView显示图片尝试
 * Created by kuroterry on 15/12/2.
 */
public abstract class AbstractAlbumFragment extends Fragment {

    private static final int MSG_LOADMORE = 1;

    protected RecyclerView revAlbum;
    protected AbstractAlbumAdapter adapter;
    protected AbstractLoader abstractLoader;
    protected FragmentCallback callback;
    private AlbumFragmentHandler handler;
    private GridLayoutManager gridLayoutManager;
    private float space;

    private boolean isActive = false;

    protected boolean isNoMore= false;

    /**
     * 是否加载中,避免多次加载
     */
    protected boolean isLoading;
    private Runnable loadMoreRunnable = new Runnable() {
        @Override
        public void run() {
            isLoading = true;
            Message message = handler.obtainMessage();
            message.obj = load();
            isLoading = false;
            message.what = MSG_LOADMORE;
            handler.sendMessage(message);
        }
    };

    public abstract Object load();

    public abstract void initAdapter();

    /**
     * 主要用于实现不同的点击时间
     *
     * @param view
     */
    public abstract void afterInit(View view);

    public abstract void loadMore(Message message);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (FragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentCallback");
        }
        LocalDisplay.init(context);
        abstractLoader = new BaseLoader();
        initAdapter();
        handler = new AlbumFragmentHandler(this);
        space = context.getResources().getDimension(R.dimen.grid_space);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pics, null);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
        ThreadManager.runBg(loadMoreRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        isActive = false;
    }

    private void init(View view) {
        gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        revAlbum = (RecyclerView) view.findViewById(R.id.rev_pics);
        adapter.setOnGetViewListener(new AlbumSummaryAdapter.OnGetViewListener() {
            @Override
            public void onBindView(int position) {
                Logger.i("terry", "onBindView position:" + position + " | total:" + adapter.getItemCount());
                getMoreImagesIfNeeded(position, adapter.getItemCount());
            }
        });
        revAlbum.setAdapter(adapter);
        revAlbum.setLayoutManager(gridLayoutManager);
        revAlbum.addItemDecoration(new GridSpacingItemDecoration(2, (int) space, false));
        afterInit(view);
        // 加载一次数据
        ThreadManager.runBg(loadMoreRunnable);
    }

    int count;
    private int lastTotalCount = 0;

    /**
     * 判断是否需要加载更多数据
     */
    protected void getMoreImagesIfNeeded(int position, int totalItemCount) {
        int defaultNumberOfItemsPerPage = abstractLoader.getDefaultCount();
        boolean shouldLoadMore = position >= totalItemCount - (defaultNumberOfItemsPerPage / 2);
        if (shouldLoadMore && !isLoading && adapter != null && adapter.getItemCount() > 0 && isActive && !isNoMore && totalItemCount != lastTotalCount) {
            Logger.i("terry", "LOAD MORE AT " + getClass().getSimpleName() + " thread count:" + ++count + " | position:" + position + " totalItemCount:" + totalItemCount);
            lastTotalCount = totalItemCount;
            // 获取更多数据
            ThreadManager.runBg(loadMoreRunnable);
        }
    }

    private static class AlbumFragmentHandler extends Handler {
        private WeakReference<AbstractAlbumFragment> fragmentWeakReference;

        public AlbumFragmentHandler(AbstractAlbumFragment fragment) {
            fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AbstractAlbumFragment fragment = fragmentWeakReference.get();
            if (fragment != null) {
                switch (msg.what) {
                    case MSG_LOADMORE:
                        fragment.loadMore(msg);
                        break;
                }
            }
        }
    }
}
