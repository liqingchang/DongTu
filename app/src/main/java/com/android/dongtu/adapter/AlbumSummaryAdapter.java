package com.android.dongtu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.dongtu.R;
import com.android.dongtu.data.AlbumSummary;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;

/**
 * Created by kuroterry on 15/12/2.
 */
public class AlbumSummaryAdapter extends AbstractAlbumAdapter {

    public List<AlbumSummary> data;
    private DisplayImageOptions options;
    private OnGetViewListener onGetViewListener;
    public static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;

    public AlbumSummaryAdapter() {
        data = new ArrayList<AlbumSummary>();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_gray)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
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
            ImageLoader.getInstance().displayImage(albumSummary.coverUrl, holder.pic, options, new SimpleImageLoadingListener());
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
        notifyDataSetChanged();
    }

    public interface OnGetViewListener {
        void onBindView(int position);
    }

    public void setOnGetViewListener(OnGetViewListener onGetViewListener) {
        this.onGetViewListener = onGetViewListener;
    }

}
