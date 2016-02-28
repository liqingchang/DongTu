package com.android.dongtu.data;

import com.android.dongtu.app.App;
import com.android.dongtu.http.HttpUtil;
import com.android.dongtu.util.IdUtil;
import com.android.dongtu.util.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本数据加载类
 * Created by kuroterry on 15/11/28.
 */
public class BaseLoader extends AbstractLoader implements ISubject{

    public static final String BASE_URL = "http://4gun.net/api/v1";
    public static final String ALBUM_URL = BASE_URL + "/albums?uid=%1$s&os=%2$s&skip=%3$s&max=%4$s";
    public static final String ALBUM_DETAIL_URL = BASE_URL + "/photos?press=%1$s&album_name=%2$s&uid=%3$s&os=%4$s&skip=%5$s&max=%6$s";
    public static final String ALBUM_LASTID = "?beg_id=";
    public static final String SET_LIKE = BASE_URL + "/user/like?uid=%1$s&photo_id=%2$s&os=%3$s";
    public static final String LIKE = BASE_URL + "/user/liked_photos?uid=%1$s&skip=%2$s&max=%3$s&os=%4$s";

    public static final String RANDOM = BASE_URL + "/random?uid=%1$s&skip=%2$s&max=%3$s&os=%4$s";
    public static final String RANK = BASE_URL + "/ranking?uid=%1$s&skip=%2$s&max=%3$s&os=%4$s";

    public static final String LIKE_SUCCESS = "success";
    public static final String OS = "android";

    public static List<IObserver> observers = new ArrayList<>();


