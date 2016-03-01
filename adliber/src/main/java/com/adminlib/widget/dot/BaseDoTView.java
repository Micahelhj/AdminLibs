package com.adminlib.widget.dot;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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
    public BaseDoTView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseDoTView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews();
        if (null != attrs) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.BaseDoTView);

            try {
                int selected = arr.getInt(R.styleable.BaseDoTView_selected, 0);
                this.selectPosition(selected);

                int selectedIndicatorColor = arr.getColor(R.styleable.BaseDoTView_selectedIndicatorColor, getResources().getColor(R.color.cl_Grid));
                this.setSelectedIndicatorColor(selectedIndicatorColor);

                int UnselectedIndicatorColor = arr.getColor(R.styleable.BaseDoTView_UnselectedIndicatorColor, getResources().getColor(R.color.cl_Grid));
                this.setUnselectedIndicatorColor(UnselectedIndicatorColor);

            } finally {
                arr.recycle();
            }

        }
    }

    public abstract void initialize(int slideCount);

    /**
     * Select the position for the new page that became selected.
     * This method is called every time the selected page changed.
     *
     * @param index The index of the page that became selected
     */
    public abstract void selectPosition(int index);

    public abstract void setSelectedIndicatorColor(int color);

    public abstract void setUnselectedIndicatorColor(int color);

    abstract void initViews();
}
