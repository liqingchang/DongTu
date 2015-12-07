package com.android.dongtu.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册详情内容
 * Created by kuroterry on 15/11/30.
 */
public class AlbumDetail implements Serializable {

    private static final int DEFAULT_COUNT = 10;

	private AlbumSummary albumSummary;

	public List<String> pics;

	private int position;

	public AlbumDetail(){

	}

	public AlbumDetail(AlbumSummary albumSummary) {
		this.albumSummary = albumSummary;
		pics = new ArrayList<>();
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void addPhotos(String photoUrl) {
		pics.add(photoUrl);
	}

    public List<String> getPics(int count) {
        List<String> ret = new ArrayList<>();
        for(int i = position ; i < position + count ; i++) {
            if(i < pics.size()) {
                ret.add(pics.get(i));
            }
        }
        position += count;
        Log.i("terry", "position: " + position +  " | size:" + ret.size());
        return ret;
    }

	public List<String> getPics() {
        return getPics(DEFAULT_COUNT);
	}

}
