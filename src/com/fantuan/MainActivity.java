package com.fantuan;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

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
}
