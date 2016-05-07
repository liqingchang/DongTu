package com.jellyape.dongtushe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jellyape.dongtushe.R;
import com.jellyape.dongtushe.ThreadManager;
import com.jellyape.dongtushe.data.Photo;
import com.jellyape.dongtushe.data.PhotoManager;
import com.jellyape.dongtushe.util.Logger;
import com.jellyape.dongtushe.util.ShareUtil;
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
    private Context context;

    public PhotoAdapter(Context context, List<Photo> data) {
        this.context = context;
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
            setFavorite(photo.isFavorite, holder.imbFavorite);
            holder.imbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photo.isFavorite = !photo.isFavorite;
                    setFavorite(photo.isFavorite, holder.imbFavorite);
                    ThreadManager.runBg(new FavoriteRunnable(photo));
                }
            });
            holder.imvPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.frmTool.getVisibility() == View.VISIBLE) {
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
            holder.imbShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeMessages(MSG_HIDETOOL);
                    ShareUtil.shareCache(context, "", photo.url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void setFavorite(boolean isFavorite, ImageButton imbFavorite) {
        if (isFavorite) {
            imbFavorite.setPressed(true);
//            imbFavorite.setImageResource(R.mipmap.ic_favorite_white_36dp);
        } else {
            imbFavorite.setPressed(false);
//            imbFavorite.setImageResource(R.mipmap.ic_favorite_border_white_36dp);
        }
    }

    private class FavoriteRunnable implements Runnable {
        private Photo photo;

        public FavoriteRunnable(Photo photo) {
            this.photo = photo;
        }

        @Override
        public void run() {
            if (PhotoManager.getInstance().setFavorite(photo)) {
                Logger.i("terry", "like success");
            } else {
                Logger.i("terry", "like fail");
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imvPic;
        ImageButton imbFavorite;
        ImageButton imbShare;
        LinearLayout frmTool;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imvPic = (ImageView) itemView.findViewById(R.id.imv_photo);
            imbFavorite = (ImageButton) itemView.findViewById(R.id.imb_favorite);
            imbShare = (ImageButton) itemView.findViewById(R.id.imb_share);
            frmTool = (LinearLayout) itemView.findViewById(R.id.frm_tool);
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
            if (photoAdapter != null) {
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
