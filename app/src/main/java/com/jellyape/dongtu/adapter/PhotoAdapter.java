package com.jellyape.dongtu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jellyape.dongtu.R;
import com.jellyape.dongtu.ThreadManager;
import com.jellyape.dongtu.data.Photo;
import com.jellyape.dongtu.data.PhotoManager;
import com.jellyape.dongtu.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 图片详情适配器
 * Created by kuroterry on 15/12/2.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static final int MSG_HIDETOOL = 0;
    private static final int HIDETIME = 3000;

    private List<Photo> data;
    protected DisplayImageOptions options;
    private PhotoAdapterHandler handler;

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
        handler = new PhotoAdapterHandler(this);
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
            holder.imvPic.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(holder.frmTool.getVisibility() == View.VISIBLE) {
                        handler.removeMessages(MSG_HIDETOOL);
                    } else {
                        holder.frmTool.setVisibility(View.VISIBLE);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = holder.frmTool;
                    message.what = MSG_HIDETOOL;
                    handler.sendMessageDelayed(message, HIDETIME);
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
            imvFavorite.setImageResource(R.mipmap.ic_favorite_white_36dp);
        } else {
            imvFavorite.setImageResource(R.mipmap.ic_favorite_border_white_36dp);
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

    private static class PhotoAdapterHandler extends Handler {
        private WeakReference<PhotoAdapter> adapterWeakReference;

        public PhotoAdapterHandler(PhotoAdapter photoAdapter) {
            adapterWeakReference = new WeakReference<>(photoAdapter);
        }

        @Override
        public void handleMessage(Message msg) {
            PhotoAdapter photoAdapter = adapterWeakReference.get();
            if(photoAdapter != null) {
                switch (msg.what) {
                    case MSG_HIDETOOL:
                        FrameLayout tool = (FrameLayout) msg.obj;
                        tool.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }



}
