package com.jellyape.dongtushe.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jellyape.dongtushe.R;

/**
 * Toolbar
 * 暂时有2个状态
 * 1. 主状态,用于主界面的各个图片浏览Fragment
 * 2. 二级状态,用于点击专辑后显示专辑内容的情况, 一般情况为一个返回键, 点击后返回到主状态
 *
 * 主状态和二级状态均对应一个TabBarView
 *
 * Created by kuroterry on 15/12/26.
 */
public class TabBar extends FrameLayout {

    public static final int STATUS_MAIN = 0;
    public static final int STATUS_SUB = 1;

    private int mainLayout;
    private int subLayout;

    private ViewGroup main;
    private ViewGroup sub;

    private int status = STATUS_MAIN;

    private OnStatusChangeListener onStatusChangeListener;

    public TabBar(Context context) {
        this(context, null);
    }

    public TabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributes(context, attrs);
        init(context);
    }

    public ViewGroup getMain() {
        return main;
    }

    public ViewGroup getSub() {
        return sub;
    }

    public int getStatus(){
        return status;
    }

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;
    }

    public void setStatus(int status) {
        switch(status) {
            case STATUS_MAIN:
                main.setVisibility(View.VISIBLE);
                sub.setVisibility(View.GONE);
                break;
            case STATUS_SUB:
                main.setVisibility(View.GONE);
                sub.setVisibility(View.VISIBLE);
                break;
        }
        if(onStatusChangeListener != null) {
            onStatusChangeListener.onStatusChangeListener(status);
        }
        this.status = status;
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        Resources.Theme theme = context.getTheme();
        if (theme != null) {
            TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.TabBar, 0, 0);
            if (typedArray != null) {

                int n = typedArray.getIndexCount();

                for (int i = 0; i < n; i++){
                    int attr = typedArray.getIndex(i);

                    switch (attr){
                        case R.styleable.TabBar_main:
                            int mainLayout = typedArray.getResourceId(i, 0);
                            this.mainLayout = mainLayout;
                            break;
                        case R.styleable.TabBar_sub:
                            int subLayout = typedArray.getResourceId(i, 0);
                            this.subLayout = subLayout;
                            break;
                    }
                }
                typedArray.recycle();
            }
        }
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        main = (ViewGroup) inflater.inflate(mainLayout, null);
        sub = (ViewGroup) inflater.inflate(subLayout, null);
        sub.setVisibility(View.GONE);
        addView(main);
        addView(sub);
    }

    public interface OnStatusChangeListener {
        public void onStatusChangeListener(int desStatus);
    }

}
