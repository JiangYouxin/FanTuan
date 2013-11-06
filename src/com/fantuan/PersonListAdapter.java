package com.fantuan;

import com.fantuan.model.Person;

import android.widget.ArrayAdapter;
import android.content.Context;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class PersonListAdapter extends ArrayAdapter<Person> {
    @Bean
    FanTuanManager mFanTuanManager;

    public PersonListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @AfterInject
    public void refresh() {
        clear();
        addAll(mFanTuanManager.getPersonList());
    }
}
