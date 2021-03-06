package com.fantuan;

import com.fantuan.model.Person;

import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    Button clear_all;

    @ViewById
    TextView title;

    @ViewById
    TextView list_header;

    private boolean mEditMode = false;

    @AfterViews
    void init() {
        mFanTuanManager.registerObserver(this);
        title.setText(R.string.person_list_title);
        button_right.setVisibility(Button.VISIBLE);
        mAdapter.setPersonList(mFanTuanManager.getPersonList());
        mAdapter.setShowIcon(true);
        list_view.setAdapter(mAdapter);
        refreshForEditMode();
        registerForContextMenu(list_view);
    }

    private void refreshForEditMode() {
        mAdapter.setEditMode(mEditMode);
        button_right.setText(mEditMode ? 
                R.string.finish: R.string.edit);
        clear_all.setVisibility(mEditMode ? 
                Button.VISIBLE: Button.GONE);
        onModelChanged();
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
    void clear_all() {
        mDialog.clearAll();
    }

    @ItemClick
    void list_viewItemClicked(int position) {
        PersonDetailActivity_.intent(getActivity())
            .name(mAdapter.getItem(position).name)
            .start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(
                R.menu.modify_delete, menu);
        if (mAdapter.getCount() <= 2)
            menu.removeItem(R.id.menu_delete);
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
            RemovePersonStep1Activity_.intent(getActivity())
                .nameToRemove(person.name)
                .start();
        }
        return true;
    }
}
