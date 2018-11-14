package com.example.zhw.piontandpiont2.Util;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * 说明：Android 6.0+ 状态栏图标原生反色操作
 */
public class DarkStatusBar {
    //反色方法
    public static void setDarkStatusIcon(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView == null) return;
            int vis = decorView.getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(vis);
        }
    }

}
