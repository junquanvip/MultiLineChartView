package com.example.aron.multilinechartview.utils;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author aron
 * @data 2017/12/27
 */

public class PathUtils {

    /**
     * 测量曲线路径
     *
     * @param path      保存曲线路径
     * @param pointList
     * @return
     */
    public static Path measurePath(@NonNull Path path, @NonNull List<PointF> pointList) {
        //小于两个位置的情况
        if (pointList.size() <= 2) {
            return measurePathForSmallerThanTwo(path, pointList);
        }

        //保存辅助线路径
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;
        float lineSmoothness = 0.13f;
        final int lineSize = pointList.size();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                PointF point = pointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    PointF point = pointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    PointF point = pointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                PointF point = pointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                path.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX;
                final float firstControlPointY;
                final float secondControlPointX;
                final float secondControlPointY;
                if (previousPointY == currentPointY) {
                    firstControlPointX = previousPointX;
                    firstControlPointY = previousPointY;
                    secondControlPointX = currentPointX;
                    secondControlPointY = currentPointY;
                } else {
                    firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                    firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                    secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                    secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                }
                //画出曲线
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        return path;
    }

    private static Path measurePathForSmallerThanTwo(@NonNull Path path, List<PointF> pointList) {
        for (int valueIndex = 0; valueIndex < pointList.size(); ++valueIndex) {
            PointF point = pointList.get(valueIndex);
            if (valueIndex == 0) {
                // 将Path移动到开始点
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
    }




    /**
     * 测量曲线路径
     *
     * @param path      保存曲线路径
     * @param pointList
     * @return
     */
    public static Path measurePath2(@NonNull Path path, @NonNull List<PointF> pointList) {
        //小于两个位置的情况
        if (pointList.size() <= 2) {
            return measurePathForSmallerThanTwo(path, pointList);
        }

        //保存辅助线路径
        Path assistPath = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;
        float lineSmoothness = 0.13f;
        final int lineSize = pointList.size();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                PointF point = pointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    PointF point = pointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    PointF point = pointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                PointF point = pointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                path.moveTo(currentPointX, currentPointY);
                assistPath.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX;
                final float firstControlPointY;
                final float secondControlPointX;
                final float secondControlPointY;
                if (previousPointY == currentPointY) {
                    firstControlPointX = previousPointX;
                    firstControlPointY = previousPointY;
                    secondControlPointX = currentPointX;
                    secondControlPointY = currentPointY;
                } else {
                    firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                    firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                    secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                    secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                }
                //画出曲线
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
                //将控制点保存到辅助路径上
                assistPath.lineTo(firstControlPointX, firstControlPointY);
                assistPath.lineTo(secondControlPointX, secondControlPointY);
                assistPath.lineTo(currentPointX, currentPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        return path;
    }

}
