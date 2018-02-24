package com.southernbox.nestedscrollcalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by SouthernBox on 2018/1/18.
 */

public class ListAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public ListAdapter(Context context) {
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mHolder = (Holder) holder;
        mHolder.textView.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
