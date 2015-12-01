package com.android.dongtu.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dongtu.R;
import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.holder.AlbumHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;

/**
 * Created by kuroterry on 15/11/15.
 */
public class AlbumSummaryAdapter extends AbstractAlbumAdapter{

    public List<AlbumSummary> data;
    private DisplayImageOptions options;

    public AlbumSummaryAdapter() {
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
    public <T> void add(List<T> data) {
        for(int i = 0 ; i < data.size(); i++) {
            this.data.add((AlbumSummary) data.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static final int sGirdImageSize = (LocalDisplay.SCREEN_WIDTH_PIXELS - LocalDisplay.dp2px(12 + 12 + 10)) / 2;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        AlbumHolder holder =null;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view  = inflater.inflate(R.layout.item_grid, null);
            holder = new AlbumHolder();
            holder.imvCover = (ImageView) view.findViewById(R.id.imv_cover);
            LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(sGirdImageSize,sGirdImageSize);
            holder.imvCover.setLayoutParams(lyp);
            holder.txvName = (TextView) view.findViewById(R.id.txv_name);
            view.setTag(holder);
        } else {
            holder = (AlbumHolder) view.getTag();
        }

        AlbumSummary albumSummary = data.get(position);
        ImageLoader.getInstance().displayImage(albumSummary.coverUrl, holder.imvCover, options, new SimpleImageLoadingListener());
        holder.txvName.setText(albumSummary.name);
        return view;
    }

}
