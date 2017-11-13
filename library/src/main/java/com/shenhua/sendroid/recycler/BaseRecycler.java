package com.shenhua.sendroid.recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2017/7/24.
 * Email shenhuanet@126.com
 */
public abstract class BaseRecycler<T> extends RecyclerView.Adapter<BaseRecycler.RecyclerHolder> {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClickListener<T> mOnItemLongClickListener;

    public BaseRecycler(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 设置itemView的layoutId
     *
     * @param viewType viewType
     * @return layoutId
     */
    public abstract int getItemViewId(int viewType);

    /**
     * 绑定数据
     *
     * @param holder   holder
     * @param position position
     * @param item     data
     */
    public abstract void bindData(RecyclerHolder holder, int position, T item);

    public void addItem(int position, T itemData) {
        mDatas.add(position, itemData);
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void addMoreItem(List<T> datas) {
        int startPosition = mDatas.size();
        mDatas.addAll(datas);
        notifyItemRangeChanged(startPosition, mDatas.size());
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> longClickListener) {
        this.mOnItemLongClickListener = longClickListener;
    }

    @Override
    public BaseRecycler.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerHolder holder = new RecyclerHolder(mContext, mInflater.inflate(getItemViewId(viewType), parent, false), viewType);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition(), mDatas.get(holder.getAdapterPosition()));
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(view, holder.getLayoutPosition(), mDatas.get(holder.getAdapterPosition()));
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecycler.RecyclerHolder holder, int position) {
        bindData(holder, position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public interface OnItemClickListener<T> {
        /**
         * item点击后回调
         *
         * @param view     itemView
         * @param position position
         * @param data     data
         */
        void onItemClick(View view, int position, T data);
    }

    public interface OnItemLongClickListener<T> {
        /**
         * item长按后回调
         *
         * @param view     itemView
         * @param position position
         * @param data     data
         */
        boolean onItemLongClick(View view, int position, T data);
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {

        /**
         * 集合类，layout里包含的View,以view的id作为key，value是view对象
         */
        private SparseArray<View> mViews;
        private Context mContext;
        private int viewType;

        public RecyclerHolder(Context context, View itemView, int viewType) {
            super(itemView);
            mContext = context;
            mViews = new SparseArray<>();
            this.viewType = viewType;
        }

        @SuppressWarnings("unchecked")
        private <T extends View> T findViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getView(int viewId) {
            return findViewById(viewId);
        }

        public int getViewType() {
            return viewType;
        }

        public RecyclerHolder setText(int viewId, String value) {
            TextView view = findViewById(viewId);
            view.setText(value);
            return this;
        }

        public RecyclerHolder setBackground(int viewId, int resId) {
            View view = findViewById(viewId);
            view.setBackgroundResource(resId);
            return this;
        }

        public RecyclerHolder setBackgroundColor(int viewId, int color) {
            View view = findViewById(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public RecyclerHolder setImage(int viewId, int resId) {
            ImageView view = findViewById(viewId);
            return this;
        }

        public RecyclerHolder setImage(int viewId, String url) {
            ImageView view = findViewById(viewId);
            return this;
        }

        public RecyclerHolder setImage(int viewId, Drawable drawable) {
            ImageView view = findViewById(viewId);
            return this;
        }

        public RecyclerHolder setOnItemViewClickListener(int viewId, View.OnClickListener listener) {
            View view = findViewById(viewId);
            view.setOnClickListener(listener);
            return this;
        }
    }
}
