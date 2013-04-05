package com.jcdecaux.jcdroid.tictactoe;

import android.app.Activity;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class FindPlayerActivity extends Activity {

    @AfterViews
    void afterViews() {
    }

}
