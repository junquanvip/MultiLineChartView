package com.example.aron.multilinechartview.utils;

/**
 * @author aron
 * @Data 2017/12/4
 */

public class ClickUtils {

    private static long sLastClickTime = -1;
    private static final int CLICK_INTERVAL = 500;

    /**
     * 是否可以点击
     */
    public static boolean isClick() {
        if (System.currentTimeMillis() - sLastClickTime > CLICK_INTERVAL) {
            sLastClickTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
