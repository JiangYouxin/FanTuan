package com.fantuan;

import com.fantuan.model.PersonHistoryItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class PersonHistoryListAdapter extends BaseAdapter {
    @RootContext
    Context mContext;

    private ArrayList<PersonHistoryItem> mList;

    public void setList(ArrayList<PersonHistoryItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public PersonHistoryItem getItem(int position) {
        return mList.get((int)getItemId(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                   R.layout.left_right, null);
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
        public ViewHolder(View view) {
            text1 = (TextView) view.findViewById(android.R.id.text1);
            text2 = (TextView) view.findViewById(android.R.id.text2);
        }
        public void setModel(PersonHistoryItem history) {
            text1.setText(history.time);
            text2.setText(String.format("%.2f", history.current));
            text2.setTextAppearance(mContext, history.current >= 0 
                ? R.style.PrimaryTextGreen: R.style.PrimaryTextRed);
        }
    }
}
