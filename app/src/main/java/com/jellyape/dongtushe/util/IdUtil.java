package com.jellyape.dongtushe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by terry on 15-5-29.
 */
public class IdUtil {

    public static final String DOC = "turingcat";
    public static final String FILE_ID = "id";
    public static final String KEY_ID = "key_id";

    /**
     * 生成一个id并记录到本地
     * 1. 获取ANDROID_ID，设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置
     * 2. 如果ANDROID_ID不可用，获取DEVICE_ID，是Android系统为开发者提供的用于标识手机设备的串号
     * 3. UUID的randomUUID方法随机生成一个UUID
     * 4. ID信息记录到SharedPreference以及本地sdcard
     *
     * @return
     */
    private static synchronized UUID generateId(Context context) {
        UUID uuid = null;
        // 获取ANDROID_ID
        final String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                uuid = UUID.nameUUIDFromBytes(androidId
                        .getBytes("utf8"));
            } else {
                final String deviceId = (
                        (TelephonyManager) context
                                .getSystemService(Context.TELEPHONY_SERVICE))
                        .getDeviceId();
                uuid = deviceId != null ? UUID
                        .nameUUIDFromBytes(deviceId
                                .getBytes("utf8")) : UUID
                        .randomUUID();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // SharedPreference记录id信息
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(KEY_ID, uuid.toString()).apply();
        // sdCard记录id信息
        writeToStrage(FILE_ID, uuid.toString());
        return uuid;
    }

    public synchronized static UUID getUUID(Context context) {
        UUID uuid = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String id = preferences.getString(KEY_ID, null);
        if (id == null || "".equals(id)) {
            id = readFromStrage(FILE_ID);
        }
        if ("".equals(id)) {
            uuid = generateId(context);
        } else {
            uuid = UUID.fromString(id);
        }

        return uuid;
    }

    private static File getTuringCatDoc() {
        File file = Environment.getExternalStorageDirectory();
        file = new File(file, DOC);
        if (!file.isDirectory()) {
            file.mkdir();
        }
        return file;
    }

    private static File getIdFile() {
        return new File(getTuringCatDoc(), FILE_ID);
    }

    private static String readFromStrage(String fileName) {
        File file = new File(getTuringCatDoc(), fileName);
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        if (file.exists() && file.isFile()) {
            FileReader in = null;
            try {
                in = new FileReader(file);
                br = new BufferedReader(in);
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private static void writeToStrage(String fileName, String text) {
        File file = new File(getTuringCatDoc(), fileName);
        PrintWriter pw = null;
        if (!file.exists())
            try {
                file.createNewFile();
                pw = new PrintWriter(new BufferedWriter(
                        new FileWriter(file)));
                pw.println(text);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(pw!=null) {
                    pw.close();
                }
            }
    }

}

