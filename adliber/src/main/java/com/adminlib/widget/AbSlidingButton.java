package com.adminlib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;

import com.adutils.ABLogUtil;


/**
 * 类描述：AbSlidingButton
 * 创建人：Michael
 * 创建时间：2015/4/3 15:07
 * 修改人：Michael
 * 修改时间：2015/12/28 13:39
 * 修改备注：
 */
public class AbSlidingButton extends CheckBox {
    /**
     * The context.
     */
    private Context context;
    //图片资源
    /**
     * The btn frame.
     */
    private Bitmap btnFrame;
    /**
     * The btn mask.
     */
    private Bitmap btnMask;
    /**
     * The btn bottom.
     */
    private Bitmap btnBottom;
    /**
     * The m btn normal.
     */
    private Bitmap mBtnNormal;
    /**
     * The m btn pressed.
     */
    private Bitmap mBtnPressed;
    /**
     * The on checked change listener.
     */
    private OnCheckedChangeListener onCheckedChangeListener;
    //记录开关当前的状态
    /**
     * The is checked.
     */
    private boolean isChecked;
    //背景的宽高
    /**
     * The m mask height.
     */
    private float mMaskHeight;
    /**
     * The m mask width.
     */
    private float mMaskWidth;
    /**
     * The m btn off pos.
     */
    private float mBtnOffPos;
    /**
     * The m btn on pos.
     */
    private float mBtnOnPos;
    /**
     * The m btn pos.
     */
    private float mBtnPos;
    /**
     * The m last btn pos.
     */
    private float mLastBtnPos;
    /**
     * The m real pos.
     */
    private float mRealPos;
    /**
     * The m btn width.
     */
    private float mBtnWidth;
    //Y方向的延伸
    /**
     * The m extend offset y.
     */
    private float mExtendOffsetY;
    /**
     * The m alpha.
     */
    private int mAlpha;
    /**
     * The m paint.
     */
    private Paint mPaint;
    /**
     * The m xfermode.
     */
    private PorterDuffXfermode mXfermode;
    /**
     * The m save layer rect f.
     */
    private RectF mSaveLayerRectF;
    /**
     * The m cur btn pic.
     */
    private Bitmap mCurBtnPic;
    /**
     * The m first down x.
     */
    private float mFirstDownX;
    /**
     * The m move event.
     */
    private boolean mMoveEvent;
    /**
     * The m animating.
     */
    private boolean mAnimating;
    /**
     * The m animation position.
     */
    private float mAnimationPosition;
    /**
     * The m animated velocity.
     */
    private float mAnimatedVelocity;
    /**
     * The handler.
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mRealPos = getRealPos((Float) msg.obj);
                    mLastBtnPos = (Float) msg.obj;
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Instantiates a new ab sliding button.
     *
     * @param context the context
     */
    public AbSlidingButton(Context context) {
        super(context);
        init(context);
    }

    /**
     * Instantiates a new ab sliding button.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public AbSlidingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Inits the.
     *
     * @param context the context
     */
    private void init(Context context) {
        this.context = context;
        this.mAlpha = 255;
        this.isChecked = false;
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        float density = getResources().getDisplayMetrics().density;
        this.mAnimatedVelocity = (int) (0.5F + 350.0F * density);
    }

    /*
     * (non-Javadoc)
     * @see android.widget.CompoundButton#onDraw(android.graphics.Canvas)
     */
    protected void onDraw(Canvas canvas) {
        //设置一个透明度为255的图层
        canvas.saveLayerAlpha(this.mSaveLayerRectF, this.mAlpha, Canvas.ALL_SAVE_FLAG);
        if (btnMask != null) {
            canvas.drawBitmap(this.btnMask, 0F, this.mExtendOffsetY, this.mPaint);
            //混合绘制，取两层绘制交集。显示上层。btnBottom的长度超出btnMask的部分不绘制
            this.mPaint.setXfermode(this.mXfermode);
            canvas.drawBitmap(this.btnBottom, this.mRealPos, this.mExtendOffsetY, this.mPaint);
            this.mPaint.setXfermode(null);
            canvas.drawBitmap(this.btnFrame, 0F, this.mExtendOffsetY, this.mPaint);
            if (mCurBtnPic != null)
                canvas.drawBitmap(this.mCurBtnPic, this.mRealPos, 0.40000000596046448F + this.mExtendOffsetY, this.mPaint);
        }
        canvas.restore();
    }

