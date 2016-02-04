package com.adminlib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * User: Meekly-hj
 * Date: 2015-07-24
 * Time: 10:51
 * FIXME
 */
@SuppressWarnings("ALL")
public class ABManifestUtil {
    /**
     * 从Meta中获取值
     *
     * @param context 上下文
     * @param metaKey 键
     * @return 值
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return apiKey;
    }
}
