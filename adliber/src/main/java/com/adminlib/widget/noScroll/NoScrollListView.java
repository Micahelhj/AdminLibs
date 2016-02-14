package com.adminlib.widget.noScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 项目名称：JKPay
 * 类描述：
 * 创建人：Michael
 * 创建时间：2015/12/17 14:28
 * 修改人：Michael
 * 修改时间：2015/12/17 14:28
 * 修改备注：
 */
public class NoScrollListView extends ListView {


    public NoScrollListView(Context context) {
        super(context);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
