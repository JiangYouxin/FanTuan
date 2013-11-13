package com.fantuan;

import com.fantuan.model.Person;

import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.person_list)
public class MainListFragment extends Fragment implements
        FanTuanManager.Observer {
    @Bean
    PersonListAdapter mAdapter;

    @Bean
    FanTuanManager mFanTuanManager;

    @Bean
    Dialogs mDialog;

    @ViewById
    ListView list_view;

    @ViewById
    Button button_right;

    @ViewById
    Button button_back;

    @ViewById
    Button newdeal;

    private boolean mEditMode = false;

    @AfterViews
    void init() {
        mFanTuanManager.registerObserver(this);
        button_back.setText(R.string.menu_add);
        refreshForEditMode();
        button_right.setVisibility(Button.VISIBLE);
        list_view.setAdapter(mAdapter);
        registerForContextMenu(list_view);
    }

    private void refreshForEditMode() {
        mAdapter.setEditMode(mEditMode);
        button_right.setText(mEditMode ? 
                R.string.finish: R.string.edit);
        button_back.setVisibility(mEditMode ? 
                Button.VISIBLE : Button.GONE);
        newdeal.setVisibility(mEditMode ? 
                Button.GONE: Button.VISIBLE);
    }

    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
        refreshForEditMode();
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

    @Click
    void button_right() {
        setEditMode(!mEditMode);
    }

    @Click
    void button_back() {
        mDialog.add();
    }

    @Click
    void newdeal() {
        NewDealStep1Activity_.intent(getActivity()).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(
                R.menu.modify_delete, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((AdapterView.AdapterContextMenuInfo)item
                .getMenuInfo()).position;
        Person person = (Person) mAdapter.getItem(position);
        if (item.getItemId() == R.id.menu_modify) {
            mDialog.modify(person);
        } else if (item.getItemId() == R.id.menu_delete) {
            mDialog.delete(person);
        }
        return true;
    }
}
