package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EActivity
@OptionsMenu(R.menu.main_actions)
public class MainActivity extends ActionBarActivity {
    @Bean
    FanTuanManager mFanTuanManager;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Fragment f = MainListFragment_.builder().build();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, f)
                .commit();
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
}
