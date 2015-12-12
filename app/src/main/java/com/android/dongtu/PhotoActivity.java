package com.android.dongtu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.ui.fragment.PhotoFragment;

/**
 * Created by kuroterry on 15/12/3.
 */
public class PhotoActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_DETAIL = "key_detail";
    public static final String KEY_POSITION = "key_position";

    private FrameLayout frmTool;
    private ImageView imvFavorite;
    private boolean isFavorite = false;

    @Override
    void init() {
        setContentView(R.layout.activity_photo);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            AlbumDetail albumDetail = (AlbumDetail) bundle.getSerializable(KEY_DETAIL);
            int position = bundle.getInt(KEY_POSITION);
            if (albumDetail != null) {
                Fragment fragment = PhotoFragment.instance(albumDetail, position);
                addFragment(fragment, true);
            }
        }

        frmTool = (FrameLayout) findViewById(R.id.frm_tool);
        imvFavorite = (ImageView) findViewById(R.id.imv_favorite);
        imvFavorite.setOnClickListener(this);
    }

    @Override
    int getContainerRes() {
        return R.id.frm_container;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_favorite:
                if (isFavorite) {
                    imvFavorite.setImageResource(R.mipmap.ic_favorite_border_black_36dp);
                } else {
                    imvFavorite.setImageResource(R.mipmap.ic_favorite_black_36dp);
                }
                isFavorite = !isFavorite;
                break;
        }
    }
}
