package com.example.aron.multilinechartview.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import static com.example.aron.multilinechartview.Constant.DEFAULT_TEMPLATE_HEIGHT;
import static com.example.aron.multilinechartview.Constant.DEFAULT_TEMPLATE_WIDTH;

/**
 * @author xc
 * @data 2017/12/27
 */

public class TemplateFrameLayout extends FrameLayout {
    protected float mTemplateWidth = DEFAULT_TEMPLATE_WIDTH;    //模板宽度
    protected float mTemplateHeight = DEFAULT_TEMPLATE_HEIGHT;    //模板高度
    protected float mBaseMultiple;      //基本缩放比例

    public TemplateFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }


    private void init() {
        setWillNotDraw(false);
        if (getContext() instanceof Activity) {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            mBaseMultiple = screenWidth / mTemplateWidth;
        } else {
            mBaseMultiple = 1;
        }
    }

    /**
     * 动态修改尺寸
     *
     * @param size
     * @return
     */
    public final int changeSize(float size) {
        return (int) (size * mBaseMultiple);
    }
}
