package com.jcdecaux.jcdroid.tictactoe.model;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static com.jcdecaux.jcdroid.tictactoe.model.Game.GameListener;
import static com.jcdecaux.jcdroid.tictactoe.model.Game.Player.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class GameTest {

    @Test
    public void gameWinHorizontal() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 0); // X X X
        game.play(HIM, 2, 2); // - O -
        game.play(YOU, 0, 1); // - - O
        game.play(HIM, 1, 1);
        game.play(YOU, 0, 2);
        Mockito.verify(mock).onPlayerWin(YOU);
    }

    @Test
    public void gameWinVertical() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 1); // - X 0
        game.play(HIM, 2, 2); // - X -
        game.play(YOU, 1, 1); // - X 0
        game.play(HIM, 0, 2);
        game.play(YOU, 2, 1);
        Mockito.verify(mock).onPlayerWin(YOU);
    }

    @Test
    public void gameWinDiagonal1() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 0); // X - -
        game.play(HIM, 2, 0); // O X -
        game.play(YOU, 1, 1); // O - X
        game.play(HIM, 1, 0);
        game.play(YOU, 2, 2);
        Mockito.verify(mock).onPlayerWin(YOU);
    }

    @Test
    public void gameWinDiagonal2() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 2, 0); // - - X
        game.play(HIM, 2, 2); // - X O
        game.play(YOU, 1, 1); // X - O
        game.play(HIM, 1, 2);
        game.play(YOU, 0, 2);
        Mockito.verify(mock).onPlayerWin(YOU);
    }

    @Test
    public void draw() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 0); // X O X
        game.play(HIM, 0, 1); // X O O
        game.play(YOU, 0, 2); // O X X
        game.play(HIM, 1, 1);
        game.play(YOU, 1, 0);
        game.play(HIM, 1, 2);
        game.play(YOU, 2, 1);
        game.play(HIM, 2, 0);
        game.play(YOU, 2, 2);
        Mockito.verify(mock).onDraw();
    }

    @Test
    public void canPlay() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        MatcherAssert.assertThat("Can play before", game.canPlay(0, 0));
        game.play(YOU, 0, 0);
        MatcherAssert.assertThat("Can't play after", !game.canPlay(0, 0));
    }

    @Test
    public void onTurnChanged() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 0);
        MatcherAssert.assertThat("Changed turn", game.isTurn(HIM));
        game.play(HIM, 0, 1);
        MatcherAssert.assertThat("Changed turn", game.isTurn(YOU));
        Mockito.verify(mock, times(2)).onTurnChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void wrongTurn() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 0, 0);
        game.play(YOU, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cellNotEmpty() {
        GameListener mock = mock(GameListener.class);
        Game game = new Game(mock, YOU);
        game.play(YOU, 1, 1);
        game.play(HIM, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullListener() {
        new Game(null, YOU);
    }

}
