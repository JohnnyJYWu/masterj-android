package com.masterj.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.masterj.base.MasterJBase;
import com.masterj.base.config.DeviceConfig;
import com.masterj.base.third.supertoasts.SuperToast.Duration;
import com.masterj.base.ui.MasterJToast;

public class UIUtils {

    public static String ellipsizeString(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength) + "...";
        } else {
            return str;
        }
    }

    public static void setTextAndCursor(EditText textView, String text) {
        textView.setText(text);
        textView.setSelection(text.length());
    }

    public static void toast(int stringId, Object... args) {
        Application app = MasterJBase.getApplication();
        String str = app.getString(stringId, args);
        toast(str);
    }

    public static void toast(int stringId) {
        toast(stringId, Duration.VERY_SHORT);
    }

    public static void toast(String msg) {
        toast(msg, Duration.VERY_SHORT);
    }

    public static void toastBottom(String msg) {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            MasterJToast.makeTextBottom(MasterJBase.getApplication(), msg, Duration.VERY_SHORT).show();
        }
    }

    public static void toast(Context context, int msgId) {
        toast(context, msgId, Duration.VERY_SHORT);
    }

    public static void toast(Context context, int msgId, int duration) {
        MasterJToast.makeText(context, msgId, duration).show();
    }

    public static void toast(Context context, String msg) {
        toast(context, msg, Duration.VERY_SHORT);
    }

    public static void toast(Context context, String msg, int duration) {
        MasterJToast.makeText(context, msg, duration).show();
    }

    public static void toast(String msg, int duration) {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            MasterJToast.makeText(MasterJBase.getApplication(), msg, duration).show();
        }
    }

    public static void toast(int stringId, int duration) {
        Application app = MasterJBase.getApplication();
        toast(app.getString(stringId), duration);
    }

    public static void hideView(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void invisibleView(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void showView(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void clickView(final View view) {
        view.setPressed(true);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setPressed(false);
                view.performClick();
            }
        });
    }

    public static void setTextSizeById(TextView textView, int dimenId) {
        setTextSize(textView, textView.getResources().getDimension(dimenId));
    }

    public static void setTextSizeBySp(TextView textView, int spValue) {
        setTextSize(textView, sp2pix(spValue));
    }

    public static void setTextSize(TextView textView, float dimension) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension);
    }

    public static int dip2pix(int dip) {
        return dip2pix((float) dip);
    }

    public static int dip2pix(float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, DeviceConfig.getInstance().getDisplayMetrics());
    }

    public static int pix2dip(int px) {
        return (int) (px / DeviceConfig.getInstance().getDisplayMetrics().density);
    }

    public static int sp2pix(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, DeviceConfig.getInstance().getDisplayMetrics());
    }

    public static int dimen2pix(int dimenResId) {
        return MasterJBase.getApplication().getResources().getDimensionPixelSize(dimenResId);
    }

    public static void setWindowBrightness(Activity activity, float brightness) {
        setWindowBrightness(activity.getWindow(), brightness);
    }

    public static void setWindowBrightness(Window window, float brightness) {
        LayoutParams attrs = window.getAttributes();
        attrs.screenBrightness = brightness;
        window.setAttributes(attrs);
    }

    public static void setScreenOnFlag(Activity activity) {
        setScreenOnFlag(activity.getWindow());
    }

    public static void setScreenOnFlag(Window window) {
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void clearScreenOnFlag(Activity activity) {
        clearScreenOnFlag(activity.getWindow());
    }

    public static void clearScreenOnFlag(Window window) {
        window.clearFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static int getLocationCenterX(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return view.getMeasuredWidth() / 2 + location[0];
    }

    public static void addEmptyFooter(ListView listView, int height) {
        View view = new View(listView.getContext());
        ListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(params);
        listView.addFooterView(view);
    }

    public static int drawableWidth(int drawableId) {
        return ContextCompat.getDrawable(MasterJBase.getApplication(), drawableId).getIntrinsicWidth();
    }

    public static int drawableHeight(int drawableId) {
        return ContextCompat.getDrawable(MasterJBase.getApplication(), drawableId).getIntrinsicHeight();
    }
}
