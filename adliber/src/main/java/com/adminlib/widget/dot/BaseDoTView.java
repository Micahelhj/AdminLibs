package com.adminlib.widget.dot;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.adminlibs.R;

/**
 * 项目名称：AdminLibs
 * 类描述：
 * 创建人：Michael
 * 创建时间：2016/3/1 11:46
 * 修改人：Michael
 * 修改时间：2016/3/1 11:46
 * 修改备注：
 */
public abstract class BaseDoTView extends LinearLayout {

    /**
     * 小圆点个数
     */
    protected int mSlideCount;
    /**
     * 默认颜色
     */
    private final static int DEFAULT_COLOR = 1;
    /**
     * 被选中颜色
     */
    private int selectedDotColor = DEFAULT_COLOR;
    /**
     * 未被选中颜色
     */
    private int unselectedDotColor = DEFAULT_COLOR;
    /**
     * 当前选中位置
     */
    private int mCurrentposition;
    /**
     * 小圆半径
     */
    private int circleRadius;

    public BaseDoTView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseDoTView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews();
        setGravity(Gravity.CENTER);
        if (null != attrs) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.BaseDoTView);
            try {
                int selectedIndicatorColor = arr.getColor(R.styleable.BaseDoTView_selectedIndicatorColor, getResources().getColor(R.color.cl_Grid));
                this.setSelectedIndicatorColor(selectedIndicatorColor);
                int UnselectedIndicatorColor = arr.getColor(R.styleable.BaseDoTView_unselectedIndicatorColor, getResources().getColor(R.color.cl_gray));
                this.setUnselectedIndicatorColor(UnselectedIndicatorColor);
                int circleRadius = arr.getInt(R.styleable.BaseDoTView_circleRadius, 0);
                if (circleRadius > 0)
                    setCircleRadius(circleRadius);
                int slideCount = arr.getInt(R.styleable.BaseDoTView_slideCount, 0);
                int selected = arr.getInt(R.styleable.BaseDoTView_selectPosition, 0);

                if (slideCount > 0) {
                    this.initialize(slideCount, selected);
                }
            } finally {
                arr.recycle();
            }
        }
        postInvalidate();
    }

    /**
     * 设置小点个数及默认选中
     *
     * @param slideCount     小点个数
     * @param first_page_num 默认选中
     */
    public abstract void initialize(int slideCount, int first_page_num);

    /**
     * 设置默认选中
     *
     * @param index The index of the page that became selected
     */
    public abstract void setSelectPosition(int index);

    /**
     * 初始化视图
     */
    protected abstract void initViews();

    /**
     * 设置选中小点颜色
     *
     * @param selectedDotColor
     */
    public void setSelectedIndicatorColor(int selectedDotColor) {
        this.selectedDotColor = selectedDotColor;
    }

    /**
     * 获取选中小点颜色
     *
     * @return
     */
    public int getSelectedIndicatorColor() {
        return selectedDotColor;
    }

    /**
     * 设置未被选中小点的颜色
     *
     * @param unselectedDotColor
     */
    public void setUnselectedIndicatorColor(int unselectedDotColor) {
        this.unselectedDotColor = unselectedDotColor;
    }

    /**
     * 获取未被选中小点的颜色
     *
     * @return
     */
    public int getUnselectedIndicatorColor() {
        return unselectedDotColor;
    }

    /**
     * 获取当前选中小点位置
     *
     * @return
     */
    public int getCurrentposition() {
        return mCurrentposition;
    }

    /**
     * 设置选中小点
     *
     * @param mCurrentposition
     */
    public void setCurrentposition(int mCurrentposition) {
        this.mCurrentposition = mCurrentposition;
    }

    /**
     * 设小点半径
     *
     * @param circleRadius
     */
    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    /**
     * 获取小点半径
     *
     * @return
     */
    public int getCircleRadius() {
        return circleRadius;
    }
}
