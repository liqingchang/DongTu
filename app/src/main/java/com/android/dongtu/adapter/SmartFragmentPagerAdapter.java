/*
 * Copyright (C) 2014 Freddie (Musenkishi) Lust-Hed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.dongtu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.android.dongtu.ui.fragment.AlbumSummaryFragment;
import com.android.dongtu.ui.fragment.LikeFragment;
import com.android.dongtu.ui.fragment.RandomFragment;
import com.android.dongtu.ui.fragment.TopFragment;

/**
 * Created by Musenkishi on 2014-05-26 17:26.
 */
public class SmartFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    public static int NUM_ITEMS = 4;

    public SmartFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RandomFragment.instance();
            case 1:
                return AlbumSummaryFragment.instance();
            case 2:
                return TopFragment.instance();
            case 3:
                return LikeFragment.instance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
