package com.fantuan;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.history_list)
public class HistoryListFragment extends Fragment
        implements FanTuanManager.Observer {
    @Bean
    HistoryListAdapter mAdapter;

    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    Dialogs mDialog;

    @ViewById
    Button button_right;

    @ViewById
    ListView list_view;

    @AfterViews
    void init() {
        mFanTuanManager.registerObserver(this);
        list_view.setAdapter(mAdapter);
        button_right.setText(R.string.menu_clear_history);
        onModelChanged();
    }

    @Override
    public void onDestroy() {
        mFanTuanManager.unregisterObserver(this);
        super.onDestroy();
    }

    @Override
    public void onModelChanged() {
        button_right.setVisibility(
            mFanTuanManager.getHistoryList().isEmpty()
            ? Button.GONE
            : Button.VISIBLE);
        mAdapter.refresh();
    }

    @Click
    void button_right() {
        mDialog.clearHistory();
    }
}
