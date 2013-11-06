package com.fantuan;

import com.fantuan.R;

import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.googlecode.androidannotations.annotations.*; 

@EFragment
public class MainListFragment extends ListFragment {
    @AfterViews
    void setAdapter() {
        setListAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                new String[] {
                    "李小庆",
                    "李瑞锋",
                    "吴可",
                    "刘江明",
                    "张国军",
                    "张勇六"
                }));
    }
}
