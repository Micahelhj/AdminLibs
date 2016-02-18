package com.adminlib.widget.topbar;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adminlibs.R;


/**
 * 项目名称：JKP
 * 类描述：
 * 创建人：Michael
 * 创建时间：2016/2/2 15:37
 * 修改人：Michael
 * 修改时间：2016/2/2 15:37
 * 修改备注：
 */
public class TopTitleBackBar extends BaseTopBackCenterHeader {
    private LinearLayout closeLl;
    private TextView titleTv;
    private TextView leftTitleTv;

    public TopTitleBackBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public TopTitleBackBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCloseClickListener(OnClickListener onClickListener) {
        closeLl.setOnClickListener(onClickListener);
    }

    public void setLeftClickListener(OnClickListener onClickListener) {
        titleTv.setOnClickListener(onClickListener);
    }

    public void setTitle(String text) {
        titleTv.setText(text);
    }

    @Override
    public void setTitleColor(int color) {
        titleTv.setTextColor(color);
    }

    public void setLeftTitle(String text) {
        leftTitleTv.setText(text);
    }

    public void setLeftShown(boolean shown) {
        leftTitleTv.setVisibility(shown ? View.VISIBLE : View.GONE);
    }


    void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.top_title_back, this);
        closeLl = (LinearLayout) findViewById(R.id.back_lay);
        titleTv = (TextView) findViewById(R.id.tV_top_title);
        leftTitleTv = (TextView) findViewById(R.id.leftTitleTv);
        closeLl.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }

            }
        });
    }
}