    /**
     * 描述：测量View的宽高.
     *
     * @param paramInt1 the param int1
     * @param paramInt2 the param int2
     * @see android.view.View#onMeasure(int, int)
     */
    protected void onMeasure(int paramInt1, int paramInt2) {
        setMeasuredDimension((int) this.mMaskWidth, (int) (this.mMaskHeight + 2F * this.mExtendOffsetY));
    }

    /**
     * 描述：设置图片资源.
     *
     * @param btnBottomResource  the btn bottom resource
     * @param btnFrameResource   the btn frame resource
     * @param btnMaskResource    the btn mask resource
     * @param btnNormalResource  the btn normal resource
     * @param btnPressedResource the btn pressed resource
     */
    public void setImageResource(int btnBottomResource, int btnFrameResource, int btnMaskResource, int btnNormalResource, int btnPressedResource) {
        btnBottom = BitmapFactory.decodeResource(context.getResources(), btnBottomResource);
        btnFrame = BitmapFactory.decodeResource(context.getResources(), btnFrameResource);
        btnMask = BitmapFactory.decodeResource(context.getResources(), btnMaskResource);
        mBtnNormal = BitmapFactory.decodeResource(context.getResources(), btnNormalResource);
        mBtnPressed = BitmapFactory.decodeResource(context.getResources(), btnPressedResource);
        this.mMaskWidth = this.btnMask.getWidth();
        this.mMaskHeight = this.btnMask.getHeight();
        float density = getResources().getDisplayMetrics().density;
        this.mExtendOffsetY = (int) (0.5F + density * 0F);
        this.mSaveLayerRectF = new RectF(-20.0F, this.mExtendOffsetY, 20 + this.mMaskWidth, this.mMaskHeight + this.mExtendOffsetY);
        this.mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mCurBtnPic = this.mBtnNormal;
        this.mBtnWidth = this.mBtnPressed.getWidth();
        this.mBtnOnPos = (this.mBtnWidth / 2F);
        this.mBtnOffPos = (this.mMaskWidth - this.mBtnWidth / 2F);
        if (isChecked) {
            this.mBtnPos = mBtnOnPos;
        } else {
            this.mBtnPos = mBtnOffPos;
        }
        this.mRealPos = getRealPos(this.mBtnPos);
    }

    /**
     * Gets the real pos.
     *
     * @param paramFloat the param float
     * @return the real pos
     */
    private float getRealPos(float paramFloat) {
        return (paramFloat - this.mBtnWidth / 2F);
    }

    /**
     * 描述：获得当前.
     *
     * @return true, if is checked
     * @see android.widget.CompoundButton#isChecked()
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 描述：设置选中.
     *
     * @param checked the new checked
     * @see android.widget.CompoundButton#setChecked(boolean)
     */
    public void setChecked(boolean checked) {
        setChecked(checked, false);
    }

    /**
     * 描述：设置选中.
     *
     * @param checked the checked
     * @param anim    the anim
     * @see android.widget.CompoundButton#setChecked(boolean)
     */
    public void setChecked(boolean checked, boolean anim) {
        if (checked) {
            this.mBtnPos = this.mBtnOnPos;
        } else {
            this.mBtnPos = this.mBtnOffPos;
        }
        if (anim) {
            startAnimation();
        } else {
            moveViewToTarget();
        }
    }

