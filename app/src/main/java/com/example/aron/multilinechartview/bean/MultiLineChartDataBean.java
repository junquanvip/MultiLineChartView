package com.example.aron.multilinechartview.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aron
 * @data 2017/12/27
 */

public class MultiLineChartDataBean extends BaseChartDataBean {

    public MultiLineChartDataBean() {
        super();
    }

    public MultiLineChartDataBean(float value, String text) {
        super(value, text);
    }


    private List<SecondaryBean> mSecondaryBeanList = new ArrayList<>();

    public List<SecondaryBean> getSecondary() {
        return mSecondaryBeanList;
    }

    public void addSecondary(SecondaryBean chartData) {
        mSecondaryBeanList.add(chartData);
    }

    public static class SecondaryBean {
        private float mIndex;
        private float mValue;

        public SecondaryBean(float floatIndex, float floatValue) {
            mIndex = floatIndex;
            mValue = floatValue;
        }

        public float getIndex() {
            return mIndex;
        }

        public void setIndex(float index) {
            mIndex = index;
        }

        public float getValue() {
            return mValue;
        }

        public void setValue(float value) {
            mValue = value;
        }
    }
}
