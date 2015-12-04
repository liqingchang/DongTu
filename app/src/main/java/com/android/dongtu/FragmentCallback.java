package com.android.dongtu;

import android.view.View;

/**
 * Fragment->Activity的回调
 * 当Fragment需要回调Activity信息的时候，只需要定义FragmentCallback的对象，
 * 并在必要的时候传入执行代码（不能相同）并执行FragmentCallback的onFragmentCallback方法，
 * Activity会根据情况执行对应逻辑
 * Created by kuroterry on 15/11/30.
 */
public interface FragmentCallback {

    public void onFragmentCallback(int code, View view, Object data);

}