    /**
     * 描述：设置监听器.
     *
     * @param listener the new on checked change listener
     * @see android.widget.CompoundButton#setOnCheckedChangeListener(OnCheckedChangeListener)
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    /**
     * 描述：滑动事件.
     *
     * @param event the event
     * @return true, if successful
     * @see android.widget.TextView#onTouchEvent(MotionEvent)
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mAnimating) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveEvent = false;
                //当前点击位置
                this.mFirstDownX = event.getX();
                //点击效果
                this.mCurBtnPic = this.mBtnPressed;
                //当前滑块再的位置
                if (this.isChecked) {
                    this.mBtnPos = this.mBtnOnPos;
                } else {
                    this.mBtnPos = this.mBtnOffPos;
                }
                mLastBtnPos = mBtnPos;
                ABLogUtil.i("原来的X位置：" + this.mBtnPos);
                break;
            case MotionEvent.ACTION_MOVE:
                ABLogUtil.i("－－－－移动－－－－");
                //当前点击位置
                float x = event.getX();
                //差
                float offsetX = x - this.mFirstDownX;
                ABLogUtil.i("X需要移动：" + offsetX);
                //转换为点击事件
                if (Math.abs(offsetX) < 5) {
                    break;
                } else {
                    mMoveEvent = true;
                }
                this.mFirstDownX = event.getX();
                //移动后的应该在的位置
                this.mBtnPos = this.mBtnPos + offsetX;
                ABLogUtil.i("现在的X位置：" + this.mBtnPos);
                //超出控件的设置
                if (this.mBtnPos < this.mBtnOffPos) {
                    this.mBtnPos = this.mBtnOffPos;
                }
                if (this.mBtnPos > this.mBtnOnPos) {
                    this.mBtnPos = this.mBtnOnPos;
                }
                moveViewToTarget();
                break;
            default:
                if (mMoveEvent) {
                    ABLogUtil.i("－－－－弹起－－－－");
                    //弹起
                    this.mCurBtnPic = this.mBtnNormal;
                    //本次移动最后结果
                    if (this.mBtnPos < (this.mBtnOnPos - this.mBtnOffPos) / 2F + this.mBtnOffPos) {
                        this.mBtnPos = this.mBtnOffPos;
                    } else {
                        this.mBtnPos = this.mBtnOnPos;
                    }
                    startAnimation();
                    offsetX = 0;
                } else {
                    //点击事件
                    setChecked(!isChecked, true);
                }
                break;
        }
        return true;
    }

    /**
     * 描述：滑块移动.
     *
     * @param pos the pos
     */
    private void moveView(float pos) {
        moveView(pos, false);
    }

    /**
     * 描述：滑块移动.
     *
     * @param pos   the pos
     * @param delay the delay
     */
    private void moveView(final float pos, boolean delay) {
        if (handler != null) {
            handler.obtainMessage(0, pos).sendToTarget();
        }
    }

    /**
     * 描述：用位移加速度实现动画.
     *
     */
    private void startAnimation() {
        //已经在目标位置
        if (mLastBtnPos == this.mBtnPos) {
            return;
        }
        this.mAnimating = true;
        ABLogUtil.i("目标移动X到：" + this.mBtnPos + ",当前在:" + mLastBtnPos);
        float mVelocity = this.mAnimatedVelocity;
        if (mLastBtnPos > this.mBtnPos) {
            mVelocity = -this.mAnimatedVelocity;
        }
        this.mAnimationPosition = mLastBtnPos;
        int i = 0;
        while (true) {
            this.mAnimationPosition = (this.mAnimationPosition + 16.0F * mVelocity / 1000.0F);
            ABLogUtil.i(i + "次移动X到：" + this.mAnimationPosition);
            if (this.mAnimationPosition >= this.mBtnOnPos) {
                this.mAnimationPosition = this.mBtnOnPos;
                moveView(this.mAnimationPosition, true);
                if (!isChecked) {
                    isChecked = true;
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(this, isChecked);
                    }
                }
                break;
            } else if (this.mAnimationPosition <= this.mBtnOffPos) {
                this.mAnimationPosition = this.mBtnOffPos;
                moveView(this.mAnimationPosition, true);
                if (isChecked) {
                    isChecked = false;
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener.onCheckedChanged(this, isChecked);
                    }
                }
                break;
            } else {
                moveView(this.mAnimationPosition, true);
            }
            i++;
        }
        this.mAnimating = false;
    }

    /**
     * 描述：直接移动到位置.
     */
    private void moveViewToTarget() {
        moveView(this.mBtnPos);
        if (this.mBtnPos == this.mBtnOnPos) {
            if (!isChecked) {
                isChecked = true;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(this, isChecked);
                }
            }
            return;
        } else if (this.mBtnPos == this.mBtnOffPos) {
            if (isChecked) {
                isChecked = false;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(this, isChecked);
                }
            }
            return;
        }
    }

    /*
     * (non-Javadoc)
     * @see android.widget.CompoundButton#performClick()
     */
    @Override
    public boolean performClick() {
        setChecked(!isChecked);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.widget.CompoundButton#toggle()
     */
    @Override
    public void toggle() {
        this.setChecked(!isChecked);
    }
}
