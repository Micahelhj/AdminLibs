package com.adminlib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


import com.adminlibs.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MyApplication
 * 类描述：可滑动的导航栏按钮容器（自定义HorizontalScrollView）
 * 创建人：Michael hj
 * 创建时间：2015/9/29 0029 17:34
 * 修改人：j
 * 修改时间：2015/9/29 0029 17:34
 * 修改备注：
 */
public class IndicatorTabView extends HorizontalScrollView {

    private int mMaxColumn;
    private static final int Default_Column = 3;

    public TabContainer mTabContainer;
    private List<TabB> mTabList = new ArrayList<TabB>();
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public IndicatorTabView(Context context) {
        this(context, null);
    }

    public IndicatorTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        mTabContainer = new TabContainer(context);
        mTabContainer.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mTabContainer.setOrientation(LinearLayout.HORIZONTAL);// default
        addView(mTabContainer);
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.IndicatorTabBar);
        mMaxColumn = attributes.getInteger(
                R.styleable.IndicatorTabBar_tab_max_column, Default_Column);
        attributes.recycle();
        linePaint.setStyle(Paint.Style.FILL);
    }
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
    private int layoutWidth;

    public void initView(ArrayList<View> view, int layoutWidth) {
        if (view != null && view.size() > 0) {
            initView(view, layoutWidth, mMaxColumn);
        }
    }


    public void initView(ArrayList<View> Views, int layoutWidth, int maxColumn) {
        this.layoutWidth = layoutWidth;
        if (maxColumn <= 0) {
            setMaxColumn(Default_Column);
        }
        int tabWidth = Math.round(layoutWidth / maxColumn);
        RemoveAllViews();
        for (int i = 0; i < Views.size(); i++) {
            addView(i, tabWidth, Views.get(i));
        }
    }

    public void setMaxColumn(int column) {
        this.mMaxColumn = column;
    }

    private TabB tabB;

    /**
     * add the View to TabContainer
     *
     * @param index tab's index
     * @param width tab's width
     */
    private void addView(final int index, int width, View view) {
        if (view == null)
            return;
        view.setPadding(5, 5, 5, 5);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
        mTabContainer.addView(view, params);
        tabB = new TabB();
        tabB.setIndex(index);
        tabB.setItemTabView(view);
        mTabList.add(tabB);
        view.setTag(tabB);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectedIndexListener != null)
                    tabSelectedIndexListener.onTabSelected(((TabB) v.getTag()).getIndex());
            }
        });
        postInvalidate();//刷新界面
    }


    /**
     * add the View to TabContainer
     */
    public void RemoveAllViews() {
        if (mTabList != null && mTabList.size() > 0) {
            mTabContainer.removeAllViews();
            mTabList.clear();
            postInvalidate();
        }
    }

    /**
     * set which tab is selected ,used for viewpager when onPageSelected
     *
     * @param position the index
     */
    public void setTabSelected(int position) {
        int w = mTabContainer.getChildAt(0).getWidth();
        if (position >= 0 || position < mTabList.size())
            if (position >= mMaxColumn / 2) {
                smoothScrollTo((position - (mMaxColumn / 2)) * w, 0);
            }
    }

    public long exitTime = 250;

    public void LeftGo() {
        if ((System.currentTimeMillis() - exitTime) > 250) {
            exitTime = System.currentTimeMillis();
            smoothScrollBy(-mTabContainer.getChildAt(0).getWidth(), 0);
        }
    }

    public void RightGo() {
        if ((System.currentTimeMillis() - exitTime) > 250) {
            exitTime = System.currentTimeMillis();
            smoothScrollBy(mTabContainer.getChildAt(0).getWidth(), 0);
        }
    }

    /**
     * Tab container which extends LinearLayout as a ViewGroup for adding View
     */
    private class TabContainer extends LinearLayout {

        public TabContainer(Context context) {
            this(context, null);
        }

        public TabContainer(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public TabContainer(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setGravity(Gravity.CENTER);
        }

    }


    /**
     * the interface will response when a Tab is selected
     */
    public interface OnTabSelectedIndexListener {
        void onTabSelected(int position);
    }

    private OnTabSelectedIndexListener tabSelectedIndexListener;

    public void setSelectedIndexListener(OnTabSelectedIndexListener tabSelectedIndexListener) {
        this.tabSelectedIndexListener = tabSelectedIndexListener;
    }

    public static class TabB implements Serializable {
        //字段
        private View itemTabView;
        private int index;
        //属性

        public View getItemTabView() {
            return itemTabView;
        }

        public void setItemTabView(View itemTabView) {
            this.itemTabView = itemTabView;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    /**
     * get the Screen Width in px
     *
     * @param context 上下文
     * @return widthPixels
     */
    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    private static final int TOUCH_STATE_VERTICAL_SCROLLING = -1;
    private int mTouchSlop;
    private int mTouchState = TOUCH_STATE_REST;
    private float mLastMotionX;
    private float mLastMotionY;

    /**
     * 拦截并处理HorizontalScrollView的Touch事件
     *
     * @param ev the MotionEvent
     * @return boolean
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float mScrollX = 0;
        float mScrollY = 0;
        boolean intercept = false;
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                //判断是否是水平滚动---是 不屏蔽父类滑动事件
                if (mTouchState == TOUCH_STATE_HORIZONTAL_SCROLLING) {
                    intercept = false;
                } else
                    //判断是否是竖直滚动事件 是  屏蔽父类滚动事件
                    if (mTouchState == TOUCH_STATE_VERTICAL_SCROLLING) {
                        intercept = true;
                    } else { //计算并判断并给mTouchState添加状态（水平滚动  还是竖直滚动）
                        final float x = ev.getX();
                        final float y = ev.getY();
                        final int xDiff = (int) Math.abs(x - mLastMotionX);
                        final int yDiff = (int) Math.abs(y - mLastMotionY);
                        boolean xMoved = xDiff > mTouchSlop;
                        boolean yMoved = yDiff > mTouchSlop;
                        if (xMoved) {
                            if (xDiff >= yDiff)
                                mTouchState = TOUCH_STATE_HORIZONTAL_SCROLLING;
                            mLastMotionX = x;
                        } else if (yMoved) {
                            if (yDiff > xDiff)
                                mTouchState = TOUCH_STATE_VERTICAL_SCROLLING;
                            mLastMotionY = y;
                        }
                    }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //结束参数状态
                mTouchState = TOUCH_STATE_REST;
                intercept = false;
                break;
            case MotionEvent.ACTION_DOWN:
                //初始化参数状态
                mTouchState = TOUCH_STATE_REST;
                mLastMotionY = ev.getY();
                mLastMotionX = ev.getX();
                break;
            default:
                break;
        }
        // 是否屏蔽父View的滑动事件
        getParent().requestDisallowInterceptTouchEvent(intercept);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        int totalW = 0;
        if (mTabContainer != null && mTabContainer.getChildCount() > 0 && mTabList != null && mTabList.size() > 0)
            totalW = mTabContainer.getChildAt(0).getWidth() * mTabList.size();
        if (mOnBorderListener != null) {
            if (totalW <= layoutWidth + scrollX)
                mOnBorderListener.onRight();
            else if (scrollX == 0)
                mOnBorderListener.onLeft();
            else
                mOnBorderListener.onScrolled();
        }
    }


    /**
     * 判断是否滚动到两端
     */
    public static interface OnBorderListener {

        /**
         * Called when scroll to left
         */
        public void onLeft();

        /**
         * Called when scroll to right
         */
        public void onRight();

        public void onScrolled();
    }

    private OnBorderListener mOnBorderListener;

    public void setOnBorderListener(OnBorderListener mOnBorderListener) {
        this.mOnBorderListener = mOnBorderListener;
    }
}
