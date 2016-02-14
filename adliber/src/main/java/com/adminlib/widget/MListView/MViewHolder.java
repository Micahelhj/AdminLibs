package com.adminlib.widget.MListView;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 项目名称：JNIDemo
 * 类描述：
 * 创建人：Michael_hj
 * 创建时间：2015/12/7 0007 18:13
 * 修改人：Michael_hj
 * 修改时间：2015/12/7 0007 18:13
 * 修改备注：
 */
public class MViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    private MViewHolder(Context context, ViewGroup parent, int layoutId,
                        int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context     上下文
     * @param convertView the View
     * @param parent      the ViewGroup
     * @param layoutId    the layoutID
     * @param position    the index
     * @return MViewHolder
     */
    public static MViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new MViewHolder(context, parent, layoutId, position);
        }
        return (MViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId the viewID
     * @param <T>    the Type
     * @return view
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId the viewid
     * @param text the text
     * @return MViewHolder
     */
    public MViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId     the viewID
     * @param drawableId the drawableId
     * @return MViewHolder
     */
    public MViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId the viewId
     * @param bm     the bm
     * @return MViewHolder
     */
    public MViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId the viewId
     * @param url    the url
     * @return MViewHolder
     */
    public MViewHolder setImageByUrl(int viewId, String url) {
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

}

