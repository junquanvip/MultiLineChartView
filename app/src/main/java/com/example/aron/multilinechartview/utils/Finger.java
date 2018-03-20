package com.example.aron.multilinechartview.utils;

import android.graphics.PointF;

/**
 * @author aron
 * @data 2018/1/18
 */

public class Finger {
    private int mId;
    private PointF mCurrentPointF = new PointF();
    private PointF mFirstPointF = new PointF();
    private PointF mLastPointF = new PointF();

    public Finger(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setFirstPointF(float pointX, float pointY) {
        mFirstPointF.set(pointX, pointY);
        mLastPointF.set(pointX, pointY);
        mCurrentPointF.set(pointX, pointY);
    }

    public void setCurrentPointF(float pointX, float pointY) {
        mLastPointF.set(mCurrentPointF.x, mCurrentPointF.y);
        mCurrentPointF.set(pointX, pointY);
    }


    public float getCurrentPointX() {
        return mCurrentPointF.x;
    }

    public float getCurrentPointY() {
        return mCurrentPointF.y;
    }

    public float getLastPointX() {
        return mLastPointF.x;
    }

    public float getLastPointY() {
        return mLastPointF.y;
    }


    public float getFirstPointX() {
        return mFirstPointF.x;
    }

    public float getFirstPointY() {
        return mFirstPointF.y;
    }


    public void reset() {
        mFirstPointF.set(0, 0);
        mCurrentPointF.set(0, 0);
    }

}
