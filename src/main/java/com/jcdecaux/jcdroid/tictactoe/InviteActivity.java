package com.jcdecaux.jcdroid.tictactoe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;

import android.app.Activity;

import com.jcdecaux.jcdroid.tictactoe.event.NewPlayer;

@EActivity(R.layout.dialog_invite)
public class InviteActivity extends Activity {

    @Extra
    NewPlayer player;

    @AfterInject
    protected void afterInject() {
        fakePlayerAccepted();
    }

    @UiThread(delay = 1000)
    protected void fakePlayerAccepted() {
        setResult(RESULT_OK);
        finish();
    }
}
