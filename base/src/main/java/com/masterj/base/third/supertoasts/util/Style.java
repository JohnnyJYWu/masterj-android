package com.masterj.base.third.supertoasts.util;

import android.graphics.Color;
import android.graphics.Typeface;

import com.masterj.base.R;
import com.masterj.base.third.supertoasts.SuperToast;

/**
 * Creates a reference to basic style options so that all types of SuperToasts * will be themed the same way in a particular class.
 */
@SuppressWarnings("UnusedDeclaration")
public class Style {
    public static final int BLACK = 0;
    public static final int BLUE = 1;
    public static final int GRAY = 2;
    public static final int GREEN = 3;
    public static final int ORANGE = 4;
    public static final int PURPLE = 5;
    public static final int RED = 6;
    public static final int WHITE = 7;
    public SuperToast.Animations animations = SuperToast.Animations.FADE;
    public int background = getBackground(BLACK);
    public int typefaceStyle = Typeface.NORMAL;
    public int textColor = Color.WHITE;
    public int dividerColor = Color.WHITE;
    public int buttonTextColor = Color.LTGRAY;

    /**
     * Returns a preset style.     *     * @param styleType {@link Style}     * @return {@link Style}
     */
    public static Style getStyle(int styleType) {
        final Style style = new Style();
        switch (styleType) {
            case BLACK:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLACK);
                style.dividerColor = Color.WHITE;
                return style;
            case WHITE:
                style.textColor = Color.DKGRAY;
                style.background = getBackground(WHITE);
                style.dividerColor = Color.DKGRAY;
                style.buttonTextColor = Color.GRAY;
                return style;
            case GRAY:
                style.textColor = Color.WHITE;
                style.background = getBackground(GRAY);
                style.dividerColor = Color.WHITE;
                style.buttonTextColor = Color.GRAY;
                return style;
            case PURPLE:
                style.textColor = Color.WHITE;
                style.background = getBackground(PURPLE);
                style.dividerColor = Color.WHITE;
                return style;
            case RED:
                style.textColor = Color.WHITE;
                style.background = getBackground(RED);
                style.dividerColor = Color.WHITE;
                return style;
            case ORANGE:
                style.textColor = Color.WHITE;
                style.background = getBackground(ORANGE);
                style.dividerColor = Color.WHITE;
                return style;
            case BLUE:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLUE);
                style.dividerColor = Color.WHITE;
                return style;
            case GREEN:
                style.textColor = Color.WHITE;
                style.background = getBackground(GREEN);
                style.dividerColor = Color.WHITE;
                return style;
            default:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLACK);
                style.dividerColor = Color.WHITE;
                return style;
        }
    }

    /**
     * Returns a preset style with specified animations.     *     * @param styleType  {@link Style}     * @param animations {@link SuperToast.Animations}     * @return {@link Style}
     */
    public static Style getStyle(int styleType, SuperToast.Animations animations) {
        final Style style = new Style();
        style.animations = animations;
        switch (styleType) {
            case BLACK:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLACK);
                style.dividerColor = Color.WHITE;
                return style;
            case WHITE:
                style.textColor = Color.DKGRAY;
                style.background = getBackground(WHITE);
                style.dividerColor = Color.DKGRAY;
                style.buttonTextColor = Color.GRAY;
                return style;
            case GRAY:
                style.textColor = Color.WHITE;
                style.background = getBackground(GRAY);
                style.dividerColor = Color.WHITE;
                style.buttonTextColor = Color.GRAY;
                return style;
            case PURPLE:
                style.textColor = Color.WHITE;
                style.background = getBackground(PURPLE);
                style.dividerColor = Color.WHITE;
                return style;
            case RED:
                style.textColor = Color.WHITE;
                style.background = getBackground(RED);
                style.dividerColor = Color.WHITE;
                return style;
            case ORANGE:
                style.textColor = Color.WHITE;
                style.background = getBackground(ORANGE);
                style.dividerColor = Color.WHITE;
                return style;
            case BLUE:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLUE);
                style.dividerColor = Color.WHITE;
                return style;
            case GREEN:
                style.textColor = Color.WHITE;
                style.background = getBackground(GREEN);
                style.dividerColor = Color.WHITE;
                return style;
            default:
                style.textColor = Color.WHITE;
                style.background = getBackground(BLACK);
                style.dividerColor = Color.WHITE;
                return style;
        }
    }

    public static int getBackground(int style) {
        switch (style) {
            case BLACK:
                return (R.drawable.base_bg_toast_black);
            case WHITE:
                return (R.drawable.base_bg_toast_white);
            case GRAY:
                return (R.drawable.base_bg_toast_gray);
            case PURPLE:
                return (R.drawable.base_bg_toast_purple);
            case RED:
                return (R.drawable.base_bg_toast_red);
            case ORANGE:
                return (R.drawable.base_bg_toast_orange);
            case BLUE:
                return (R.drawable.base_bg_toast_blue);
            case GREEN:
                return (R.drawable.base_bg_toast_green);
            default:
                return (R.drawable.base_bg_toast_black);
        }
    }
}