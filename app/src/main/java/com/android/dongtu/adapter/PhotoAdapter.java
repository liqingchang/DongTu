package com.android.dongtu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.dongtu.R;
import com.android.dongtu.ThreadManager;
import com.android.dongtu.data.Photo;
import com.android.dongtu.data.PhotoManager;
import com.android.dongtu.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * 图片详情适配器
 * Created by kuroterry on 15/12/2.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<Photo> data;
    protected DisplayImageOptions options;

    public PhotoAdapter(List<Photo> data) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Photo photo = data.get(position);
        if (photo != null) {
            ImageLoader.getInstance().displayImage(photo.url, holder.imvPic, options, new SimpleImageLoadingListener());
            setFavorite(photo.isFavorite, holder.imvFavorite);
            holder.imvFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photo.isFavorite = !photo.isFavorite;
                    setFavorite(photo.isFavorite, holder.imvFavorite);
                    ThreadManager.runBg(new FavoriteRunnable(photo));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void setFavorite(boolean isFavorite, ImageView imvFavorite) {
        if (isFavorite) {
            imvFavorite.setImageResource(R.mipmap.ic_favorite_black_36dp);
        } else {
            imvFavorite.setImageResource(R.mipmap.ic_favorite_border_black_36dp);
        }
    }

    private class FavoriteRunnable implements Runnable {
        private Photo photo;

        public FavoriteRunnable(Photo photo) {
            this.photo = photo;
        }

        @Override
        public void run() {
            if(PhotoManager.getInstance().setFavorite(photo)){
                Logger.i("terry", "like success");
            } else {
                Logger.i("terry", "like fail");
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imvPic;
        ImageView imvFavorite;
        FrameLayout frmTool;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imvPic = (ImageView) itemView.findViewById(R.id.imv_photo);
            imvFavorite = (ImageView) itemView.findViewById(R.id.imv_favorite);
            frmTool = (FrameLayout) itemView.findViewById(R.id.frm_tool);
        }
    }
}
