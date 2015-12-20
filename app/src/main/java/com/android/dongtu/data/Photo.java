package com.android.dongtu.data;

import java.io.Serializable;

/**
 * 专辑具体图片
 * Created by kuroterry on 15/12/20.
 */
public class Photo implements Serializable {

    /**
     * 图片url
     */
    public String url;
    /**
     * id
     */
    public String id;
    /**
     * 是否用户的收藏图片
     */
    public boolean isFavorite;

    public Photo(String id, String url, boolean isFavorite) {
        this.id = id;
        this.url = url;
        this.isFavorite = isFavorite;
    }


}
