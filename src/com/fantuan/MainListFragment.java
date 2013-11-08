package com.fantuan;

import com.fantuan.model.Person;

import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EFragment
@OptionsMenu(R.menu.main_actions)
public class MainListFragment extends ListFragment implements
        FanTuanManager.Observer {
    @Bean
    PersonListAdapter mAdapter;

    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    PersonManageDialog mDialog;

    @AfterViews
    void init() {
        mFanTuanManager.registerObserver(this);
        setListAdapter(mAdapter);
        registerForContextMenu(getListView());
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
    void menu_add() {
        mDialog.add();
    }

    @OptionsItem
    void menu_newdeal() {
        mDialog.newDeal();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(
                R.menu.modify_delete, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextMenuSelected(MenuItem item) {
        int position = item.getMenuInfo().position;
        Person person = (Person) mAdapter.getItem(position);
        if (item.getItemId() == R.id.menu_modify) {
            mDialog.modify(person);
        } else if (item.getItemId() == R.id.menu_delete) {
            mDialog.delete(person);
        }
    }
}