    @Override
    public Albums loadAlbumSummary(int skip, int max) {
        Albums albums = new Albums();
        List<AlbumSummary> albumSummaries = new ArrayList<>();
        String url = String.format(ALBUM_URL, IdUtil.getUUID(App.sApp), OS, skip, max);
        Logger.i("terry", "load url : " + url);
        String jsonString = HttpUtil.get(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray albumList = data.getJSONArray("albums");
                for (int i = 0; i < albumList.length(); i++) {
                    JSONObject jsonAlbum = albumList.getJSONObject(i);
                    AlbumSummary albumSummary = new AlbumSummary();
                    albumSummary.name = jsonAlbum.getString("name");
                    albumSummary.coverUrl = jsonAlbum.getString("cover");
                    albumSummary.press = jsonAlbum.getString("press");
                    albumSummary.model = jsonAlbum.getString("models");
                    albumSummaries.add(albumSummary);
                }
                albums.setAllAlbumSummaries(albumSummaries);
                return albums;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Albums loadAlbumSummary(int skip) {
        return loadAlbumSummary(skip, getDefaultCount());
    }

    @Override
    public Albums loadAlbumSummary() {
        return loadAlbumSummary(0);
    }

    @Override
    public Albums loadAlbumSummary(String lastId) {
        return loadAlbumSummary(lastId, getDefaultCount());
    }

    @Override
    public Albums loadAlbumSummary(String lastId, int size) {
        Albums albums = new Albums();
        List<AlbumSummary> albumSummaries = new ArrayList<>();
        String url = lastId == null ? ALBUM_URL : ALBUM_URL + ALBUM_LASTID + lastId;
        String jsonString = HttpUtil.get(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray albumList = data.getJSONArray("albums");
                String lId = data.getString("last_id");
                for (int i = 0; i < albumList.length(); i++) {
                    JSONObject jsonAlbum = albumList.getJSONObject(i);
                    AlbumSummary albumSummary = new AlbumSummary();
                    albumSummary.name = jsonAlbum.getString("name");
                    albumSummary.coverUrl = jsonAlbum.getString("cover_url");
                    albumSummary.press = jsonAlbum.getString("press");
                    albumSummary.model = jsonAlbum.getString("models");
                    albumSummaries.add(albumSummary);
                }
                albums.setAllAlbumSummaries(albumSummaries);
                return albums;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public AlbumDetail loadAlbumDetail(AlbumSummary albumSummary) {
        return loadAlbumDetail(albumSummary, 0, getDefaultCount());
    }

    @Override
    public AlbumDetail loadAlbumDetail(AlbumSummary albumSummary, int skip) {
        return loadAlbumDetail(albumSummary, skip, getDefaultCount());
    }

    @Override
    public AlbumDetail loadAlbumDetail(AlbumSummary albumSummary, int skip, int max) {
        String url = String.format(ALBUM_DETAIL_URL, albumSummary.press, albumSummary.name, IdUtil.getUUID(App.sApp), OS, skip,max);
        AlbumDetail albumDetail = new AlbumDetail(albumSummary);
        String jsonString = HttpUtil.get(url);
        Logger.i("terry", "photo json:" + jsonString);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray photos = data.getJSONArray("photos");
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject photoJSONObject = photos.getJSONObject(i);
                    String photoUrl = photoJSONObject.getString("url");
                    String id = photoJSONObject.getString("id");
                    boolean isFavorite = photoJSONObject.getInt("likes") == 1;
                    Photo photo = new Photo(id, photoUrl, isFavorite);
                    albumDetail.addPhoto(photo);
                }
                return albumDetail;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean setLike(Photo photo) {
        // 组装url
        String url = String.format(SET_LIKE, IdUtil.getUUID(App.sApp), photo.id, OS);
        String json = HttpUtil.get(url);
        Logger.i("terry", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String isSuccess = jsonObject.getString("status");
            if (isSuccess.contains(LIKE_SUCCESS)) {
                notifyUpdate();
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AlbumDetail loadLike() {
        return loadLike(0, getDefaultCount());
    }

    @Override
    public AlbumDetail loadLike(int skip, int max) {
        String url = String.format(LIKE, IdUtil.getUUID(App.sApp), skip, max, OS);
        AlbumDetail albumDetail = new AlbumDetail();
        String json = HttpUtil.get(url);
        Logger.i("terry", "load " + url);
        Logger.i("terry", "load " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject liked = jsonObject.getJSONObject("data");
            JSONArray likedArray = liked.getJSONArray("photos");
            for (int i = 0; i < likedArray.length(); i++) {
                JSONObject photoJSONObject = likedArray.getJSONObject(i);
                String photoUrl = photoJSONObject.getString("url");
                String id = photoJSONObject.getString("photo_id");
                Photo photo = new Photo(id, photoUrl, true);
                albumDetail.pics.add(photo);
            }
        } catch (JSONException e) {
            Logger.e(e.toString());
        }
        return albumDetail;
    }

    @Override
    public AlbumDetail loadRandom() {
        return loadRandom(0, getDefaultCount());
    }

    @Override
    public AlbumDetail loadRandom(int skip, int max) {
        String url = String.format(RANDOM, IdUtil.getUUID(App.sApp), skip , max , OS);
        return load(url);
    }

    @Override
    public AlbumDetail loadTop() {
        return loadTop(0, getDefaultCount());
    }

    @Override
    public AlbumDetail loadTop(int skip, int max) {
        String url = String.format(RANK, IdUtil.getUUID(App.sApp), skip, max, OS);
        return load(url);
    }

    public AlbumDetail load(String url, int skip, int max) {
        return null;
    }


    private AlbumDetail load(String url){
        AlbumDetail albumDetail = new AlbumDetail();
        String json = HttpUtil.get(url);
        Logger.i("terry", "load " + url);
        Logger.i("terry", "load " + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject liked = jsonObject.getJSONObject("data");
            JSONArray likedArray = liked.getJSONArray("photos");
            for (int i = 0; i < likedArray.length(); i++) {
                JSONObject photoJSONObject = likedArray.getJSONObject(i);
                String photoUrl = photoJSONObject.getString("url");
                String id = photoJSONObject.getString("id");
                boolean like = photoJSONObject.getInt("likes") == 1;
                Photo photo = new Photo(id, photoUrl, like);
                albumDetail.pics.add(photo);
            }
        } catch (JSONException e) {
            Logger.e(e.toString());
        }
        return albumDetail;
    }

    @Override
    public void addObserver(IObserver iObserver) {
        observers.add(iObserver);
    }

    @Override
    public void removeObserver(IObserver iObserver) {
        observers.remove(iObserver);
    }

    @Override
    public void notifyUpdate() {
        for(IObserver iObserver : observers) {
            iObserver.update();
        }
    }

}
