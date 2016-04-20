package com.jellyape.dongtushe.data;

/**
 * 图片管理类,全局管理图片内容
 * Created by kuroterry on 15/12/20.
 */
public class PhotoManager {

    private static PhotoManager singleton = new PhotoManager();
    private AbstractLoader loader;

    public static PhotoManager getInstance() {
        return singleton;
    }

    private PhotoManager() {
        loader = new BaseLoader();
    }

    public boolean setFavorite(Photo photo) {
        return loader.setLike(photo);
    }

    public AlbumDetail getAllLike(int skip, int max) {
        return loader.loadLike(skip, max);
    }

    public AlbumDetail getRandom(int skip, int max){
        return loader.loadRandom(skip, max);
    }

    public AlbumDetail getRanking(int skip, int max){
        return loader.loadTop(skip, max);
    }


}
