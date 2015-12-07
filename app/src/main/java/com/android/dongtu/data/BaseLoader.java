package com.android.dongtu.data;

import android.util.Log;

import com.android.dongtu.http.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuroterry on 15/11/28.
 */
public class BaseLoader extends AbstractLoader {

    public static final String ALBUM_URL = "http://4gun.net/api/v1/albums";
    public static final String ALBUM_DETAIL_URL = "http://4gun.net/api/v1/photos?press=%1$s&beg=0&album_name=%2$s";
    public static final String ALBUM_LASTID = "?beg_id=";


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
        String url = String.format(ALBUM_DETAIL_URL, albumSummary.press, albumSummary.name);
        AlbumDetail albumDetail = new AlbumDetail(albumSummary);
        String jsonString = HttpUtil.get(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray photos = data.getJSONArray("photos");
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject photoJSONObject = photos.getJSONObject(i);
                    String photoUrl = photoJSONObject.getString("url");
                    String id = photoJSONObject.getString("id");
                    albumDetail.addPhotos(photoUrl);
                }
                return albumDetail;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
