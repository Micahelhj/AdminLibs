package com.adminlib.widget.dot;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adminlibs.R;
import com.adutils.ABTextUtil;

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
    /**
     * 放大放大倍数
     */
    public static final float SCALE = 1.0f;

    public static final int ALPHA = 255;

    private float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,};

    public Dot(Context context) {
        this(context, null);
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 小圆初始化设置
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (null != attrs) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.BaseDoT);
            try {
                int selectedIndicatorColor = arr.getColor(R.styleable.BaseDoT_circleColor, getResources().getColor(R.color.cl_Grid));
                this.setColor(selectedIndicatorColor);
            } finally {
                arr.recycle();
            }
        }
    }

    /**
     * 小圆半径
     */
    private int circleRadius;

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    /**
     * 小圆画笔
     */
    private Paint paint = null;
    /**
     * 小圆颜色
     */
    private int DefaultColor = 0;

    public void setColor(int color) {
        DefaultColor = color;
        if (paint != null)
            paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = getWidth() / 3;
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        canvas.save();//保存设置
        float translateX = x;
        canvas.translate(translateX, y);//平移画笔起始点
        canvas.scale(scaleFloats[0], scaleFloats[0]);//放大
        paint.setAlpha(alphas[0]);//设置透明度
        canvas.drawCircle(0, 0, radius, paint);//画圆
        canvas.restore();
    }

    public static final int DEFAULT_SIZE = 15;

    /**
     * 当控件的父元素正要放置该控件时调用
     * 根据父容器传递跟子容器的大小要求来确定子容器的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(ABTextUtil.dip2px(getContext(), DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(ABTextUtil.dip2px(getContext(), DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 根据父容器传递跟子容器的大小要求来确定子容器的大小
     *
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }
}
