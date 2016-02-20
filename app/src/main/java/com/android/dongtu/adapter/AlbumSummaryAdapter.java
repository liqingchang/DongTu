package com.android.dongtu.adapter;

import android.widget.LinearLayout;

import com.android.dongtu.data.AlbumSummary;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;

/**
 * 专辑索引适配器
 * Created by kuroterry on 15/12/2.
 */
public class AlbumSummaryAdapter extends AbstractAlbumAdapter {

    public List<AlbumSummary> data;
    public static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;

    public AlbumSummaryAdapter() {
        super();
        data = new ArrayList<AlbumSummary>();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(onGetViewListener != null) {
            onGetViewListener.onBindView(position);
        }
        AlbumSummary albumSummary = data.get(position);
        if (albumSummary != null) {
            LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize,sGirdImageSize);
            holder.itemView.setLayoutParams(lyp);
            // 封面使用缩略图增加速度
            ImageLoader.getInstance().displayImage(albumSummary.coverUrl+"?imageView2/1/w/720/q/50", holder.pic, options, new SimpleImageLoadingListener());
            holder.name.setText(albumSummary.name);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public <T> void add(List<T> data) {
        for(int i = 0 ; i < data.size(); i++) {
            this.data.add((AlbumSummary) data.get(i));
        }
    }

    @Override
    public <T> void setData(List<T> data) {
        this.data = (List<AlbumSummary>)data;
    }

    @Override
    public Object getData() {
        return data;
    }


}
