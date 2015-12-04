package com.android.dongtu;

import android.support.v4.app.Fragment;
import android.view.View;

import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.ui.fragment.AlbumDetailFragment;
import com.android.dongtu.ui.fragment.AlbumSummaryFragment;

public class MainActivity extends BaseActivity implements FragmentCallback {

    public static final int MAIN_ALBUMDETAIL = 0;

    @Override
    void init() {
        setContentView(R.layout.activity_main);
        addFragment(new AlbumSummaryFragment(), true);
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
                addFragment(fragment, false);
                break;
        }
    }

}
