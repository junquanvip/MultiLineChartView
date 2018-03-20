package com.example.aron.multilinechartview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.OverScroller;
import com.example.aron.multilinechartview.ChartViewShowActivity;
import com.example.aron.multilinechartview.bean.MultiLineChartBean;
import com.example.aron.multilinechartview.bean.MultiLineChartDataBean;
import com.example.aron.multilinechartview.utils.ChartUtils;
import com.example.aron.multilinechartview.utils.CustomMatrix;
import com.example.aron.multilinechartview.utils.Finger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aron
 * @data 2018/1/17
 * 缩放折线图
 */

public class MultiLineChartView extends BaseChartView<MultiLineChartView> {
    private static final float RESISTANCE = 0.3f;
    private static final int DURATION = 400;
    private static final int CLICK_THRESHOLD = 5;

    private final Util mUtil = new Util();
    private final Measure mMeasure = new Measure();
    private final Draw mDraw = new Draw();
    private final Touch mTouch = new Touch();
    private final OverScroller mScroller;


    /**
     * 保存多条线
     */
    private List<MultiLineChartBean> mDataBeanList = new ArrayList<>();
    private String mBottomText = "年龄", mLeftText = "身高(cm)";
    private List<String> mUnitText = new ArrayList<>();

    private int mGridLineColor = 0xFFEBEBEB;
    private int mLeftSpace = 23, mTopSpace, mRightSpace = 23, mBottomSpace = -mPaddingBottom;
    //左侧刻度总数  左侧刻度最小值   左侧刻度最大值
    private int mValueCount = 20, mMinValue = 0, mMaxValue = 150;
    private int mLeft, mTop, mRight, mBottom;

