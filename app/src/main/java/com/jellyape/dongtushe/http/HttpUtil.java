package com.jellyape.dongtushe.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by kuroterry on 15/11/28.
 */
public class HttpUtil {

	public static String get(String url) {
		OkHttpClient client = new OkHttpClient();
		try {
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
