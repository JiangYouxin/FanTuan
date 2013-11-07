package com.fantuan;

import com.fantuan.model.Person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class PersonListAdapter extends BaseAdapter {
    @Bean
    FanTuanManager mFanTuanManager;

    @RootContext
    Context mContext;

    @Override
    public Object getItem(int position) {
        return mFanTuanManager.getPersonList().get(position);
    }

    @Override
    public int getCount() {
        return mFanTuanManager.getPersonList().size();
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
                   android.R.layout.simple_list_item_2, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.setModel((Person)getItem(position));
        return convertView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView text1;
        private TextView text2;
        public ViewHolder(View view) {
            text1 = (TextView) view.findViewById(android.R.id.text1);
            text2 = (TextView) view.findViewById(android.R.id.text2);
        }
        public void setModel(Person p) {
            text1.setText(p.name);
            text2.setText(String.format("%.2f", p.current));
        }
    }
}
