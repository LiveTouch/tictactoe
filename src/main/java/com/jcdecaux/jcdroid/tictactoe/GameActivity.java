package com.jcdecaux.jcdroid.tictactoe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.jcdecaux.jcdroid.tictactoe.event.NewTurn;
import com.squareup.otto.Subscribe;

@EActivity(R.layout.activity_game)
public class GameActivity extends Activity {

    @ViewById
    protected TextView youScore, himScore;

    @ViewById
    protected GridView grid;

    @Bean
    protected EBus bus;

    private OttoEventsReceiver ottoEventsReceiver;

    @AfterViews
    protected void afterViews() {
        bus.register(ottoEventsReceiver = new OttoEventsReceiver());
        youScore.setText("0");
        himScore.setText("0");
        grid.setAdapter(new ArrayAdapter<String>(this, //
                android.R.layout.simple_list_item_1, new String[] { //
                "X", "O", " ", //
                        " ", "X", "X", //
                        "O", "O", "X" //
                }));
        grid.setNumColumns(3);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(//
                R.anim.slide_in_from_left, //
                R.anim.slide_out_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(ottoEventsReceiver);
    }

    // Due to a limitation in Otto. http://goo.gl/hsPJM
    final class OttoEventsReceiver {

        @Subscribe
        public void hasPlayed(NewTurn newTurn) {

        }

    }
}
