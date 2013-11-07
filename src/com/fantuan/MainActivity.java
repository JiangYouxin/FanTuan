package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EActivity(R.layout.main)
@OptionsMenu(R.menu.main_actions)
public class MainActivity extends ActionBarActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    NewDealDialog mNewDealDialog;

    @Bean
    TabsAdapter mAdapter;

    @AfterViews
    void init() {
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mAdapter.addTab(bar.newTab().setText(R.string.person_list_title),
                MainListFragment_.class, null);
        mAdapter.addTab(bar.newTab().setText(R.string.history_list_title),
                HistoryListFragment_.class, null);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
    }

    @OptionsItem
    void menu_add() {
        final EditText edit = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.input_name)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edit.getText().toString();
                        if (name != null && !name.isEmpty())
                            mFanTuanManager.addNewPerson(name);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @OptionsItem
    void menu_newdeal() {
        mNewDealDialog.newDeal();
    }
}
