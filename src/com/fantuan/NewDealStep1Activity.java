package com.fantuan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import com.googlecode.androidannotations.annotations.*; 
import com.googlecode.androidannotations.annotations.sharedpreferences.*; 

@EActivity(R.layout.list_with_header)
public class NewDealStep1Activity extends FragmentActivity implements View.OnClickListener { 
    @Pref
    MainPref_ mPref;

    @Bean
    FanTuanManager mFanTuanManager;

    @ViewById
    ListView list_view;

    @ViewById
    Button button_right;

    @Bean
    Dialogs mDialogs;

    @ViewById
    TextView list_header;

    private String[] names;

    private boolean[] selected;

    private BaseAdapter mAdapter;

    private Gson gson = new Gson();

    private Type type = new TypeToken<HashSet<String>>(){}.getType(); 

    void initData() {
        int length = mFanTuanManager.getPersonList().size();
        names = new String[length];
        selected = new boolean[length];

        HashSet<String> lastSelected = null;

        try {
            String json = mPref.lastSelected().get(); 
            lastSelected = gson.fromJson(json, type); 
        } catch (Exception e) {
        }

        for (int i = 0; i < length; i++) {
            names[i] = mFanTuanManager.getPersonList().get(i).name;
            if ((lastSelected == null) || lastSelected.isEmpty())
                selected[i] = true;
            else
                selected[i] = lastSelected.contains(names[i]);
        }
    }

    @AfterViews
    void init() {
        initData();
        mAdapter = new CustomAdapter();
        View footer = getLayoutInflater().inflate(R.layout.gray_button, null);
        footer.findViewById(R.id.add_new).setOnClickListener(this);
        list_view.addFooterView(footer);
        list_view.setAdapter(mAdapter);
        button_right.setVisibility(Button.VISIBLE);
        refresh();
    }

    @Override
    public void onClick(View v) {
        final EditText edit = new EditText(this);
        new AlertDialog.Builder(this)
            .setTitle(R.string.input_name)
            .setView(edit)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (addNew(edit.getText().toString()))
                        dialog.dismiss();
                }
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }

    private boolean addNew(String name) {
        if (name == null || name.isEmpty())
            return false;
        for (String n: names) {
            if (name.equals(n)) {
                mDialogs.showNameExistsDialog();
                return false;
            }
        }

        // 写点垃圾代码吧
        String[] oldNames = names;
        boolean[] oldSelected = selected;
        int length = oldNames.length;
        names = new String[length + 1];
        selected = new boolean[length + 1];
        for (int i = 0; i < length; i++) {
            names[i] = oldNames[i];
            selected[i] = oldSelected[i];
        }
        names[length] = name;
        selected[length] = true;
        refresh();
        return true;
    }
    
    @Click
    void button_right() {
        HashSet<String> checkedNames = new HashSet<String>();
        for (int i = 0; i < names.length; i++) {
            if (selected[i])
                checkedNames.add(names[i]);
        }
        NewDealStep2Activity_.intent(this)
            .names(checkedNames.toArray(new String[0]))
            .start();
        saveLastSelected(checkedNames);
    }

    @Background
    void saveLastSelected(HashSet<String> checkedNames) {
        String json = gson.toJson(checkedNames, type);
        mPref.edit().lastSelected().put(json).apply();
    }

    @ItemClick
    void list_viewItemClicked(int position) {
        selected[position] = !selected[position];
        mAdapter.notifyDataSetChanged();
        refresh();
    }

    void refresh() {
        int count = getSelectedCount();
        button_right.setEnabled(count >= 2);
        list_header.setText(getString(R.string.newdeal_step_1, count));
    }

    private int getSelectedCount() {
        int selectedCount = 0;
        for (boolean b: selected) if (b)
           selectedCount++;
        return selectedCount;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public String getItem(int position) {
            return names[position];
        }
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.checked_text, null);
            CheckedTextView tv = (CheckedTextView) convertView;
            tv.setText(names[position]);
            tv.setChecked(selected[position]);
            return tv;
        }
    } 
}
