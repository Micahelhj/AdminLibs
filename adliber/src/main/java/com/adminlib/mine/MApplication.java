package com.adminlib.mine;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.adminlibs.R;
import com.adutils.ABFileUtil;


/**
 * 类描述：主Application
 * 创建人：Michael
 * 创建时间：2015/10/27 16:42
 * 修改人：Michael
 * 修改时间：2015/10/27 16:42
 * 修改备注：
 */
public class MApplication extends Application {
    private static boolean isGetLog = true;

    /**
     * 设置是否捕获日志
     *
     * @param isGetLog 日子捕获开关
     */
    public void setIsGetLog(boolean isGetLog) {
        this.isGetLog = isGetLog;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initAll(this);
    }
    /**
     * ========================================================================================================================================
     */
    /**
     * 自定义初始化
     *
     * @param application Application对象
     */
    public static void initAll(MApplication application) {
        ABFileUtil.initFileDir(application);//初始化缓存文件夹
        if (isGetLog)
            CrashHandler.getInstance().initCrash(application);// 自定义捕获程序崩溃日志
    }

    /**
     * ===============================================================================================================================================
     */
    /**
     * 自定义等待提示弹出框
     */
    public static class MyProgressDialog extends ProgressDialog {
        public MyProgressDialog(Context context) {
            super(context);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.dismiss();
                return true;
            }
            return super.onKeyUp(keyCode, event);
        }

        @Override
        public void setView(View view) {
            super.setView(view);
        }
    }

    /**
     * 自定义Loading
     */
    public static MyProgressDialog myProgerssDialog = null;

    /**
     * 打开Loading
     *
     * @param activity      Activity对象
     * @param onKeyListener 弹出框监听事件
     * @param message       弹出的提示内容
     */
    public static void openProgressDialog(Activity activity, DialogInterface.OnKeyListener onKeyListener, String message) {
        if (myProgerssDialog != null) {
            closeProgressDialog();
        }
        myProgerssDialog = new MyProgressDialog(activity);
        myProgerssDialog.setProgressStyle(R.style.CustomDialog);
        myProgerssDialog.setCanceledOnTouchOutside(false);
        myProgerssDialog.setCancelable(true);
        if (message != null && !message.equals("")) {
            myProgerssDialog.setMessage(message);
        } else {
            myProgerssDialog.setMessage("正在加载...");
        }
        if (onKeyListener != null)
            myProgerssDialog.setOnKeyListener(onKeyListener);
        if (!activity.isFinishing())
            myProgerssDialog.show();
    }

    /**
     * 关闭Loading
     */
    public static void closeProgressDialog() {
        if (myProgerssDialog != null && myProgerssDialog.isShowing()) {
            myProgerssDialog.dismiss();
            myProgerssDialog = null;
        }
    }

    /**
     * 自定义Toast
     */
    private static Toast toast = null;

    /**
     * 关闭Loading
     *
     * @param context 上下文
     * @param msg     提示内容
     */
    public static void showToast(Context context, String msg) {
        showToast(context, msg, false);
    }

    /**
     * 战士toast
     *
     * @param context        上下文
     * @param msg            提示内容
     * @param isShowLongTime 展示时间
     */
    public static void showToast(Context context, String msg, boolean isShowLongTime) {
        if (toast == null) {
            if (isShowLongTime) {
                toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
            } else {
                toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
            }
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
