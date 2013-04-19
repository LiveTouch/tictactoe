package com.jcdecaux.jcdroid.tictactoe.event;

import static com.jcdecaux.jcdroid.tictactoe.event.NewPlayer.State.BUSY;

import java.io.Serializable;

public class NewPlayer implements Comparable<NewPlayer>, Serializable {
    private static final long serialVersionUID = -3309674783385376677L;

    public static enum State {
        IDLE, BUSY
    }

    public final String identifier;

    public final State state;

    public final double lat, lon;

    public NewPlayer(String identifier, State state, double lat, double lon) {
        this.identifier = identifier;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int compareTo(NewPlayer another) {
        // Sort by state, then by identifier
        if (another.state == this.state) {
            return another.identifier.compareTo(this.identifier);
        } else {
            return this.state == BUSY ? 1 : -1;
        }
    }
}
