package com.jellyape.dongtushe;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.jellyape.dongtushe.adapter.SmartFragmentPagerAdapter;
import com.jellyape.dongtushe.data.AlbumSummary;
import com.jellyape.dongtushe.ui.fragment.AlbumDetailFragment;
import com.jellyape.dongtushe.ui.view.TabBar;
import com.jellyape.dongtushe.ui.view.TabBarView;
import com.jellyape.dongtushe.util.FileUtil;
import com.jellyape.dongtushe.util.ShareUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

public class MainActivity extends BaseActivity implements FragmentCallback {

    public static final int MAIN_ALBUMDETAIL = 0;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabBarView tabBarMain;
    private ImageButton btnBack;
    private ImageButton btnShare;
    private TabBar tabBar;
    private SmartFragmentPagerAdapter adapter;
    private AlbumSummary handlingAlbum;

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
            btnShare = (ImageButton) tabBar.getSub().findViewById(R.id.btn_share);
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

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handlingAlbum != null) {
                    String fileName = handlingAlbum.model + "_" + handlingAlbum.name + ".png";
                    String text = handlingAlbum.model + "@" + handlingAlbum.name + " " + MainActivity.this.getString(R.string.share_from) + " " + MainActivity.this.getString(R.string.app_name);
                    File coverDoc = new File(FileUtil.getDefaultLocalDir(Const.ROOT_DOC), Const.COVER_DOC);
                    File cover = new File(coverDoc, fileName);
                    if (cover.exists()) {
                        ShareUtil.shareImage(MainActivity.this, text, Uri.fromFile(cover));
                    }
                }
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
                handlingAlbum = albumSummary;
                // 每次进入都异步下载封面用于分享
                String fileName = albumSummary.model + "_" + albumSummary.name + ".png";
                File cover = new File(FileUtil.getDefaultLocalDir(Const.ROOT_DOC + File.separatorChar + Const.COVER_DOC), fileName);
                if (!cover.exists() || cover.length() == 0) {
                    CoverDownloadTask coverDownloadTask = new CoverDownloadTask();
                    coverDownloadTask.execute(albumSummary.coverUrl, fileName);
                }
                fragment = AlbumDetailFragment.instance(albumSummary);
                addFragment(fragment, false);
                break;
        }
    }

    private class CoverDownloadTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(final String... params) {
            ImageLoader.getInstance().loadImage(params[0], new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    File coverDoc = new File(FileUtil.getDefaultLocalDir(Const.ROOT_DOC), Const.COVER_DOC);
                    File target = new File(coverDoc, params[1]);
                    FileUtil.convertBitmapToFile(loadedImage, target, Bitmap.CompressFormat.PNG);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            return null;
        }

    }

}
