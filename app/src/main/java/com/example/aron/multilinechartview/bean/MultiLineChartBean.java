package com.example.aron.multilinechartview.bean;

import android.graphics.Path;
import android.graphics.PointF;

import com.example.aron.multilinechartview.utils.PathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aron
 * @data 2017/12/28
 */

public class MultiLineChartBean implements Cloneable {

    private int mColor;
    private int mLineWidth;
    private String mTag = "";
    private List<MultiLineChartDataBean> mChartBeanList;
    private List<PointF> mPointList = new ArrayList<>();
    private Path mPath = new Path();

    public int getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.mLineWidth = lineWidth;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getTag() {
        return mTag;
    }

    public void setChartBeanList(List<MultiLineChartDataBean> chartBeanList) {
        mChartBeanList = chartBeanList;
    }

    public List<MultiLineChartDataBean> getChartBeanList() {
        return mChartBeanList;
    }

    public void setPointList(List<PointF> pointList) {
        mPointList.clear();
        mPointList.addAll(pointList);
    }

    /**
     * 获取path
     *
     * @param startX 开始的横坐标(原始尺寸)
     * @param stopX  结束的横坐标(原始尺寸)
     * @param dx     横坐标偏移量(缩放后的尺寸)
     * @param dy     纵坐标偏移量(缩放后的尺寸)
     * @param left   左侧横坐标限制
     * @param top    上方纵坐标限制( 暂不使用)
     * @param right  右侧横坐标限制
     * @param bottom 下方纵坐标限制
     * @param scale  缩放倍数
     * @return
     */
    public Path getPath(float startX, float stopX, float dx, float dy, float left, float top, float right, float bottom, float scale) {
        //重置path
        mPath.rewind();
        //如果没有数据,返回空的path
        if (mPointList.size() <= 0) {
            return mPath;
        }
        //用于保存将要计算的 坐标
        ArrayList<PointF> list = new ArrayList<>();
        for (int i = 0; i < mPointList.size(); i++) {
            float x = (left + dx + mPointList.get(i).x * scale);
            float y = (dy + bottom - mPointList.get(i).y * scale);
            boolean noStart = i == 0 || x < left;
            boolean isStop = x > right;
            if (noStart) {
                //处理  Path too large to be rendered into a texture 问题
                if (i < mPointList.size() - 1 && x < left) {
                    float nextX = (left + dx + mPointList.get(i + 1).x * scale);
                    float nextY = (dy + bottom - mPointList.get(i + 1).y * scale);
                    float k = (y - nextY) / (x - nextX);
                    float b = -(k * x - y);
                    x = left;
                    y = k * x + b;
                }

                //未开始一直替换 0 号index
                if (list.size() > 0) {
                    list.get(0).set(x, y);
                } else {
                    list.add(new PointF(x, y));
                }
            } else if (isStop) {
                //处理  Path too large to be rendered into a texture 问题
                if (i < mPointList.size() && x > right) {
                    float lastX = (left + dx + mPointList.get(i - 1).x * scale);
                    float lastY = (dy + bottom - mPointList.get(i - 1).y * scale);
                    float k = (lastY - y) / (lastX - x);
                    float b = -(k * x - y);
                    x = right;
                    y = k * x + b;
                }

                //停止时保存 index
                list.add(new PointF(x, y));
                break;
            } else {
                //正常保存
                list.add(new PointF(x, y));
            }
        }
        if (list.size() <= 1) {
            return mPath;
        }
        PathUtils.measurePath(mPath, list);
        return mPath;
    }

    public List<PointF> getPointList() {
        return mPointList;
    }


    public boolean isInvalid() {
        return mChartBeanList == null || mPointList.size() <= 0;
    }

    @Override
    public MultiLineChartBean clone() throws CloneNotSupportedException {
        return (MultiLineChartBean) super.clone();
    }
}
