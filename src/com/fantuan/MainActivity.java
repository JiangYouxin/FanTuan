package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.googlecode.androidannotations.annotations.*; 

@EActivity
@OptionsMenu(R.menu.main_actions)
public class MainActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Fragment f = MainListFragment_.builder().build();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, f)
                .commit();
    }

    @OptionsItem
    void menu_add() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.input_name)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setView(new EditText(this))
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
}
