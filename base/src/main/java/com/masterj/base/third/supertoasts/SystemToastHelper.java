package com.masterj.base.third.supertoasts;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

public class SystemToastHelper {
    private static Toast systemToastCache = null;

    static Toast getSystemToast(SuperToast superToast, boolean onlyReuseText) {
        if (systemToastCache != null) {
            systemToastCache.cancel();
        }
        systemToastCache = getToast(superToast, onlyReuseText);
        return systemToastCache;
    }

    private static Toast getToast(SuperToast superToast, boolean onlyReuseText) {
        ToastPositionConfig config = superToast.getPositionConfig();
        Toast toast;
        if (onlyReuseText) {
            toast = Toast.makeText(superToast.getContext(), superToast.getText(), superToast.getDuration());
        } else {
            toast = Toast.makeText(superToast.getContext(), "", superToast.getDuration());
            toast.setView(superToast.getView());
        }
        hookToast(toast);
        toast.setGravity(config.getGravity(), config.getxOffset(), config.getyOffset());
        return toast;
    }

    /**
     * 修复7.xToast崩溃问题
     * https://www.jianshu.com/p/c8e00943afc9
     */
    public static void hookToast(Toast toast) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            Class<Toast> cToast = Toast.class;
            try {
                //TN是private的
                Field fTn = cToast.getDeclaredField("mTN");
                fTn.setAccessible(true);

                //获取tn对象
                Object oTn = fTn.get(toast);
                //获取TN的class，也可以直接通过Field.getType()获取。
                Class<?> cTn = oTn.getClass();
                Field fHandle = cTn.getDeclaredField("mHandler");

                //重新set->mHandler
                fHandle.setAccessible(true);
                fHandle.set(oTn, new HandlerProxy((Handler) fHandle.get(oTn)));
            } catch (Throwable t) {
            }
        }
    }

    private static class HandlerProxy extends Handler {

        private Handler mHandler;

        HandlerProxy(Handler handler) {
            this.mHandler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                mHandler.handleMessage(msg);
            } catch (WindowManager.BadTokenException e) {
                //ignore
            }
        }
    }
}
