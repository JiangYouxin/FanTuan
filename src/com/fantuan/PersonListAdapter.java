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

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class PersonListAdapter extends BaseAdapter {
    @Bean
    FanTuanManager mFanTuanManager;

    @RootContext
    Context mContext;

    private ArrayList<Person> mPersonList;

    private boolean mEditMode;

    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public void setPersonList(ArrayList<Person> personList) {
        mPersonList = personList;
    }

    @Override
    public Person getItem(int position) {
        return mPersonList.get(position);
    }

    @Override
    public int getCount() {
        return mPersonList.size();
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
