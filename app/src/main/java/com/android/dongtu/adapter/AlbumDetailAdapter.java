package com.android.dongtu.adapter;

import android.graphics.Bitmap;
import android.widget.LinearLayout;

import com.android.dongtu.R;
import com.android.dongtu.data.Photo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;

/**
 * Created by kuroterry on 15/12/2.
 */
public class AlbumDetailAdapter extends AbstractAlbumAdapter {

    public List<Photo> data;
    private DisplayImageOptions options;
    public static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;

    public AlbumDetailAdapter() {
        data = new ArrayList<>();
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(onGetViewListener != null) {
            onGetViewListener.onBindView(position);
        }
        String url = data.get(position).url;
        if (url != null) {
            LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize,sGirdImageSize);
            holder.itemView.setLayoutParams(lyp);
            ImageLoader.getInstance().displayImage(url+"!thumb", holder.pic, options, new SimpleImageLoadingListener());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public <T> void add(List<T> data) {
        for(int i = 0 ; i < data.size(); i++) {
            this.data.add((Photo) data.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public <T> void setData(List<T> data) {
        this.data = (List<Photo>)data;
    }

    @Override
    public Object getData() {
        return data;
    }

    public interface OnGetViewListener {
        void onBindView(int position);
    }

}
