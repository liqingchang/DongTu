package com.jellyape.dongtushe.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jellyape.dongtushe.R;
import com.jellyape.dongtushe.adapter.PhotoAdapter;
import com.jellyape.dongtushe.data.AlbumDetail;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 图片详情页面
 * Created by kuroterry on 15/12/2.
 */
public class PhotoFragment extends Fragment {

    private static final String ARG_DATA = "arg_data";
    private static final String ARG_POSITION = "arg_position";

    private RecyclerViewPager viewPager;
    private PhotoAdapter adapter;
    private AlbumDetail data;
    private int position;
    protected DisplayImageOptions options;


    public static PhotoFragment instance(AlbumDetail data, int position) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (args != null) {
            data = (AlbumDetail) args.getSerializable(ARG_DATA);
            position = args.getInt(ARG_POSITION);
        }
        adapter = new PhotoAdapter(context, data.getAllPics());
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, null);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(View view) {
        viewPager = (RecyclerViewPager) view.findViewById(R.id.viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        viewPager.setLayoutManager(layout);
        viewPager.setAdapter(new RecyclerViewPagerAdapter(viewPager, adapter));
        viewPager.scrollToPosition(position);
    }
}
