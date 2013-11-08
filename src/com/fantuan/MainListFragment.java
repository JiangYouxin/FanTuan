package com.fantuan;

import android.support.v4.app.ListFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    NewDealDialog mNewDealDialog;

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
    void menu_add() {
        final EditText edit = new EditText(getActivity());
        new AlertDialog.Builder(getActivity())
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
