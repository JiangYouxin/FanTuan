package com.fantuan;

import com.fantuan.model.Person;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*; 

@EFragment(R.layout.history_list)
public class MoreFragment extends Fragment implements View.OnClickListener {
    @Bean
    Dialogs mDialog;

    @ViewById
    ListView list_view;

    @ViewById
    TextView title;

    @ViewById
    Button button_back;

    @AfterViews
    void init() {
        title.setText(R.string.more_title);
        button_back.setVisibility(View.GONE);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.text_arrow);
        mAdapter.add("test1");
        mAdapter.add("test2");
        mAdapter.add("test3");
        View view = getActivity().getLayoutInflater()
            .inflate(R.layout.more, null);
        view.findViewById(R.id.clear_all).setOnClickListener(this);
        list_view.addFooterView(view);
        list_view.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        mDialog.clearAll();
    }
}
