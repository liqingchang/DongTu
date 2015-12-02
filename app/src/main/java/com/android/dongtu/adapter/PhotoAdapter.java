package com.android.dongtu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.dongtu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by kuroterry on 15/12/2.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    public List<String> data;
    protected DisplayImageOptions options;

    public PhotoAdapter(List<String> data) {
        this.data = data;
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
        final View view = inflater.inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = data.get(position);
        if (url != null) {
            ImageLoader.getInstance().displayImage(url, holder.imageView, options, new SimpleImageLoadingListener());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return false;
//    }

//    @override
//    public int getcount() {
//        return data.size();
//    }
//
//    @override
//    public object getitem(int i) {
//        return data.get(i);
//    }
//
//    @override
//    public long getitemid(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        Holder holder = null;
//        if (view == null) {
//            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//            view = inflater.inflate(R.layout.item_viewpager, null);
//            holder = new Holder();
//            holder.imageView = (ImageView) view;
//            view.setTag(holder);
//        } else {
//            holder = (Holder) view.getTag();
//        }
//
//        String url = data.get(i);
//        ImageLoader.getInstance().displayImage(url, holder.imageView, options, new SimpleImageLoadingListener());
//        return view;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView;
        }
    }
}
