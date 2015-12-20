package com.android.dongtu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.ui.fragment.AlbumDetailFragment;
import com.android.dongtu.ui.fragment.AlbumSummaryFragment;
import com.android.dongtu.ui.fragment.LikeFragment;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;

public class MainActivity extends BaseActivity implements FragmentCallback,View.OnClickListener {

    public static final int MAIN_ALBUMDETAIL = 0;

    private Toolbar toolbar;
    public MaterialMenuView homeButton;
    public ImageView imvLike;
    private MaterialMenuDrawable.IconState currentIconState;

    @Override
    void init() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.dra);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        homeButton = (MaterialMenuView) findViewById(R.id.tool_billboard);
        homeButton.setOnClickListener(this);
        imvLike = (ImageView) findViewById(R.id.tool_like);
        imvLike.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        addFragment(new AlbumSummaryFragment(), true);
    }

    @Override
    public void onBackPressed() {
        if(currentIconState == MaterialMenuDrawable.IconState.ARROW) {
            // 还原动画
            setHomeIcon(MaterialMenuDrawable.IconState.ARROW);
            animateHomeIcon(MaterialMenuDrawable.IconState.BURGER);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    int getContainerRes() {
        return R.id.frm_container;
    }

    @Override
    public void onFragmentCallback(int code, View view, Object data) {
        Fragment fragment = null;
        switch (code) {
            case MAIN_ALBUMDETAIL:
                AlbumSummary albumSummary = (AlbumSummary) data;
                fragment = AlbumDetailFragment.instance(albumSummary);
                setHomeIcon(MaterialMenuDrawable.IconState.BURGER);
                animateHomeIcon(MaterialMenuDrawable.IconState.ARROW);
                addFragment(fragment, false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_like:
                addFragment(LikeFragment.instance(), false);
                break;
            case R.id.tool_billboard:
                onBackPressed();
                break;
        }
    }

    public boolean animateHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return false;
        currentIconState = iconState;
        homeButton.animateState(currentIconState);
        return true;
    }

    public void setHomeIcon(MaterialMenuDrawable.IconState iconState) {
        if (currentIconState == iconState) return;
        currentIconState = iconState;
        homeButton.setState(currentIconState);
    }

}
