package com.adminlib.control.MListView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：JNIDemo
 * 类描述：
 * 创建人：Michael_hj
 * 创建时间：2015/12/7 0007 15:24
 * 修改人：Michael_hj
 * 修改时间：2015/12/7 0007 15:24
 * 修改备注：
 */
public abstract class MAdapter<T> extends BaseAdapter {
    private Activity activityContext;
    private ArrayList<T> list = new ArrayList<>();
    protected int mItemLayoutId;

    protected MAdapter(Activity activity, int itemLayoutId) {
        init(activity, new ArrayList<T>(), itemLayoutId);
    }

    protected MAdapter(Activity activity, ArrayList<T> mDatas, int itemLayoutId) {
        init(activity, mDatas, itemLayoutId);
    }

    /**
     * ================================================初始化数据================================================================
     */
    /**
     * @param activity     the activity
     * @param mDatas       zhe datas
     * @param itemLayoutId the layoutId
     */
    protected void init(Activity activity, ArrayList<T> mDatas, int itemLayoutId) {
        if (activity == null)
            throw new NullPointerException("上下文传入为空");
        else
            this.activityContext = activity;
        if (mDatas != null)
            this.list = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * ==========================================================================================================================
     */
    /**
     * 设置参数
     *
     * @param list 数据集合
     */
    public void setData(ArrayList<T> list) {
        initData(list);
    }

    /**
     * 设置参数并刷新列表
     *
     * @param list 数据集合
     */
    public void setDataNotify(ArrayList<T> list) {
        initData(list);
        notifyDataSetChanged();
    }

    /**
     * 初始化数据
     *
     * @param list 数据集合
     */
    private void initData(ArrayList<T> list) {
        if (list != null && list.size() > 0)
            this.list = list;
        else
            throw new NullPointerException("传入数据有误");
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 插入数据
     *
     * @param data 一条数据
     */
    public void insert(T data) {
        list.add(0, data);
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data 一条数据
     */
    public void append(T data) {
        list.add(data);
        this.notifyDataSetChanged();
    }

    /**
     * 替换一条数据
     *
     * @param data 一条数据
     */
    public void replace(T data) {
        int idx = this.list.indexOf(data);
        this.replace(idx, data);
    }

    /**
     * 替换一条数据
     *
     * @param index 数据位置
     * @param data  一条数据
     */

    public void replace(int index, T data) {
        if (index < 0) return;
        if (index > list.size() - 1) return;
        list.set(index, data);
        this.notifyDataSetChanged();
    }

    /**
     * 获取数据集合
     *
     * @return List
     */
    public List getItems() {
        return list;
    }

    /**
     * 删除一条数据
     *
     * @param position 数据位置
     */
    public void removeItem(int position) {
        if (list.size() <= 0) return;
        if (position < 0) return;
        if (position > list.size() - 1) return;
        list.remove(position);
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (list != null)
            list.clear();
        this.notifyDataSetChanged();
    }

    /**
     * =============================================================================================================
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        ItemConvert(viewHolder, position, getItem(position));

        return viewHolder.getConvertView();
    }

    /**
     * 数据绑定
     *
     * @param helper   the MViewHolder对象
     * @param position 列表位置
     * @param item     当前列表item
     */
    public abstract void ItemConvert(MViewHolder helper, int position, T item);

    /**
     * 获取ViewHolder
     *
     * @param position    位置
     * @param convertView 当前View
     * @param parent      ViewGroup
     * @return MViewHolder
     */
    private MViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return MViewHolder.get(activityContext, convertView, parent, mItemLayoutId, position);
    }
}
