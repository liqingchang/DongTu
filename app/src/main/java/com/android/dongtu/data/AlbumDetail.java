package com.android.dongtu.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册详情内容
 * Created by kuroterry on 15/11/30.
 */
public class AlbumDetail implements Serializable {

	private AlbumSummary albumSummary;

	public List<String> pics;

	public AlbumDetail(){

	}

	public AlbumDetail(AlbumSummary albumSummary) {
		this.albumSummary = albumSummary;
		pics = new ArrayList<>();
	}

	public void addPhotos(String photoUrl) {
		pics.add(photoUrl);
	}

	public List<String> getPics() {
		return pics;
	}

}
