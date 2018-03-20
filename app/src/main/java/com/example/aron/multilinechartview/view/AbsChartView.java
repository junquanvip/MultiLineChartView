package com.example.aron.multilinechartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;


/**
 * @author aron
 * @data 2017/12/27
 */

public abstract class AbsChartView extends TemplateFrameLayout {

    public static final int BLUE = 0xFF6BBFDE;
    public static final int GREED = 0xFF84CB86;
    public static final int ORANGE = 0xFFF79E3C;

    protected int mWidth;
    protected int mHeight;
    //todo 血压图 血糖图 需要 public 后期修改为使用 baseView 的customView
    public int mMargin = 30;
    public int mPaddingLeft = 20;
    public int mPaddingTop = 0;
    public int mPaddingRight = 20;
    public int mPaddingBottom = 38;

    protected int mBackgroundColor = BLUE;         //背景的颜色
    protected int mBackgroundAngle = 10;            //背景圆角度

    //todo 血压图 血糖图 需要 public 后期修改为使用 baseView 的customView
    public int mTitleHeight = 58;            //标题高度
    public int mTitleBorderWidth = 2;            //标题下方线条宽度
    protected int mBorderColor = 0x66FFFFFF;        //标题下方线条颜色

    protected String mTitle;            //标题文字
    protected String mLeftTitle;        //左侧标题文字
    protected String mRightTitle;       //右侧标题文字
    protected String mLeftTopTitle;     //左侧上方标题文字
    protected String mLeftBottomTitle;  //左侧下方标题文字
    protected String mRightTopTitle;     //右侧上方标题文字
    protected String mRightBottomTitle;  //右侧下方标题文字
    protected String mBottomRightTitle;  //右侧下方标题文字

    protected int mTitleTextSize = 24;            //标题文字大小
    protected int mLeftTitleSize = 24;        //左侧标题文字大小
    protected int mRightTitleSize = 24;       //右侧标题文字大小
    protected int mLeftTopTitleSize = 23;     //左侧上方标题文字大小
    protected int mLeftBottomTitleSize = 17;  //左侧下方标题文字大小
    protected int mRightTopTitleSize = 21;     //右侧上方标题文字大小
    protected int mRightBottomTitleSize = 21;  //右侧下方标题文字大小

    protected int mLeftTitleSpace = 3;      //左侧标题文字间隔
    protected int mRightTitleSpace = 3;     //右侧标题文字间隔


