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
    public static final String ALBUM_DETAIL_URL = "http://4gun.net/api/v1/photos?press=%1$s&beg=0&max=100&album_name=%2$s";


    @Override
    public List<AlbumSummary> loadAlbumSummary() {
        return loadAlbumSummary(getDefaultCount());
    }

    @Override
    public List<AlbumSummary> loadAlbumSummary(int size) {
        List<AlbumSummary> albumSummaries = new ArrayList<>();
        String jsonString = HttpUtil.get(ALBUM_URL);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray albums = data.getJSONArray("albums");
                for (int i = 0; i < albums.length(); i++) {
                    JSONObject jsonAlbum = albums.getJSONObject(i);
                    AlbumSummary albumSummary = new AlbumSummary();
                    albumSummary.name = jsonAlbum.getString("name");
                    albumSummary.coverUrl = jsonAlbum.getString("cover_url");
                    albumSummary.press = jsonAlbum.getString("press");
                    albumSummary.model = jsonAlbum.getString("models");
                    albumSummaries.add(albumSummary);
                }
                return albumSummaries;
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
                Log.i("terry", "size:" + albumDetail.getPics().size());
                return albumDetail;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
