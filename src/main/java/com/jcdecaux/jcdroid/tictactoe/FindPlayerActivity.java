package com.jcdecaux.jcdroid.tictactoe;

import android.app.Activity;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.jcdecaux.jcdroid.tictactoe.event.NewPlayer;
import com.jcdecaux.jcdroid.tictactoe.event.NewPlayer.State;
import com.squareup.otto.Subscribe;

@EActivity(R.layout.activity_main)
public class FindPlayerActivity extends Activity {

    @ViewById
    protected ListView playerList;

    @Bean
    protected EBus bus;

    private PlayerListAdapter playerListAdapter;

    @AfterViews
    void afterViews() {
        playerListAdapter = new PlayerListAdapter(this);
        playerList.setAdapter(playerListAdapter);
        bus.register(new OttoEventsReceiver());

        // Test data
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0010", State.BUSY, 0d, 0d));
        bus.post(new NewPlayer("FRPLAMUI0028", State.IDLE, 0d, 0d));
    }

    // Due to a limitation in Otto. http://goo.gl/hsPJM
    final class OttoEventsReceiver {

        @Subscribe
        public void playerDiscovered(NewPlayer newPlayerEvent) {
            playerListAdapter.addPlayer(newPlayerEvent);
        }

    }

}
