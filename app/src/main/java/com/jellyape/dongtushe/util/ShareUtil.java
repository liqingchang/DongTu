package com.jellyape.dongtushe.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jellyape.dongtushe.Const;
import com.jellyape.dongtushe.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;

/**
 * 提供分享方法
 * Created by kuroterry on 16/5/7.
 */
public class ShareUtil {

    @SuppressWarnings("unused")
    public static void shareText(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static void shareImage(Context context, String text, Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("*/*");
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_to)));
    }

    public static void shareCache(Context context, String text, String url) {
        File pic = ImageLoader.getInstance().getDiskCache().get(url);
        File coverDoc = new File(FileUtil.getDefaultLocalDir(Const.ROOT_DOC), Const.COVER_DOC);
        File target = new File(coverDoc, Const.SHARE_FILENAME);
        if (pic.exists()) {
            try {
                FileUtil.copyFile(pic, target);
                shareImage(context, text, Uri.fromFile(target));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
