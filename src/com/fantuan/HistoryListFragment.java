package com.fantuan;

import android.support.v4.app.ListFragment;

import com.googlecode.androidannotations.annotations.*; 

@EFragment
@OptionsMenu(R.menu.history_actions)
public class HistoryListFragment extends ListFragment
        implements FanTuanManager.Observer {
    @Bean
    HistoryListAdapter mAdapter;

    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    Dialogs mDialog;

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

    @OptionsItem
    void menu_clear_history() {
        mDialog.clearHistory();
    }
}
