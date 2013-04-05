package com.jcdecaux.jcdroid.tictactoe;

import static com.jcdecaux.jcdroid.tictactoe.event.NewPlayer.State.BUSY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jcdecaux.jcdroid.tictactoe.event.NewPlayer;
import com.joanzap.android.BaseAdapterHelper;

public class PlayerListAdapter extends BaseAdapter {

    private static final float ALPHA_DISABLED = 0.2f;
    private static final float ALPHA_ENABLED = 1f;
    private List<NewPlayer> players = new ArrayList<NewPlayer>();
    private Context context;

    public PlayerListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewPlayer player = getItem(position);
        return BaseAdapterHelper.get(context, convertView, parent, R.layout.player_item)//
                .setText(R.id.playerName, player.identifier) //
                .setText(R.id.playerState, player.state.toString()) //
                .setAlpha(R.id.playerItem, //
                        player.state == BUSY ? ALPHA_DISABLED : ALPHA_ENABLED) //
                .getView();
    }

    public void addPlayer(NewPlayer newPlayerEvent) {
        players.add(newPlayerEvent);
        keepSorted();
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public NewPlayer getItem(int item) {
        return players.get(item);
    }

    @Override
    public long getItemId(int item) {
        return item;
    }

    private void keepSorted() {
        Collections.sort(players);
    }
}
