package com.jellyape.dongtushe.app;

import android.app.Application;

import com.jellyape.dongtushe.util.IdUtil;
import com.jellyape.dongtushe.util.Logger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.beacon.event.UserAction;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.UUID;

/**
 * Created by kuroterry on 15/11/15.
 */
public class App extends Application {

    public static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        UUID uuid = IdUtil.getUUID(this);
        Logger.i(uuid.toString());
        sApp = this;
        UserAction.initUserAction(this);
        
        CrashReport.initCrashReport(getApplicationContext(), "900020918", false);
    }
}
