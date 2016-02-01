package com.adminlib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class ABPrefsUtil {
    private static ABPrefsUtil prefsUtil;
    public Context context;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;

    /**
     * 获取单例
     *
     * @param context 上下文
     * @return ABPrefsUtil对象
     */
    public synchronized static ABPrefsUtil getInstance(Context context) {
        if (prefsUtil == null) {
            prefsUtil = new ABPrefsUtil();
        }
        prefsUtil.context = context;
        return prefsUtil;
    }

    /**
     * 初始化
     *
     * @param prefsname the prefsname
     * @param mode      the mode
     */
    public void init(String prefsname, int mode) {
        prefsUtil.prefs = prefsUtil.context.getSharedPreferences(prefsname, mode);
        prefsUtil.editor = prefsUtil.prefs.edit();
    }

    /**
     * 无参构造
     */
    private ABPrefsUtil() {
    }

    /**
     * 获取boolean
     *
     * @param key        the key
     * @param defaultVal 默认值
     * @return 返回的对应值
     */
    public boolean getBoolean(String key, boolean defaultVal) {
        return this.prefs.getBoolean(key, defaultVal);
    }

    /**
     * 获取boolean
     *
     * @param key the key
     * @return 返回的对应值
     */
    public boolean getBoolean(String key) {
        return this.prefs.getBoolean(key, false);
    }


    public String getString(String key, String defaultVal) {
        return this.prefs.getString(key, defaultVal);
    }

    public String getString(String key) {
        return this.prefs.getString(key, null);
    }

    public int getInt(String key, int defaultVal) {
        return this.prefs.getInt(key, defaultVal);
    }

    public int getInt(String key) {
        return this.prefs.getInt(key, 0);
    }


    public float getFloat(String key, float defaultVal) {
        return this.prefs.getFloat(key, defaultVal);
    }

    public float getFloat(String key) {
        return this.prefs.getFloat(key, 0f);
    }

    public long getLong(String key, long defaultVal) {
        return this.prefs.getLong(key, defaultVal);
    }

    public long getLong(String key) {
        return this.prefs.getLong(key, 0l);
    }

    public ArrayList<String> getArrayStringList(String key) {
        ArrayList<String> showSceneList = new ArrayList<String>();
        if (prefs == null)
            return showSceneList;
        String liststr = this.prefs.getString(key, null);
        if (!TextUtils.isEmpty(liststr)) {
            try {
                showSceneList.addAll(String2SceneList(liststr));
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return showSceneList;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defaultVal) {
        return this.prefs.getStringSet(key, defaultVal);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key) {
        return this.prefs.getStringSet(key, null);
    }

    public Map<String, ?> getAll() {
        return this.prefs.getAll();
    }


    public ABPrefsUtil putArrayStringList(String key, ArrayList<String> value) {
        try {
            editor.putString(key, SceneList2String(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        editor.commit();
        return this;
    }

    public ABPrefsUtil putString(String key, String value) {
        editor.putString(key, value);
//        editor.commit();
        return this;
    }

    public ABPrefsUtil putInt(String key, int value) {
        editor.putInt(key, value);
//        editor.commit();
        return this;
    }

    public ABPrefsUtil putFloat(String key, float value) {
        editor.putFloat(key, value);
//        editor.commit();
        return this;
    }

    public ABPrefsUtil putLong(String key, long value) {
        editor.putLong(key, value);
//        editor.commit();
        return this;
    }

    public ABPrefsUtil putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
//        editor.commit();
        return this;
    }

    public void commit() {
        editor.commit();
        if (prefsUtil != null)
            prefsUtil = null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ABPrefsUtil putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.commit();
        return this;
    }


    public static String SceneList2String(ArrayList SceneList)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList String2SceneList(String SceneListString)
            throws IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        ArrayList SceneList = (ArrayList) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }
}
