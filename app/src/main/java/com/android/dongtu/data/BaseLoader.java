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
 * Created by kuroterry on 15/11/28.
 */
public class BaseLoader extends AbstractLoader {

    public static final String BASE_URL = "http://4gun.net/api/v1";
    public static final String ALBUM_URL = BASE_URL + "/albums";
    public static final String ALBUM_DETAIL_URL = BASE_URL + "/photos?press=%1$s&beg=0&album_name=%2$s&uid=%3$s";
    public static final String ALBUM_LASTID = "?beg_id=";
    public static final String ALBUM_FAVORITE = BASE_URL + "/user/like?uid=%1$s&photo_id=%2$s";
    public static final String LIKE = BASE_URL + "/user/liked_photos?uid=%1$s";

    public static final String LIKE_SUCCESS = "success";


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
                albums.albumSummaries = albumSummaries;
                albums.setLastId(lId);
                return albums;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public AlbumDetail loadAlbumDetail(AlbumSummary albumSummary) {
        String url = String.format(ALBUM_DETAIL_URL, albumSummary.press, albumSummary.name, IdUtil.getUUID(App.sApp));
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
                    boolean isFavorite = photoJSONObject.getBoolean("is_like");
                    Photo photo = new Photo(id, photoUrl, isFavorite);
                    albumDetail.addPhotos(photo);
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
        String url = String.format(ALBUM_FAVORITE, IdUtil.getUUID(App.sApp), photo.id);
        String json = HttpUtil.get(url);
        Logger.i("terry", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String isSuccess = jsonObject.getString("status");
            if (isSuccess.contains(LIKE_SUCCESS)) {
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
        AlbumDetail albumDetail = new AlbumDetail();
        String url = String.format(LIKE, IdUtil.getUUID(App.sApp));
        String json = HttpUtil.get(url);
        Logger.i("terry", "load like" + json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject liked = jsonObject.getJSONObject("data");
            JSONArray likedArray = liked.getJSONArray("liked_photos");
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

}
