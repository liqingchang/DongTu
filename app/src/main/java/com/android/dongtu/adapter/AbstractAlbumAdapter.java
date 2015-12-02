package com.android.dongtu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dongtu.R;

import java.util.List;

/**
 * Created by kuroterry on 15/11/30.
 */
public abstract class AbstractAlbumAdapter extends RecyclerView.Adapter<AbstractAlbumAdapter.ViewHolder> {

    public abstract <T> void add(List<T> data);

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView pic;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            pic = (ImageView) itemView.findViewById(R.id.imv_cover);
            name = (TextView) itemView.findViewById(R.id.txv_name);
        }
    }
}