    public MultiLineChartView(Context context) {
        super(context);
        setTitleBorderWidth(0);
        // 获取TouchSlop值
        mScroller = new OverScroller(context);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartViewShowActivity.showView(MultiLineChartView.this);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLeft = changeSize(getCustomPaddingLeft()) + changeSize(mLeftSpace);
        mTop = changeSize(getCustomPaddingTop()) + changeSize(mTopSpace);
        mRight = changeSize(getCustomPaddingRight()) + changeSize(mRightSpace);
        mBottom = changeSize(getCustomPaddingBottom());
        float contentWidth = Math.abs(mWidth - mRight - mLeft);
        float contentHeight = Math.abs(mHeight - mBottom - mTop);
        mTouch.setHeight(contentHeight, contentHeight);
        mTouch.setWidth((mUnitText.size() + 3) * mUtil.getEachSquareWidth(), contentWidth);
        mMeasure.measurePath();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //静态绘制
        mDraw.drawBackground(canvas, mLeft, mTop, canvas.getWidth() - mRight, canvas.getHeight() - mBottom);
        mDraw.drawPromptInfo(canvas);
        //动态绘制
        mDraw.drawScale(canvas, mLeft, mTop, canvas.getWidth() - mRight, canvas.getHeight() - mBottom);
        mDraw.drawLines(canvas, mLeft, mTop, canvas.getWidth() - mRight, canvas.getHeight() - mBottom);
        mDraw.drawTag(canvas, mLeft, mTop, canvas.getWidth() - mRight, canvas.getHeight() - mBottom);
        mDraw.drawDot(canvas, mLeft, mTop, canvas.getWidth() - mRight, canvas.getHeight() - mBottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean bool = mTouch.onTouch(event);
        if (bool) {
            notifyChangeView();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            mTouch.postTranslate(mScroller.getCurrX() - mScroller.getStartX(), mScroller.getCurrY() - mScroller.getStartY());
            notifyChangeView();
        }
    }

    private class Util {

        /**
         * 单个方块 的高度
         */
        private float getEachSquareWidth() {
            return (mHeight - mTop - mBottom) / mValueCount;
        }

        /**
         * 单个单位 的高度
         */
        private float getSingleUnitHeight() {
            return (mHeight - mTop - mBottom) / (mMaxValue * 1.1f - mMinValue);
        }

        /**
         * 每份单位的差值
         */
        private float getEachValue() {
            return (mMaxValue * 1.1f - mMinValue) / mValueCount;
        }

        private float float2X(float left, float tranX, float scale, float x) {
            return left + tranX + scale * x;
        }

        private float float2Y(float bottom, float tranY, float scale, float y) {
            return tranY + bottom - scale * y;
        }

    }

    private class Measure {
        /**
         * 计算坐标点
         */
        private void measurePath() {
            float singleUnitHeight = mUtil.getSingleUnitHeight();
            for (int i = 0; i < mDataBeanList.size(); i++) {
                ArrayList<PointF> pointList = measurePath(mUtil.getEachSquareWidth(), singleUnitHeight, mDataBeanList.get(i).getChartBeanList());
                mDataBeanList.get(i).setPointList(pointList);
            }
        }

        private ArrayList<PointF> measurePath(float eachWidth, float eachSquareWidth, @NonNull List<MultiLineChartDataBean> list) {
            ArrayList<PointF> pointList = new ArrayList<>();
            for (int chartIndex = 0; chartIndex < list.size(); chartIndex++) {
                if (list.get(chartIndex).getValue() > 0) {
                    //主数据
                    float x = eachWidth * chartIndex;
                    float y = list.get(chartIndex).getValue() - mMinValue;
                    PointF point = new PointF(x, y * eachSquareWidth);
                    pointList.add(point);
                }
                List<MultiLineChartDataBean.SecondaryBean> secondary = list.get(chartIndex).getSecondary();
                if (secondary != null && secondary.size() > 0) {
                    //次要的数据
                    float x = eachWidth * chartIndex;
                    for (int secondaryIndex = 0; secondaryIndex < secondary.size(); secondaryIndex++) {
                        if (secondary.get(secondaryIndex).getValue() > 0) {
                            float secondaryX = secondary.get(secondaryIndex).getIndex() * eachSquareWidth;
                            float secondaryY = secondary.get(secondaryIndex).getValue() - mMinValue;
                            PointF secondaryPoint = new PointF(x + secondaryX, secondaryY * eachSquareWidth);
                            pointList.add(secondaryPoint);
                        }
                    }
                }
            }
            return pointList;
        }
    }

    private class Draw {

        /**
         * 绘制提示信息
         *
         * @param canvas 画布
         */
        private void drawPromptInfo(Canvas canvas) {
            canvas.save();
            canvas.rotate(-90, canvas.getHeight() / 2, canvas.getHeight() / 2);

            //绘制提示
            Paint textPaint = CustomType.getPaint(changeSize(10), false);
            //左侧
            canvas.drawText(mLeftText, canvas.getHeight() / 2 - textPaint.measureText(mLeftText) / 2,
                    changeSize(mMargin) + textPaint.getTextSize() + changeSize(2), textPaint);
            canvas.restore();
            //下方
            canvas.drawText(mBottomText, canvas.getWidth() / 2 - textPaint.measureText(mBottomText) / 2,
                    canvas.getHeight() - changeSize(mMargin) - textPaint.getTextSize(), textPaint);
        }

        /**
         * 绘制小箭头
         *
         * @param canvas 画布
         * @param left   绘制起始坐标
         * @param top    绘制起始坐标
         * @param right  绘制起始坐标
         * @param bottom 绘制起始坐标
         */
        private void drawBackground(Canvas canvas, int left, int top, int right, int bottom) {
            //画个三角形
            Paint paint = CustomType.getPaint();
            Path path = new Path();
            //左侧
            path.moveTo(left, top - changeSize(18));
            path.lineTo(left + changeSize(5), top - changeSize(8));
            path.lineTo(left - changeSize(5), top - changeSize(8));
            path.close();
            //右侧
            path.moveTo(right, top - changeSize(18));
            path.lineTo(right + changeSize(5), top - changeSize(8));
            path.lineTo(right - changeSize(5), top - changeSize(8));
            path.close();

            canvas.drawPath(path, paint);
            canvas.drawLine(left, top - changeSize(18), left, bottom, paint);
            canvas.drawLine(right, top - changeSize(18), right, bottom, paint);

            canvas.drawLine(left, top, right, top, paint);
            canvas.drawLine(left, bottom, right, bottom, paint);

            paint.setColor(Color.WHITE);
            canvas.drawRect(left, top, right, bottom, paint);

        }

        /**
         * 绘制刻度
         *
         * @param canvas 画布
         * @param left   绘制起始坐标
         * @param top    绘制起始坐标
         * @param right  绘制起始坐标
         * @param bottom 绘制起始坐标
         */
        private void drawScale(Canvas canvas, int left, int top, int right, int bottom) {
            Paint paint = CustomType.getPaint(changeSize(10), false);
            Paint paint2 = CustomType.getPaint(changeSize(10), false);
            paint2.setColor(mGridLineColor);
            float width = Math.abs(right - left);
            float height = Math.abs(bottom - top);
            float eachWidth = mUtil.getEachSquareWidth() * mTouch.getScale();
            float eachValue = mUtil.getEachValue();
            //绘制文字
            canvas.save();
            //绘制左右两侧文字
            canvas.clipRect(left - paint.measureText("9999"), top - paint.getTextSize(), right + paint.measureText("9999"), bottom + paint.getTextSize() * 1.2f);
            for (int i = 0; i < mValueCount + 1; i++) {
                //由下向上绘制
                String text = String.valueOf(Math.round(mMinValue + eachValue * i));
                float x = left - paint.measureText(text) - changeSize(3);
                float x2 = right + changeSize(3);
                float y = bottom - eachWidth * (i) + paint.measureText(text) / 3 + mTouch.getTranY();
                canvas.drawText(text, x, y, paint);
                canvas.drawText(text, x2, y, paint);
            }
            //绘制下方文字
            canvas.clipRect(left, top - paint.getTextSize(), right, bottom + paint.getTextSize() * 1.2f);
            for (int i = 0; i < mUnitText.size(); i++) {
                String text = mUnitText.get(i);
                float x = left + mTouch.getTranX();
                if (i == 0) {
                    x += eachWidth * i;
                } else if (i == mUnitText.size()) {
                    x += eachWidth * i - paint.measureText(text);
                } else {
                    x += eachWidth * i - paint.measureText(text) / 2;
                }
                float y = bottom + paint.getTextSize();
                canvas.drawText(text, x, y, paint);
            }
            //绘制网格
            canvas.clipRect(left, top, right, bottom);
            for (int i = 0; i < Math.ceil(height / eachWidth) + 1; i++) {
                float y = bottom - eachWidth * (i) + mTouch.getTranY() % eachWidth;
                canvas.drawLine(left, y, right, y, paint2);
            }
            for (int i = 0; i < Math.ceil(width / eachWidth) + 1; i++) {
                float x = left + (eachWidth * i) + mTouch.getTranX() % eachWidth;
                canvas.drawLine(x, top, x, bottom, paint2);
            }
            canvas.restore();
        }

        /**
         * 绘制趋势线条
         *
         * @param canvas 画布
         * @param left   绘制起始坐标
         * @param top    绘制起始坐标
         * @param right  绘制起始坐标
         * @param bottom 绘制起始坐标
         */
        private void drawLines(Canvas canvas, int left, int top, int right, int bottom) {
            Paint paint = CustomType.getPaint();
            paint.setStyle(Paint.Style.STROKE);
            canvas.save();
            canvas.clipRect(left, top, right, bottom);
            for (int i = 0; i < mDataBeanList.size(); i++) {
                MultiLineChartBean dataBean = mDataBeanList.get(i);
                if (dataBean.isInvalid()) {
                    continue;
                }
                paint.setColor(mDataBeanList.get(i).getColor());
                paint.setStrokeWidth(mDataBeanList.get(i).getLineWidth());
                Path path = mDataBeanList.get(i).getPath(-mTouch.getTranX() / mTouch.getScale(),
                        -mTouch.getTranX() / mTouch.getScale() + right, mTouch.getTranX(), mTouch.getTranY(),
                        left, top, right, bottom, mTouch.getScale());
                canvas.drawPath(path, paint);
            }
            canvas.restore();
        }

        /**
         * 绘制右侧提示文字
         *
         * @param canvas
         */
        private void drawTag(Canvas canvas, int left, int top, int right, int bottom) {
            Paint paint = CustomType.getPaint(changeSize(8), false);
            canvas.save();
            canvas.clipRect(left, top, right, bottom);
            for (int i = 0; i < mDataBeanList.size(); i++) {
                MultiLineChartBean dataBean = mDataBeanList.get(i);
                if (dataBean.isInvalid()) {
                    continue;
                }
                String tag = dataBean.getTag();
                PointF point = dataBean.getPointList().get(dataBean.getPointList().size() - 1);
                paint.setColor(dataBean.getColor());
                canvas.drawText(tag, mUtil.float2X(left, mTouch.getTranX(), mTouch.getScale(), point.x) + paint.getTextSize() / 2,
                        mUtil.float2Y(bottom, mTouch.getTranY(), mTouch.getScale(), point.y) + paint.getTextSize() * 0.33f, paint);
            }
            canvas.restore();
        }

        /**
         * 绘制小圆点
         *
         * @param canvas 画布
         */
        private void drawDot(Canvas canvas, int left, int top, int right, int bottom) {
            Paint paint = CustomType.getPaint();
            canvas.save();
            canvas.clipRect(left, top, right, bottom);
            for (int i = 0; i < mDataBeanList.size(); i++) {
                List<PointF> pointList = mDataBeanList.get(i).getPointList();
                paint.setColor(mDataBeanList.get(i).getColor());
                for (int j = 0; j < pointList.size(); j++) {
                    PointF point = pointList.get(j);
                    canvas.drawCircle(mUtil.float2X(left, mTouch.getTranX(), mTouch.getScale(), point.x),
                            mUtil.float2Y(bottom, mTouch.getTranY(), mTouch.getScale(), point.y),
                            changeSize(1.5f), paint);
                }
            }
            canvas.restore();
        }

    }

    private class Touch {
        private boolean mIsBeingDragged = true;
        private final int mTouchSlop;
        float mDeltaX = 0, mDeltaY = 0;

        private SparseArray<Finger> mFingers = new SparseArray<>();
        private CustomMatrix mMatrix = new CustomMatrix();
        private float mWidth = 0, mHeight = 0;
        private View.OnClickListener mOnClickListener;


        private Touch() {
            //1号手指  id固定
            mFingers.put(0, new Finger(0));
            //2号手指  id固定
            mFingers.put(1, new Finger(1));
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }

        private void setWidth(float width, float showWidth) {
            mWidth = width;
            mMatrix.setWidth(width, showWidth);
        }

        private void setHeight(float height, float showHeight) {
            mHeight = height;
            mMatrix.setHeight(height, showHeight);
        }


        private boolean onTouch(MotionEvent event) {
            final ViewParent parent = getParent();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    mIsBeingDragged = !mScroller.isFinished();
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        Finger finger = mFingers.get(event.getPointerId(i));
                        //出现好几个手指,忽略
                        if (finger != null) {
                            mFingers.get(event.getPointerId(i)).setFirstPointF(event.getX(i), event.getY(i));
                        }
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        int pointerId = event.getPointerId(i);
                        if (mFingers.get(pointerId) != null) {
                            mFingers.get(pointerId).setCurrentPointF(event.getX(i), event.getY(i));
                        }
                    }
                    if (event.getPointerCount() == 1 && event.getPointerId(0) == 0) {
                        //单指操作
                        Finger finger = mFingers.get(0);
                        mDeltaX = finger.getCurrentPointX() - finger.getLastPointX();
                        mDeltaY = finger.getCurrentPointY() - finger.getLastPointY();
                        if (!mIsBeingDragged && (Math.abs(mDeltaX) > mTouchSlop || Math.abs(mDeltaY) > mTouchSlop)) {
                            mIsBeingDragged = true;
                        }
                        //平移
                        mMatrix.postTranslate(mDeltaX, mDeltaY);
                    } else if (event.getPointerCount() >= 2) {
                        //双指操作
                        Finger finger1 = mFingers.get(0);
                        Finger finger2 = mFingers.get(1);
                        //第一次触摸的距离
                        float firstWight = Math.abs(finger1.getFirstPointX() - finger2.getFirstPointX());
                        float firstHeight = Math.abs(finger1.getFirstPointY() - finger2.getFirstPointY());
                        float firstLength = (float) Math.sqrt(firstWight * firstWight + firstHeight * firstHeight);
                        //第二次触摸的距离
                        float currentWight = Math.abs(finger1.getCurrentPointX() - finger2.getCurrentPointX());
                        float currentHeight = Math.abs(finger1.getCurrentPointY() - finger2.getCurrentPointY());
                        float currentLength = (float) Math.sqrt(currentWight * currentWight + currentHeight * currentHeight);
                        float singleLength = Math.max(mWidth, mHeight) / mMatrix.getMaxScale();
                        mMatrix.postScale(1 + (currentLength - firstLength) / singleLength * RESISTANCE);
                    }

                    return true;
                case MotionEvent.ACTION_UP:
                    Finger finger1 = mFingers.get(0);
                    if (Math.abs(finger1.getFirstPointX() - finger1.getCurrentPointX()) <= CLICK_THRESHOLD
                            && Math.abs(finger1.getFirstPointY() - finger1.getCurrentPointY()) <= CLICK_THRESHOLD
                            && mOnClickListener != null) {
                        mOnClickListener.onClick(MultiLineChartView.this);
                    }
                    if (mIsBeingDragged) {
                        //拖拽
                        mIsBeingDragged = false;
                        scrollBy(mDeltaX, mDeltaY);
                    }
                    //最后一个指头拿起来
                    for (int i = 0; i < mFingers.size(); i++) {
                        mFingers.get(i).reset();
                    }
                    return true;
                default:
                    return false;
            }
        }

