package com.adminlib.mine;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;


/**
 * 类描述：BaseActivity
 * 创建人：Michael
 * 创建时间：2015/12/28 13:39
 * 修改人：Michael
 * 修改时间：2015/12/28 13:39
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity {
    private boolean isExit = false;

    public void setExit(boolean exit) {
        isExit = exit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String msg) {
        MApplication.showToast(this, msg + "");
    }

    protected String getResString(int resId) {
        return getResources().getString(resId) + "";
    }


    private long exitTime = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isExit && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {// android2.1
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
            }
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
