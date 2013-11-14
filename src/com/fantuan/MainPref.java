package com.fantuan;

import com.googlecode.androidannotations.annotations.sharedpreferences.*;

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface MainPref {
    String data();
    String lastSelected();
}
