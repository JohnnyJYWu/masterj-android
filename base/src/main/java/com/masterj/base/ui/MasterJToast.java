package com.masterj.base.ui;

import android.content.Context;
import android.view.Gravity;

import com.masterj.base.third.supertoasts.SuperToast;

public class MasterJToast {

    public static SuperToast makeText(Context context, int resId, int duration) {
        return makeText(context, context.getResources().getText(resId), duration);
    }


    public static SuperToast makeText(Context context, CharSequence text, int duration) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = SuperToast.create(context, text, duration);
        superToast.setGravity(Gravity.CENTER, 0, 0);
        return superToast;
    }

    public static SuperToast makeTextBottom(Context context, CharSequence text, int duration) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = SuperToast.create(context, text, duration);
        superToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 64);
        return superToast;
    }

}
