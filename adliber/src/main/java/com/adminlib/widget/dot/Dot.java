package com.adminlib.widget.dot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 项目名称：AdminLibs
 * 类描述：
 * 创建人：Michael
 * 创建时间：2016/3/1 13:26
 * 修改人：Michael
 * 修改时间：2016/3/1 13:26
 * 修改备注：
 */
public class Dot extends ImageView {
    public Dot(Context context) {
        this(context, null);
        init();
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        paint = new Paint();
    }

    private Paint paint = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleSpacing = 4;
        float radius = (getWidth() - circleSpacing * 2) / 6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        canvas.save();
        float translateX = x + (radius * 2) + circleSpacing;
        canvas.translate(translateX, y);
        canvas.drawCircle(0, 0, radius, paint);
        canvas.restore();
    }

}
