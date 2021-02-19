package com.feixun.shutdown;

import android.app.Application;

import com.zhouyou.http.EasyHttp;

/**
 * 版权：易金 版权所有
 * <p>
 * 作者：suntinghui
 * <p>
 * 创建日期：2020/9/23 0023
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasyHttp.init(this);//默认初始化
    }
}
