package com.jcdecaux.jcdroid.tictactoe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.widget.ListView;

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

    private OttoEventsReceiver ottoEventsReceiver;

    @AfterViews
    void afterViews() {
        playerListAdapter = new PlayerListAdapter(this);
        playerList.setAdapter(playerListAdapter);
        bus.register(ottoEventsReceiver = new OttoEventsReceiver());

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(ottoEventsReceiver);
    }

    @ItemClick
    void playerListItemClicked(NewPlayer player) {
        InviteActivity_.intent(this).player(player).startForResult(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GameActivity_.intent(this).start();
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }
    }

    // Due to a limitation in Otto. http://goo.gl/hsPJM
    final class OttoEventsReceiver {

        @Subscribe
        public void playerDiscovered(NewPlayer newPlayerEvent) {
            // FindPlayerActivity_.intent(FindPlayerActivity.this).start();
            playerListAdapter.addPlayer(newPlayerEvent);
        }

    }

}
