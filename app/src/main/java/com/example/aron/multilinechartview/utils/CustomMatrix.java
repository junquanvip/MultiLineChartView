package com.example.aron.multilinechartview.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author aron
 * @data 2018/1/18
 */

public class CustomMatrix {
    private float[] mFloats = {
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
    };

    private float mWidth = 0, mHeight = 0, mShowWidth = 0, mShowHeight = 0,
            mMinScale = 1, mMaxScale = 3;

    public float[] getValues() {
        return mFloats;
    }


    @IntDef({Type.MSCALE_X, Type.MSKEW_X, Type.MTRANS_X,
            Type.MSKEW_Y, Type.MSCALE_Y, Type.MTRANS_Y,
            Type.MPERSP_0, Type.MPERSP_1, Type.MPERSP_2})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int MSCALE_X = 0;
        int MSKEW_X = 1;
        int MTRANS_X = 2;
        int MSKEW_Y = 3;
        int MSCALE_Y = 4;
        int MTRANS_Y = 5;
        int MPERSP_0 = 6;
        int MPERSP_1 = 7;
        int MPERSP_2 = 8;
    }

    public float getMaxScale() {
        return mMaxScale;
    }

    public void setWidth(float width, float showWidth) {
        mWidth = width;
        mShowWidth = showWidth;
    }

    public void setHeight(float height, float showHeight) {
        mHeight = height;
        mShowHeight = showHeight;
    }

    public void postTranslate(float dx, float dy) {
        if (mFloats[Type.MSCALE_X] <= mMinScale) {
            dy = 0;
        }
        mFloats[Type.MTRANS_X] += dx;
        mFloats[Type.MTRANS_Y] += dy;
        checkTrans();
    }

    public void postScale(float scale) {
        //获取实际平移x 与 y
        float originX = (mFloats[Type.MTRANS_X] - mShowWidth / 2) / mFloats[Type.MSCALE_X];
        float originY = (mFloats[Type.MTRANS_Y] + mShowHeight / 2) / mFloats[Type.MSCALE_Y];
        //缩放
        mFloats[Type.MSCALE_X] *= scale;
        mFloats[Type.MSCALE_Y] *= scale;
        //检查限制范围
        checkScale(scale);
        //变换平移位置
        mFloats[Type.MTRANS_X] = originX * mFloats[Type.MSCALE_X] + mShowWidth / 2;
        mFloats[Type.MTRANS_Y] = originY * mFloats[Type.MSCALE_Y] - mShowHeight / 2;
        //检查限制范围
        checkTrans();
    }

    private String toFloat() {
        return mFloats[0] + "       " + mFloats[1] + "      " + mFloats[2] + "      " +
                mFloats[3] + "      " + mFloats[4] + "      " + mFloats[5] + "      "
                + mFloats[6] + "        " + mFloats[7] + "      " + mFloats[8];
    }

    private float checkScale(float scale) {
        if (mFloats[Type.MSCALE_X] <= mMinScale) {
            //限制最小缩放范围
            mFloats[Type.MSCALE_X] = mMinScale;
            mFloats[Type.MSCALE_Y] = mMinScale;
            return mMinScale;
        } else if (mFloats[Type.MSCALE_X] >= mMaxScale) {
            //限制最大缩放范围
            mFloats[Type.MSCALE_X] = mMaxScale;
            mFloats[Type.MSCALE_Y] = mMaxScale;
            return mMaxScale;
        }
        return scale;
    }

    private void checkTrans() {
        float minTransX = 0, minTransY = 0;
        float maxTransX = mWidth * (mFloats[Type.MSCALE_X]) - mShowWidth;
        float maxTransY = mHeight * (mFloats[Type.MSCALE_Y]) - mShowHeight;
        if (mFloats[Type.MTRANS_X] >= minTransX) {
            //限制最左侧范围
            mFloats[Type.MTRANS_X] = minTransX;
        } else if (mFloats[Type.MTRANS_X] <= -maxTransX) {
            //限制最右侧范围
            mFloats[Type.MTRANS_X] = -maxTransX;
        }
        if (mFloats[Type.MTRANS_Y] >= maxTransY) {
            //限制最上方范围
            mFloats[Type.MTRANS_Y] = maxTransY;
        } else if (mFloats[Type.MTRANS_Y] <= minTransY) {
            //限制最下方范围
            mFloats[Type.MTRANS_Y] = minTransY;
        }
    }

    public float getValues(@Type int type) {
        return mFloats[type];
    }

}
