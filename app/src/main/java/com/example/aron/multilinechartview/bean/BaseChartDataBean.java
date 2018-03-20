package com.example.aron.multilinechartview.bean;

/**
 * @author aron
 * @data 2018/1/24
 */

public class BaseChartDataBean {
    public static final float INVALID = -1;

    private float mValue = INVALID;
    private String mText = "";

    public BaseChartDataBean() {
    }

    public BaseChartDataBean(float value, String text) {
        mValue = value;
        mText = text;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
