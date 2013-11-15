package com.fantuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.fantuan.model.Person;
import com.fantuan.R;

import java.util.ArrayList;

import com.googlecode.androidannotations.annotations.*;

@EBean
public class Dialogs {
    @RootContext
    Activity mActivity;

    @Bean
    FanTuanManager mFanTuanManager;

    public void modify(final Person p) {
        final EditText edit = new EditText(mActivity);
        edit.setText(p.name);
        edit.selectAll();
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.input_name_modify)
                .setView(edit)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edit.getText().toString();
                        if (name != null && !name.isEmpty()) {
                            if (!mFanTuanManager.nameExists(name))
                                mFanTuanManager.modifyName(p, name);
                            else
                                showNameExistsDialog();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void showNameExistsDialog() {
        new AlertDialog.Builder(mActivity)
            .setTitle(R.string.name_exists)
            .setPositiveButton(R.string.ok, null) 
            .show();
    }

    public void clearHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.confirm_clear_history_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFanTuanManager.clearHistory();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public void clearAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.clear_all_confirm);
        builder.setMessage(R.string.clear_all_confirm_message);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mFanTuanManager.clearAll();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
