package com.jcdecaux.jcdroid.tictactoe;

import static com.jcdecaux.jcdroid.tictactoe.event.NewPlayer.State.IDLE;
import static com.joanzapata.android.BaseAdapterHelper.get;
import static com.nineoldandroids.animation.ObjectAnimator.ofFloat;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jcdecaux.jcdroid.tictactoe.event.NewPlayer;
import com.joanzapata.android.BaseAdapterHelper;
import com.nineoldandroids.animation.AnimatorSet;

public class PlayerListAdapter extends BaseAdapter {

    private static final int ANIMATION_DELAY = 160;
    private static final int ANIMATION_DURATION = 400;

    private List<NewPlayer> players = new ArrayList<NewPlayer>();
    private final Context context;
    private int alreadyAnimatedItem = -1;
    private long lastAnimationTime = 0;

    public PlayerListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewPlayer player = getItem(position);
        BaseAdapterHelper helper = get(context, //
                convertView, parent, R.layout.player_item)//
                .setText(R.id.playerName, player.identifier) //
                .setText(R.id.playerState, player.state.toString());

        // Animate view on first display
        if (position > alreadyAnimatedItem) {
            helper.setAlpha(R.id.playerItem, 0);
            animate(helper.getView(), getNextDelay());
            alreadyAnimatedItem = position;
        }

        return helper.getView();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).state == IDLE;
    }

    /**
     * Get the next delay to use for an animation so that animations smoothly
     * come one after the other.
     */
    private synchronized long getNextDelay() {
        long now = System.currentTimeMillis();
        long delay = max(ANIMATION_DELAY - (now - lastAnimationTime), 0);
        lastAnimationTime = now + delay;
        return delay;
    }

    private void animate(View view, long delay) {
        final AnimatorSet set = new AnimatorSet();
        set.playTogether( //
                ofFloat(view, "alpha", 0f, 1f), //
                ofFloat(view, "translationY", -20, 0), //
                ofFloat(view, "translationX", 500, 0), //
                ofFloat(view, "scaleX", 2f, 1f), //
                ofFloat(view, "scaleY", 2f, 1f) //
        );
        set.setDuration(ANIMATION_DURATION).setStartDelay(delay);
        set.start();
    }

    public void addPlayer(NewPlayer newPlayerEvent) {
        players.add(newPlayerEvent);
        keepSorted();
        notifyDataSetChanged();
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
