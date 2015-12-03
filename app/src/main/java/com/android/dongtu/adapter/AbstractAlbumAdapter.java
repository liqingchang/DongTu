package com.android.dongtu.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dongtu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by kuroterry on 15/11/30.
 */
public abstract class AbstractAlbumAdapter extends RecyclerView.Adapter<AbstractAlbumAdapter.ViewHolder> {

    public abstract <T> void add(List<T> data);
    public abstract Object getData();

    protected DisplayImageOptions options;
    protected OnGetViewListener onGetViewListener;
    protected OnItemClickListener onItemClickListener;


    public AbstractAlbumAdapter() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView pic;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = (ImageView) itemView.findViewById(R.id.imv_cover);
            name = (TextView) itemView.findViewById(R.id.txv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnGetViewListener {
        void onBindView(int position);
    }

    public void setOnGetViewListener(OnGetViewListener onGetViewListener) {
        this.onGetViewListener = onGetViewListener;
    }

    public interface OnItemClickListener {
        abstract void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
