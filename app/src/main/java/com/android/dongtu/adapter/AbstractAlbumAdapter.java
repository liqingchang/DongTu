package com.android.dongtu.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by kuroterry on 15/11/30.
 */
public abstract class AbstractAlbumAdapter extends BaseAdapter {

    public abstract <T> void add(List<T> data);

}
