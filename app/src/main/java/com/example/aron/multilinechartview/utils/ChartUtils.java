package com.example.aron.multilinechartview.utils;

import com.example.aron.multilinechartview.bean.MultiLineChartDataBean;

import java.util.List;
/**
 * @author xc
 * @data 2017/12/27
 */

public class ChartUtils {

    public static float getMaxValue(List<? extends MultiLineChartDataBean> list, float maxValue) {
        if (list == null || list.size() <= 0) {
            return maxValue;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() > maxValue) {
                maxValue = list.get(i).getValue();
            }
        }
        return maxValue;
    }

    public static float getMinValue(List<? extends MultiLineChartDataBean> list, float minValue) {
        if (list == null || list.size() <= 0) {
            return minValue;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == MultiLineChartDataBean.INVALID) {
                continue;
            }
            if (list.get(i).getValue() < minValue) {
                minValue = list.get(i).getValue();
            }
        }
        return minValue;
    }
}
