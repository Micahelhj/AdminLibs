package com.adminlib.widget.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.adminlibs.R;


/**
 * 项目名称：JKP
 * 类描述：
 * 创建人：Michael
 * 创建时间：2016/2/2 15:38
 * 修改人：Michael
 * 修改时间：2016/2/2 15:38
 * 修改备注：
 */
public abstract class BaseTopBackCenterHeader extends RelativeLayout {
    public BaseTopBackCenterHeader(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseTopBackCenterHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews();
        if (null != attrs) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.BaseTopBackCenterHeader);

            try {
                String title = arr.getString(R.styleable.BaseTopBackCenterHeader_headerTitle);
                this.setTitle(title);
                int titleColor = arr.getColor(R.styleable.BaseTopBackCenterHeader_headerTitleColor, getResources().getColor(R.color.cl_Grid));
                this.setTitleColor(titleColor);
                String leftTitle = arr.getString(R.styleable.BaseTopBackCenterHeader_headerLeftTitle);
                this.setLeftTitle(leftTitle);
                boolean leftShown = arr.getBoolean(R.styleable.BaseTopBackCenterHeader_headerShowLeft, false);
                this.setLeftShown(leftShown);
                boolean rightShown = !arr.getBoolean(R.styleable.BaseTopBackCenterHeader_headerHideLeft, false);
                this.setRightShown(rightShown);
            } finally {
                arr.recycle();
            }

        }
    }

    public abstract void setLeftClickListener(OnClickListener var1);

    public abstract void setTitle(String var1);

    public abstract void setTitleColor(int color);

    public abstract void setLeftTitle(String var1);

    public abstract void setLeftShown(boolean var1);

    public abstract void setRightShown(boolean var1);

    abstract void initViews();
}
