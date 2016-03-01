package com.adminlib.widget.dot;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adminlibs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：AdminLibs
 * 类描述：
 * 创建人：Michael
 * 创建时间：2016/3/1 11:17
 * 修改人：Michael
 * 修改时间：2016/3/1 11:17
 * 修改备注：
 */
public class DoTView extends BaseDoTView {

    public final static int DEFAULT_COLOR = 1;
    private static final int FIRST_PAGE_NUM = 0;

    private List<ImageView> mDots;
    private int mSlideCount;
    int selectedDotColor = DEFAULT_COLOR;
    int unselectedDotColor = DEFAULT_COLOR;
    int mCurrentposition;

    public DoTView(Context context) {
        super(context);
    }

    public DoTView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initialize(int slideCount) {
        mDots = new ArrayList<>();
        mSlideCount = slideCount;
        selectedDotColor = -1;
        unselectedDotColor = -1;

        for (int i = 0; i < slideCount; i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_dot_grey));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            addView(dot, params);
            mDots.add(dot);
        }

        selectPosition(FIRST_PAGE_NUM);
    }

    @Override
    public void selectPosition(int index) {
        mCurrentposition = index;
        for (int i = 0; i < mSlideCount; i++) {
            int drawableId = (i == index) ? (R.drawable.indicator_dot_white) : (R.drawable.indicator_dot_grey);
            Drawable drawable = ContextCompat.getDrawable(getContext(), drawableId);
            if (selectedDotColor != DEFAULT_COLOR && i == index)
                drawable.mutate().setColorFilter(selectedDotColor, PorterDuff.Mode.SRC_IN);
            if (unselectedDotColor != DEFAULT_COLOR && i != index)
                drawable.mutate().setColorFilter(unselectedDotColor, PorterDuff.Mode.SRC_IN);
            mDots.get(i).setImageDrawable(drawable);
        }
    }

    @Override
    public void setSelectedIndicatorColor(int color) {
        selectedDotColor = color;
        selectPosition(mCurrentposition);
    }

    @Override
    public void setUnselectedIndicatorColor(int color) {
        unselectedDotColor = color;
        selectPosition(mCurrentposition);
    }

    @Override
    void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.default_indicator, this);
    }

}
