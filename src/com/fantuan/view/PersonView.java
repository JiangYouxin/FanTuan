package com.fantuan.view;

import com.fantuan.Dialogs;
import com.fantuan.R;
import com.fantuan.model.Person;

import android.content.Context;
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

    @Bean
    Dialogs mDialogs;

    private Person p;

    public PersonView(Context context) {
        super(context);
    }

    public void bind(Person p) {
        this.p = p;
        text1.setText(p.name);
        text2.setText(String.format("%.2f", p.current));
        rename.setVisibility(p.needRename ? TextView.VISIBLE: TextView.GONE);
    }

    @Click
    void rename() {
        mDialogs.modify(p);
    }
}
