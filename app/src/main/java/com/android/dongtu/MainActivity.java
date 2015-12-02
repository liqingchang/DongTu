package com.android.dongtu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.dongtu.data.AlbumDetail;
import com.android.dongtu.data.AlbumSummary;
import com.android.dongtu.ui.fragment.AlbumDetailFragment;
import com.android.dongtu.ui.fragment.AlbumSummaryFragment;
import com.android.dongtu.ui.fragment.PhotoFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentCallback {

    public static final int MAIN_ALBUMDETAIL = 0;
    public static final int MAIN_ALBUMPHOTO =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new AlbumSummaryFragment(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentCallback(int code, Object data) {
        Fragment fragment = null;
        switch(code) {
            case MAIN_ALBUMDETAIL:
                AlbumSummary albumSummary = (AlbumSummary) data;
                fragment = AlbumDetailFragment.instance(albumSummary);
                break;
            case MAIN_ALBUMPHOTO:
                List<String> photoUrl = (List<String>) data;
                AlbumDetail albumDetail = new AlbumDetail();
                albumDetail.pics = photoUrl;
                fragment = PhotoFragment.instance(albumDetail, 0);
                break;
        }
        if(fragment != null) {
            addFragment(fragment, false);
        }
    }

    private void addFragment(Fragment fragment, boolean isReplace) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isReplace) {
            fragmentTransaction.replace(R.id.frm_container, fragment);
        } else {
            fragmentTransaction.add(R.id.frm_container, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

}