    protected int mTitleColor = 0xFFFFFFFF;            //标题文字颜色
    protected int mLeftTitleColor = 0xFFFFFFFF;        //左侧标题文字颜色
    protected int mRightTitleColor = 0xFFFFFFFF;       //右侧标题文字颜色
    protected int mLeftTopTitleColor = 0xFFFFFFFF;     //左侧上方标题文字颜色
    protected int mLeftBottomTitleColor = 0xFFFFFFFF;  //左侧下方标题文字颜色
    protected int mRightTopTitleColor = 0xFFFFFFFF;     //右侧上方标题文字颜色
    protected int mRightBottomTitleColor = 0xFFFFFFFF;  //右侧下方标题文字颜色


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawTitle(canvas);
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    protected void drawBackground(Canvas canvas) {

        RectF rectF = new RectF(changeSize(mMargin), changeSize(mMargin), getWidth() - changeSize(mMargin), getHeight() - changeSize(mMargin));

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rectF, changeSize(mBackgroundAngle), changeSize(mBackgroundAngle), paint);

    }

    /**
     * 绘制标题
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(changeSize(mTitleTextSize));
        paint.setTypeface(CustomType.getTypeface());
        paint.setColor(mTitleColor);
        if (!TextUtils.isEmpty(mTitle)) {       //中部标题
            canvas.drawText(mTitle, getWidth() / 2 - (paint.measureText(mTitle) / 2),
                    changeSize(mMargin) + changeSize(mPaddingTop) + changeSize(mTitleHeight) - paint.getTextSize(), paint);
        }
        if (!TextUtils.isEmpty(mLeftTitle)) {    //左侧标题
            canvas.drawText(mLeftTitle, changeSize(mMargin) + changeSize(mPaddingRight),
                    changeSize(mMargin) + changeSize(mPaddingTop) + changeSize(mTitleHeight) - paint.getTextSize(), paint);
        }
        if (!TextUtils.isEmpty(mRightTitle)) {   //右侧标题
            canvas.drawText(mRightTitle, getWidth() - changeSize(mMargin) - changeSize(mPaddingRight) - paint.measureText(mRightTitle),
                    changeSize(mMargin) + changeSize(mPaddingTop) + changeSize(mTitleHeight) - paint.getTextSize(), paint);
        }
        //左侧双层标题
        if (!TextUtils.isEmpty(mLeftTopTitle) && !TextUtils.isEmpty(mLeftBottomTitle)) {
            float space = changeSize(mTitleHeight) - (changeSize(mLeftTopTitleSize) + changeSize(mLeftBottomTitleSize) + changeSize(mLeftTitleSpace));
            paint.setTextSize(changeSize(mLeftTopTitleSize));
            canvas.drawText(mLeftTopTitle, changeSize(mMargin) + changeSize(mPaddingLeft),
                    changeSize(mMargin) + changeSize(mPaddingTop) + space / 2 + changeSize(mLeftTopTitleSize), paint);
            paint.setTextSize(changeSize(mLeftBottomTitleSize));
            canvas.drawText(mLeftBottomTitle, changeSize(mMargin) + changeSize(mPaddingLeft),
                    changeSize(mMargin) + changeSize(mPaddingTop) + space / 2 + changeSize(mLeftTopTitleSize) + changeSize(mLeftTitleSpace) + changeSize(mLeftBottomTitleSize), paint);
        }
        //右侧双层标题
        if (!TextUtils.isEmpty(mRightTopTitle) && !TextUtils.isEmpty(mRightBottomTitle)) {
            float space = changeSize(mTitleHeight) - (changeSize(mRightTopTitleSize) + changeSize(mRightBottomTitleSize) + changeSize(mRightTitleSpace));
            paint.setTextSize(changeSize(mRightTopTitleSize));
            canvas.drawText(mRightTopTitle, mWidth - changeSize(mMargin) - changeSize(mPaddingRight) - paint.measureText(mRightTopTitle),
                    changeSize(mMargin) + changeSize(mPaddingTop) + space / 2 + changeSize(mRightTopTitleSize), paint);
            paint.setTextSize(changeSize(mRightBottomTitleSize));
            canvas.drawText(mRightBottomTitle, mWidth - changeSize(mMargin) - changeSize(mPaddingRight) - paint.measureText(mRightBottomTitle),
                    changeSize(mMargin) + changeSize(mPaddingTop) + space / 2 + changeSize(mRightTopTitleSize) + changeSize(mRightTitleSpace) + changeSize(mRightBottomTitleSize), paint);
        }
        //右侧下方标题
        if (!TextUtils.isEmpty(mBottomRightTitle)) {
            canvas.drawText(mBottomRightTitle, getWidth() - changeSize(mMargin) - changeSize(mPaddingRight) - paint.measureText(mBottomRightTitle),
                    mHeight - changeSize(mMargin) / 2 - paint.getTextSize(), paint);
        }
        if (mTitleBorderWidth>0) {
            paint.setColor(mBorderColor);
            paint.setStrokeWidth(changeSize(mTitleBorderWidth));
            canvas.drawLine(changeSize(mPaddingLeft + mMargin), changeSize(mTitleHeight + mMargin),
                    getWidth() - changeSize(mPaddingRight + mMargin), changeSize(mTitleHeight + mMargin), paint);
        }
    }

    /**
     * 使用自定义的视图进行填充
     *
     * @param child
     */
    public void addCustomView(View child) {
        int left = changeSize(mMargin) + changeSize(mPaddingLeft);
        int top = changeSize(mMargin) + changeSize(mPaddingTop) + changeSize(mTitleHeight) + changeSize(mTitleBorderWidth);
        int right = changeSize(mMargin) + changeSize(mPaddingRight);
        int bottom = changeSize(mMargin) + changeSize(mPaddingBottom);
        addCustomView(child, left, top, right, bottom);
    }

    /**
     * 使用自定义的视图进行填充
     *
     * @param child
     */
    public void addCustomView(View child, int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(left, top, right, bottom);
        child.setLayoutParams(layoutParams);
        super.addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public AbsChartView(@NonNull Context context) {
        super(context);
    }

}
