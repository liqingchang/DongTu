package com.android.dongtu;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.android.dongtu.adapter.SmartFragmentPagerAdapter;
import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.ui.fragment.AlbumDetailFragment;
import com.android.dongtu.ui.view.TabBar;
import com.android.dongtu.ui.view.TabBarView;

public class MainActivity extends BaseActivity implements FragmentCallback {

    public static final int MAIN_ALBUMDETAIL = 0;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabBarView tabBarMain;
    private ImageButton btnBack;
    private TabBar tabBar;
    private SmartFragmentPagerAdapter adapter;

    @Override
    void init() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(12.0f);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.tab_bar);
            tabBar = (TabBar) getSupportActionBar().getCustomView();
            tabBarMain = (TabBarView) tabBar.getMain();
            btnBack = (ImageButton) tabBar.getSub().findViewById(R.id.btn_back);
        }

        viewPager = (ViewPager) findViewById(R.id.fragment_pager);
        if (adapter == null) {
            adapter = new SmartFragmentPagerAdapter(getSupportFragmentManager());
        }

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(SmartFragmentPagerAdapter.NUM_ITEMS - 1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (tabBarMain != null) {
                    tabBarMain.setOffset(positionOffset);
                    tabBarMain.setSelectedTab(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (adapter != null && adapter.getRegisteredFragment(position) != null) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (adapter.getRegisteredFragment(i) != null) {
                            adapter.getRegisteredFragment(i).setUserVisibleHint(position == i);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (tabBarMain != null) {
            tabBarMain.setSelectedTab(0);
        }

        tabBarMain.setOnTabClickedListener(new TabBarView.OnTabClickedListener() {
            @Override
            public void onTabClicked(int index) {
                viewPager.setCurrentItem(index);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    int getContainerRes() {
        return R.id.frm_container;
    }

    @Override
    public void onBackPressed() {
        if (tabBar.getStatus() == TabBar.STATUS_SUB) {
            tabBar.setStatus(TabBar.STATUS_MAIN);
        }
        super.onBackPressed();
    }

    @Override
    public void onFragmentCallback(int code, View view, Object data) {
        Fragment fragment;
        switch (code) {
            case MAIN_ALBUMDETAIL:
                tabBar.setStatus(TabBar.STATUS_SUB);
                AlbumSummary albumSummary = (AlbumSummary) data;
                fragment = AlbumDetailFragment.instance(albumSummary);
                addFragment(fragment, false);
                break;
        }
    }

}
