package com.fantuan;

import android.support.v4.app.ListFragment;

import com.googlecode.androidannotations.annotations.*; 

@EFragment
public class HistoryListFragment extends ListFragment
        implements FanTuanManager.Observer {
    @Bean
    HistoryListAdapter mAdapter;

    @Bean
    FanTuanManager mFanTuanManager;

    @AfterInject
    void init() {
        mFanTuanManager.registerObserver(this);
        setListAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mFanTuanManager.unregisterObserver(this);
        super.onDestroy();
    }

    @Override
    public void onModelChanged() {
        mAdapter.refresh();
    }
}
