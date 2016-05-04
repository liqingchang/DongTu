package com.android.dongtu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.dongtu.FragmentCallback;
import com.android.dongtu.R;
import com.android.dongtu.ThreadManager;
import com.android.dongtu.adapter.AbstractAlbumAdapter;
import com.android.dongtu.data.BaseLoader;
import com.android.dongtu.data.ILoader;
import com.android.dongtu.ui.view.LoadMoreFootView;

import java.lang.ref.WeakReference;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 下拉刷新 底部加载 动态加载图片
 * Created by kuroterry on 15/11/15.
 */
public abstract class AbstractAlbumFragment extends Fragment {

    private static final int MSG_LOADMORE = 1;

    private PtrClassicFrameLayout ptrFrameLayout;
    protected GridViewWithHeaderAndFooter grvPics;
    protected LoadMoreContainer loadMoreContainer;
    protected AbstractAlbumAdapter adapter;
    protected ILoader iLoader;
    private AlbumFragmentHandler handler;
    private LoadMoreFootView loadMoreFootView;
    protected FragmentCallback callback;

    public abstract Object load();

    public abstract void initAdapter();

    /**
     * 主要用于实现不同的点击时间
     *
     * @param view
     */
    public abstract void afterInit(View view);

    public abstract void loadMore(Message message);

    private Runnable loadMoreRunnable = new Runnable() {
        @Override
        public void run() {
            Message message = handler.obtainMessage();
            message.obj = load();
            message.what = MSG_LOADMORE;
            handler.sendMessage(message);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
           callback = (FragmentCallback)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentCallback");
        }
        LocalDisplay.init(context);
        iLoader = new BaseLoader();
        initAdapter();
        handler = new AlbumFragmentHandler(this);
        loadMoreFootView = new LoadMoreFootView(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, null);
        init(view);
        return view;
    }

    private void init(View view) {
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_albumcover);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, grvPics, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 500);
            }
        });

        loadMoreContainer = (LoadMoreContainer) view.findViewById(R.id.loadmore_container);
        loadMoreContainer.setLoadMoreView(loadMoreFootView);
        loadMoreContainer.setLoadMoreUIHandler(loadMoreFootView);
        loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                ThreadManager.runBg(loadMoreRunnable);
            }
        });
        // 参数1表示列表是否为空 参数2表示是否有更多内容 基本上就参数2有用
        loadMoreContainer.loadMoreFinish(false, true);

        grvPics = (GridViewWithHeaderAndFooter) view.findViewById(R.id.grv_albumcover);
        grvPics.setAdapter(adapter);
        afterInit(view);

        // 加载一次数据
        ThreadManager.runBg(loadMoreRunnable);

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
