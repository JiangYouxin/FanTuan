package com.fantuan;

import com.fantuan.model.NewHistoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class HistoryListAdapter extends BaseAdapter {
    @Bean
    FanTuanManager mFanTuanManager;

    @RootContext
    Context mContext;

    @Override
    public NewHistoryItem getItem(int position) {
        return mFanTuanManager.getHistoryList().get((int)getItemId(position));
    }

    @Override
    public int getCount() {
        return mFanTuanManager.getHistoryList().size();
    }

    @Override
    public long getItemId(int position) {
        return getCount() - 1 - position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                   R.layout.twoline, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.setModel(getItem(position));
        return convertView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView text1;
        private TextView text2; 
        private TextView current;
        public ViewHolder(View view) {
            text1 = (TextView) view.findViewById(android.R.id.text1);
            text2 = (TextView) view.findViewById(android.R.id.text2);
            current = (TextView) view.findViewById(R.id.current);
        }
        public void setModel(NewHistoryItem history) {
            text1.setText(history.time);
            text2.setText(mContext.getString(R.string.history_item,
                        history.persons.size(),
                        history.whoPay()));
            current.setText(String.format("%.2f", history.getCurrent()));
        }
    }
}
