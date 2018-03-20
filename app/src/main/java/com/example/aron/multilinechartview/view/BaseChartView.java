package com.example.aron.multilinechartview.view;

import android.content.Context;
import android.view.View;


/**
 * 基本view
 *
 * @author :   aron
 * @date :   2017/5/2
 */

public class BaseChartView<V extends BaseChartView> extends AbsChartView implements Cloneable {
//--------------------     是否固定高度   ----------------------------------------------------------

    public boolean mIsFixHeight = true;

    public V setFixHeight(boolean verticalScreen) {
        mIsFixHeight = verticalScreen;
        return (V) this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsFixHeight) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(changeSize(mTemplateHeight), View.MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


//--------------------     处理克隆事件   ----------------------------------------------------------

    @Override
    public BaseChartView clone() {
        throw new RuntimeException("不能直接克隆 BaseView");
    }

//--------------------     处理点击事件   ----------------------------------------------------------

    private boolean mEnableClick = true;

    public void enableClick(boolean enableClick) {
        mEnableClick = enableClick;
    }

    @Override
    public boolean performClick() {
        return mEnableClick && super.callOnClick();
    }


//--------------------       构造方法     ----------------------------------------------------------

    public BaseChartView(Context context) {
        super(context);
    }

//--------------------  提供给子类的接口  ----------------------------------------------------------

    public V setBackgroundAngle(int backgroundAngle) {
        mBackgroundAngle = backgroundAngle;
        return (V) this;
    }

    public V setTitleHeight(int titleHeight) {
        mTitleHeight = titleHeight;
        return (V) this;
    }

    public V setLeftTopTitle(String leftTopTitle) {
        mLeftTopTitle = leftTopTitle;
        return (V) this;
    }

    public V setLeftBottomTitle(String leftBottomTitle) {
        mLeftBottomTitle = leftBottomTitle;
        return (V) this;
    }

    public V setRightTopTitle(String rightTopTitle) {
        mRightTopTitle = rightTopTitle;
        return (V) this;
    }

    public V setRightBottomTitle(String rightBottomTitle) {
        mRightBottomTitle = rightBottomTitle;
        return (V) this;
    }

    public V setTitleTextSize(int titleTextSize) {
        mTitleTextSize = titleTextSize;
        return (V) this;
    }

    public V setLeftTitleSize(int leftTitleSize) {
        mLeftTitleSize = leftTitleSize;
        return (V) this;
    }

    public V setRightTitleSize(int rightTitleSize) {
        mRightTitleSize = rightTitleSize;
        return (V) this;
    }

    public V setLeftTopTitleSize(int leftTopTitleSize) {
        mLeftTopTitleSize = leftTopTitleSize;
        return (V) this;
    }

    public V setLeftBottomTitleSize(int leftBottomTitleSize) {
        mLeftBottomTitleSize = leftBottomTitleSize;
        return (V) this;
    }

    public V setRightTopTitleSize(int rightTopTitleSize) {
        mRightTopTitleSize = rightTopTitleSize;
        return (V) this;
    }

    public V setRightBottomTitleSize(int rightBottomTitleSize) {
        mRightBottomTitleSize = rightBottomTitleSize;
        return (V) this;
    }

    public V setLeftTitleSpace(int leftTitleSpace) {
        mLeftTitleSpace = leftTitleSpace;
        return (V) this;
    }

    public V setRightTitleSpace(int rightTitleSpace) {
        mRightTitleSpace = rightTitleSpace;
        return (V) this;
    }

    public V setTitleColor(int titleColor) {
        mTitleColor = titleColor;
        return (V) this;
    }

    public V setLeftTitleColor(int leftTitleColor) {
        mLeftTitleColor = leftTitleColor;
        return (V) this;
    }

    public V setRightTitleColor(int rightTitleColor) {
        mRightTitleColor = rightTitleColor;
        return (V) this;
    }

    public V setLeftTopTitleColor(int leftTopTitleColor) {
        mLeftTopTitleColor = leftTopTitleColor;
        return (V) this;
    }

    public V setLeftBottomTitleColor(int leftBottomTitleColor) {
        mLeftBottomTitleColor = leftBottomTitleColor;
        return (V) this;
    }

    public V setRightTopTitleColor(int rightTopTitleColor) {
        mRightTopTitleColor = rightTopTitleColor;
        return (V) this;
    }

    public V setRightBottomTitleColor(int rightBottomTitleColor) {
        mRightBottomTitleColor = rightBottomTitleColor;
        return (V) this;
    }

    public V setTitleBorderWidth(int titleBorderWidth) {
        mTitleBorderWidth = titleBorderWidth;
        return (V) this;
    }

    /**
     * 设置标题下方线条颜色
     *
     * @param backgroundColor
     * @return
     */
    public V setBorderColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        return (V) this;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor
     * @return
     */
    public V setBackground(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setTitle(String text) {
        mTitle = text;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setLeftTitle(String text) {
        mLeftTitle = text;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setLeftTitle(String topTitle, String bottomTitle) {
        mLeftTopTitle = topTitle;
        mLeftBottomTitle = bottomTitle;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setRightTitle(String topTitle, String bottomTitle) {
        mRightTopTitle = topTitle;
        mRightBottomTitle = bottomTitle;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setRightTitle(String text) {
        mRightTitle = text;
        return (V) this;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public V setBottomRightTitle(String text) {
        mBottomRightTitle = text;
        return (V) this;
    }

    /**
     * 设置模板高度
     *
     * @return
     */
    public V setTemplateHeight(float templateHeight) {
        mTemplateHeight = templateHeight;
        return (V) this;
    }

    /**
     * 设置模板高度
     *
     * @return
     */
    public V setMargin(int margin) {
        mMargin = margin;
        return (V) this;
    }

    /**
     * 获取左侧padding值 相对于customView
     *
     * @return 未转化
     */
    public int getCustomPaddingLeft() {
        return mMargin + mPaddingLeft;
    }

    /**
     * 获取左侧padding值 相对于customView
     *
     * @return 未转化
     */
    public int getCustomPaddingTop() {
        return mMargin + mPaddingTop + mTitleHeight + mTitleBorderWidth;
    }

    /**
     * 获取左侧padding值 相对于customView
     *
     * @return 未转化
     */
    public int getCustomPaddingRight() {
        return mMargin + mPaddingRight;
    }

    /**
     * 获取左侧padding值 相对于customView
     *
     * @return 未转化
     */
    public int getCustomPaddingBottom() {
        return mMargin + mPaddingBottom;
    }

    /**
     * 重新绘制视图
     */
    public void notifyChangeView() {
        this.invalidate();
    }

}
