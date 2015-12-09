package com.android.dongtu;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.ui.fragment.AlbumDetailFragment;
import com.android.dongtu.ui.fragment.AlbumSummaryFragment;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;

public class MainActivity extends BaseActivity implements FragmentCallback {

    public static final int MAIN_ALBUMDETAIL = 0;

    private Toolbar toolbar;
    public MaterialMenuView homeButton;

    @Override
    void init() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.dra);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        homeButton = (MaterialMenuView) findViewById(R.id.material_menu_button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addFragment(new AlbumSummaryFragment(), true);
    }

    private MaterialMenuDrawable.IconState currentIconState;
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


    @Override
    int getContainerRes() {
        return R.id.frm_container;
    }


    @Override
    public void onFragmentCallback(int code,View view, Object data) {
        Fragment fragment = null;
        switch(code) {
            case MAIN_ALBUMDETAIL:
                AlbumSummary albumSummary = (AlbumSummary) data;
                fragment = AlbumDetailFragment.instance(albumSummary);
                setHomeIcon(MaterialMenuDrawable.IconState.BURGER);
                animateHomeIcon(MaterialMenuDrawable.IconState.ARROW);
                addFragment(fragment, false);
                break;
        }
    }

}
