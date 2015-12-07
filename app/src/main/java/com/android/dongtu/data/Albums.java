package com.android.dongtu.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页每次请求得到的专辑列表
 * Created by kuroterry on 15/12/7.
 */
public class Albums {

    public List<AlbumSummary> albumSummaries;
    private String lastId;

    public Albums(){
        albumSummaries = new ArrayList<>();
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getLastId(){
        return lastId;
    }

}
