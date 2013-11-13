package com.fantuan.view;

import com.fantuan.Dialogs;
import com.fantuan.R;
import com.fantuan.model.Person;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.*;

@EViewGroup(R.layout.person_view)
public class PersonView extends LinearLayout {

    @ViewById(android.R.id.text1)
    TextView text1;

    @ViewById(android.R.id.text2)
    TextView text2;

    @ViewById
    TextView rename;

    @ViewById
    TextView delete;

    @Bean
    Dialogs mDialogs;

    private Person p;

    public PersonView(Context context) {
        super(context);
    }

    public void bind(Person p, boolean editMode) {
        this.p = p;
        text1.setText(p.name);
        text2.setText(String.format("%.2f", p.current));
        text2.setTextColor(p.current >= 0 ? 0xff70c656: 0xffef4444);
        rename.setVisibility(editMode || p.needRename ? TextView.VISIBLE: TextView.GONE);
        delete.setVisibility(editMode ? TextView.VISIBLE: TextView.GONE);
    }

    @Click
    void rename() {
        mDialogs.modify(p);
    }

    @Click
    void delete() {
        mDialogs.delete(p);
    }
}
