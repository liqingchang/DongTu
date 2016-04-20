package com.jellyape.dongtushe.data;

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

    public List<Photo> pics;

    /**
     * index
     */
    private int position;

    public AlbumDetail() {
        pics = new ArrayList<>();
    }

    public AlbumDetail(AlbumSummary albumSummary) {
        this();
        this.albumSummary = albumSummary;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getSize() {
        return pics.size();
    }

    public void addPhoto(Photo photo) {
        pics.add(photo);
    }

    public void addAllPhoto(List<Photo> photos) {
        pics.addAll(photos);
    }

    public List<Photo> getPics(int startPosition, int count) {
        List<Photo> ret = new ArrayList<>();
        for (int i = startPosition; i < startPosition + count; i++) {
            if (i < pics.size()) {
                ret.add(pics.get(i));
            }
        }
        position += count;
        return ret;
    }

    public List<Photo> getPics(int startPosition) {
        return getPics(startPosition, DEFAULT_COUNT);
    }

    public List<Photo> getAllPics() {
        return pics;
    }

}
