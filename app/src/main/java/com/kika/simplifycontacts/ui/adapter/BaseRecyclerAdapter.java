package com.kika.simplifycontacts.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by skylineTan on 2016/6/30.
 */
public abstract class BaseRecyclerAdapter<T extends Comparable<? super T>>
        extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_HEADER = 1;
    private static final int VIEW_FOOTER = 2;
    private static final int VIEW_EMPTY = 3;
    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;

    //点击事件
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemChildClickListener mChildClickListener;

    private boolean isHeadAndEmpty;
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;


    protected abstract void bindData(BaseViewHolder viewHolder, T item, int
            position);


    public BaseRecyclerAdapter(Context context, int layoutResId, List<T> data) {
        super();
        mData = data;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        if (layoutResId != 0) {
            mLayoutResId = layoutResId;
        }
        Collections.sort(mData);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        switch (viewType) {
            case VIEW_HEADER:
                baseViewHolder = new BaseViewHolder(mContext, mHeaderView);
                break;
            case VIEW_FOOTER:
                baseViewHolder = new BaseViewHolder(mContext, mFooterView);
                break;
            case VIEW_EMPTY:
                baseViewHolder = new BaseViewHolder(mContext, mEmptyView);
                break;
            default:
                baseViewHolder = new BaseViewHolder(mContext,
                        mLayoutInflater.inflate(mLayoutResId, parent, false));
                initClickListener(baseViewHolder);
                break;
        }
        return baseViewHolder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_HEADER:
                break;
            case VIEW_FOOTER:
                break;
            case VIEW_EMPTY:
                break;
            default:
                bindData(holder, mData.get(position),position);
                break;
        }
    }


    @Override public int getItemCount() {
        return mData.size();
    }


    @Override public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return VIEW_HEADER;
        }
        else if (mFooterView != null &&
                position == mData.size() + getHeaderViewsCount()) {
            //位置为最后一个元素position+1
            return VIEW_FOOTER;
        }
        else if (mEmptyView != null &&
                getItemCount() == (isHeadAndEmpty ? 2 : 1)) {
            return VIEW_EMPTY;
        }
        return super.getItemViewType(position);
    }


    //Header Footer Empty
    public void addHeaderView(View header) {
        mHeaderView = header;
        notifyDataSetChanged();
        if (mEmptyView != null) {
            isHeadAndEmpty = true;
        }
    }


    public void addFooterView(View footer) {
        mFooterView = footer;
        notifyDataSetChanged();
    }


    public void removeFooterView() {
        if (mFooterView != null) {
            mFooterView = null;
            notifyDataSetChanged();
        }
    }


    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        if (mHeaderView != null) {
            isHeadAndEmpty = true;
        }
    }


    public void setEmptyView(int resId) {

    }


    private void getViewByResId(int resId, ViewGroup parent) {
        mLayoutInflater.inflate(resId, parent, false);
    }


    //增加数据
    public void add(T item) {
        mData.add(item);
        //notifyItemInserted(position);
        
        Collections.sort(mData);
        notifyDataSetChanged();
    }


    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        Collections.sort(mData);
        notifyDataSetChanged();
    }


    public void addAll(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }


    //得到Header和Footer的个数
    public int getHeaderViewsCount() {
        return mHeaderView == null ? 0 : 1;
    }


    public int getFooterViewsCount() {
        return mFooterView == null ? 0 : 1;
    }


    //初始化点击事件
    private void initClickListener(final BaseViewHolder baseViewHolder) {
        if (onItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    onItemClickListener.onItemClick(v,baseViewHolder
                            .getAdapterPosition());
                }
            });
        }
        if (onItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override public boolean onLongClick(View v) {
                            return onItemLongClickListener.onItemLongClick(v,
                                    baseViewHolder.getAdapterPosition());
                        }
                    });
        }
    }


    //点击事件
    public void setOnItemClickListener(OnItemClickListener onRecyclerViewItemClickListener) {
        this.onItemClickListener = onRecyclerViewItemClickListener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public void setOnItemChildClickListener(OnItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    public class OnClickListenerwithHeader implements OnClickListener {

        public int position;


        @Override public void onClick(View v) {
            if (mChildClickListener != null) {
                mChildClickListener.onItemChildClick(BaseRecyclerAdapter.this,
                        v, position - getHeaderViewsCount());
            }
        }
    }
}
