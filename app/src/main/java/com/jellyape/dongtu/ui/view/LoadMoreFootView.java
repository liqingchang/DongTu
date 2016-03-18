package com.jellyape.dongtu.ui.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jellyape.dongtu.R;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreUIHandler;

/**
 * Created by kuroterry on 15/11/30.
 */
public class LoadMoreFootView extends RelativeLayout implements LoadMoreUIHandler{

	private TextView txvLoading;
	private TextView txvNoMore;

	public LoadMoreFootView(Context context) {
		super(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.view_loadmore, null);
		txvLoading = (TextView) view.findViewById(R.id.txv_loading);
		txvNoMore = (TextView) view.findViewById(R.id.txv_nomore);
		addView(view);
	}

	@Override
	public void onLoading(LoadMoreContainer loadMoreContainer) {

	}

	@Override
	public void onLoadFinish(LoadMoreContainer loadMoreContainer, boolean empty, boolean hasMore) {
		if(hasMore) {
			txvLoading.setVisibility(View.VISIBLE);
			txvNoMore.setVisibility(View.GONE);
		} else {
			txvLoading.setVisibility(View.GONE);
			txvNoMore.setVisibility(View.VISIBLE);

		}
	}

	@Override
	public void onWaitToLoadMore(LoadMoreContainer loadMoreContainer) {

	}

	@Override
	public void onLoadError(LoadMoreContainer loadMoreContainer, int i, String s) {

	}
}
