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

    public abstract void initialize(int slideCount, int first_page_num);

    /**
     * Select the position for the new page that became selected.
     * This method is called every time the selected page changed.
     *
     * @param index The index of the page that became selected
     */
    public abstract void setSelectPosition(int index);

    abstract void initViews();


    public void setSelectedIndicatorColor(int selectedDotColor) {
        this.selectedDotColor = selectedDotColor;
    }

    public int getSelectedIndicatorColor() {
        return selectedDotColor;
    }

    public void setUnselectedIndicatorColor(int unselectedDotColor) {
        this.unselectedDotColor = unselectedDotColor;
    }

    public int getUnselectedIndicatorColor() {
        return unselectedDotColor;
    }

    public int getCurrentposition() {
        return mCurrentposition;
    }

    public void setCurrentposition(int mCurrentposition) {
        this.mCurrentposition = mCurrentposition;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getCircleRadius() {
        return circleRadius;
    }
}