        private void scrollBy(float deltaX, float deltaY) {
            mScroller.startScroll(0, 0, (int) deltaX, (int) deltaY, DURATION);
            notifyChangeView();
        }

        private void postTranslate(float dx, float dy) {
            mMatrix.postTranslate(dx, dy);
        }

        private float getTranX() {
            return mMatrix.getValues(CustomMatrix.Type.MTRANS_X);
        }

        private float getTranY() {
            return mMatrix.getValues(CustomMatrix.Type.MTRANS_Y);
        }

        private float getScale() {
            return mMatrix.getValues(CustomMatrix.Type.MSCALE_X);
        }

        private float[] getValues() {
            return mMatrix.getValues();
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }
    }

    private void setData(@NonNull List<MultiLineChartBean> list) {
        mDataBeanList.clear();
        mDataBeanList.addAll(list);
    }

    public void setData(@NonNull List<MultiLineChartDataBean> list, @ColorInt int color, @NonNull String tag) {
        setData(list, color, tag, 1);
    }

    public void setData(@NonNull List<MultiLineChartDataBean> list, @ColorInt int color, @NonNull String tag, int lineWidth) {
        for (int i = 0; i < mDataBeanList.size(); i++) {
            if (tag.equals(mDataBeanList.get(i).getTag())) {
                //相同数据移除之前的
                mDataBeanList.remove(i);
            }
        }
        MultiLineChartBean dataBean = new MultiLineChartBean();
        dataBean.setChartBeanList(list);
        dataBean.setColor(color);
        dataBean.setTag(tag);
        dataBean.setLineWidth(lineWidth);
        mDataBeanList.add(dataBean);
        //计算最大值与最小值，绘制左侧刻度
        mMaxValue = (int) ChartUtils.getMaxValue(list, mMaxValue);
        mMinValue = (int) ChartUtils.getMinValue(list, mMinValue);
        requestLayout();
    }

    /**
     * 设置下方刻度文本
     *
     * @param unitText 下方刻度文本集合
     */
    public MultiLineChartView setUnitText(List<String> unitText) {
        mUnitText = unitText;
        return this;
    }

    public MultiLineChartView setMaxValue(int maxValue) {
        mMaxValue = maxValue;
        return this;
    }

    public MultiLineChartView setMinValue(int minValue) {
        mMinValue = minValue;
        return this;
    }

    public MultiLineChartView setGridLineColor(int gridLineColor) {
        mGridLineColor = gridLineColor;
        return this;
    }

    public MultiLineChartView setBottomText(String bottomText) {
        mBottomText = bottomText;
        return this;
    }

    public MultiLineChartView setLeftText(String leftText) {
        mLeftText = leftText;
        return this;
    }

    @Override
    public BaseChartView clone() {
        MultiLineChartView multiLineView = new MultiLineChartView(getContext());
        ArrayList<MultiLineChartBean> list = new ArrayList<>();
        try {
            for (int i = 0; i < mDataBeanList.size(); i++) {
                list.add(mDataBeanList.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        multiLineView.setMinValue(mMinValue)
                .setMaxValue(mMaxValue)
                .setBottomText(mBottomText)
                .setLeftText(mLeftText)
                .setTitle(mTitle)
                .setBackground(mBackgroundColor)
                .setUnitText(mUnitText)
                .setFixHeight(false)
                .setData(list);
        multiLineView.setOnClickListener(null);
        return multiLineView;
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        mTouch.setOnClickListener(l);
    }
}
