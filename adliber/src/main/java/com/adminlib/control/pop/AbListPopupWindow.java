package com.adminlib.control.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.adminlib.utils.ABTextUtil;
import com.adminlibs.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AbListPopupWindow implements OnItemClickListener {
    private static Context context;
    public static AbListPopupWindow application;

    public AbListPopupWindow(Context c) {
    }

    public static AbListPopupWindow getInstance(Context c) {
        if (application == null)
            application = new AbListPopupWindow(c);
        context = c;
        return application;
    }

    private View mBaseView;
    private OnItemClickListener mOnItemClickListener;
    private PopupWindow mPopupWindow;
    private ListView mLV_pop;
    private InsideListAdapter mInsideListAdapter;
    private float TextSize = 14;
    private int TextGravity = Gravity.CENTER;

    public float getTextSize() {
        return TextSize;
    }

    public void setTextSize(float textSize) {
        TextSize = ABTextUtil.px2sp(context, textSize);
    }

    public int getTextGravity() {
        return TextGravity;
    }

    public void setTextGravity(int textGravity) {
        TextGravity = textGravity;
    }

    public AbListPopupWindow(Context context, final View baseView, int textSize, List<String> listString, int height, OnItemClickListener onItemClickListener) {
        this.context = context;
        openPopupWindow(true, baseView, textSize, listString, height, onItemClickListener);
    }

    public AbListPopupWindow(Context context, final View baseView, int baseViewWidth, int baseViewHeight, OnItemClickListener onItemClickListener) {
        this.context = context;
        InitPopupWindow(baseView, baseViewWidth, baseViewHeight, onItemClickListener);
    }

    /**
     * 开启下拉框
     *
     * @param isNewPopopWindow    是否新建一个pop
     * @param baseView            the baseView
     * @param textSize            the textSize
     * @param listString          the listString
     * @param height              the height
     * @param onItemClickListener the onItemClickListener
     */
    public void openPopupWindow(boolean isNewPopopWindow, final View baseView, int textSize, List<String> listString, int height, OnItemClickListener onItemClickListener) {
        String mLongStr = "";
        for (String str : listString) {
            if (str.length() > mLongStr.length()) {
                mLongStr = str;
            }
        }
        int tSize = ABTextUtil.getFontWidth(textSize, "正");
        int w = baseView.getWidth();
        int tw = mLongStr.length() * tSize;
        if (this.mBaseView == null || isNewPopopWindow || !this.mBaseView.equals(baseView)) {
            if (mPopupWindow != null && mPopupWindow.isShowing())
                mPopupWindow.dismiss();
            mPopupWindow = null;
            InitPopupWindow(baseView, tw > w ? tw : w, height, onItemClickListener);
        } else {
            if (mPopupWindow == null)
                InitPopupWindow(baseView, tw > w ? tw : w, height, onItemClickListener);
            else if (mPopupWindow.isShowing())
                mPopupWindow.dismiss();
        }
        setStringData(listString);
        mInsideListAdapter.notifyDataSetChanged();
        mPopupWindow.showAsDropDown(baseView, 0, 5);
    }

    /**
     * 初始化下拉框
     *
     * @param baseView            the baseView
     * @param baseViewWidth       the baseViewWidth
     * @param baseViewHeight      the baseViewHeight
     * @param onItemClickListener the onItemClickListener
     */
    private void InitPopupWindow(final View baseView, int baseViewWidth, int baseViewHeight, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mBaseView = baseView;
        this.mOnItemClickListener = onItemClickListener;
        int w = baseViewWidth > baseView.getWidth() ? baseViewWidth : baseView.getWidth();
        LayoutInflater inflater = (LayoutInflater) baseView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.popupwindow, null, false);
        mLV_pop = (ListView) popView.findViewById(R.id.popupwindow_list);
        mLV_pop.setOnItemClickListener(this);
        mPopupWindow = new PopupWindow(popView, w <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : w, baseViewHeight <= 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : baseViewHeight);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mInsideListAdapter = new InsideListAdapter(baseView.getContext());
        mLV_pop.setAdapter(mInsideListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    public void setEntityData(List<MyPopupWindowData> myPopupWindowDatas) {
        setData(myPopupWindowDatas, 0);
    }

    public void setStringData(List<String> listString) {
        setData(getEntityData(listString), 0);
    }

    private ArrayList<MyPopupWindowData> getEntityData(List<String> listString) {
        ArrayList<MyPopupWindowData> myPopupWindowDatas = new ArrayList<MyPopupWindowData>();
        if (listString == null || listString.size() < 1)
            return new ArrayList<MyPopupWindowData>();
        MyPopupWindowData data;
        for (int i = 0; i < listString.size(); i++) {
            data = new MyPopupWindowData();
            data.setmPopItemName(listString.get(i) + "");
            data.setmPopItemCode(i);
            myPopupWindowDatas.add(data);
        }
        return myPopupWindowDatas;
    }

    public void setData(List<MyPopupWindowData> myPopupWindowDatas, int defaultIndex) {
        if (myPopupWindowDatas != null && myPopupWindowDatas.size() != 0) {
            mInsideListAdapter.setData(myPopupWindowDatas);
            mInsideListAdapter.notifyDataSetChanged();
            showPopupWindow();
        }
    }

    public void showPopupWindow() {
        if (mPopupWindow.isShowing())
            mPopupWindow.dismiss();
        mPopupWindow.showAsDropDown(mBaseView, 0, 5);
    }

    /**
     * MyPopupWindow数据实体类
     */
    public static class MyPopupWindowData implements Serializable {
        public String mPopItemName = "";
        public int mPopItemCode = 0;

        public int getmPopItemCode() {
            return mPopItemCode;
        }

        public void setmPopItemCode(int mPopItemCode) {
            this.mPopItemCode = mPopItemCode;
        }

        public String getmPopItemName() {
            return mPopItemName;
        }

        public void setmPopItemName(String mPopItemName) {
            this.mPopItemName = mPopItemName;
        }
    }

    /**
     * MyPopupWindow列表适配器
     */
    private class InsideListAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<MyPopupWindowData> myPopupWindowDatas;

        public InsideListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<MyPopupWindowData> myPopupWindowDatas) {
            this.myPopupWindowDatas = myPopupWindowDatas;
        }

        @Override
        public int getCount() {
            if (myPopupWindowDatas == null)
                return 0;
            return myPopupWindowDatas.size();
        }

        @Override
        public MyPopupWindowData getItem(int position) {
            return myPopupWindowDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHoler {
            TextView tV_pop_item;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoler viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHoler();
                //定义一个线性布局
                LinearLayout layout = new LinearLayout(context);
                layout.setBackgroundResource(R.drawable.list_item_bg_select);
                //定义一个布局参数类（用于定义Button在线性布局中的参数）
                LayoutParams ltp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                viewHolder.tV_pop_item = new TextView(context);
                viewHolder.tV_pop_item.setGravity(getTextGravity());
                viewHolder.tV_pop_item.setPadding(0, 8, 0, 8);
                viewHolder.tV_pop_item.setTextSize(getTextSize());
                layout.addView(viewHolder.tV_pop_item, ltp);
                convertView = layout;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHoler) convertView.getTag();
            }
            viewHolder.tV_pop_item.setText(getItem(position).mPopItemName);
            return convertView;
        }
    }

}
