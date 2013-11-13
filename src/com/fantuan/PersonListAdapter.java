package com.fantuan;

import com.fantuan.model.Person;
import com.fantuan.view.PersonView;
import com.fantuan.view.PersonView_;

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

    private boolean mEditMode;

    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    @Override
    public Person getItem(int position) {
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
        PersonView personView;
        if (convertView == null)
            personView = PersonView_.build(mContext);
        else
            personView = (PersonView) convertView;
        personView.bind(getItem(position), mEditMode);
        return personView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
