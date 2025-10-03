package edu.up.cs301.gamestate;

import java.util.ArrayList;

public class GameState {
    public final int MAX_PLAYERS = 5;
    private Deck deck;
    private int playerCount;
    private ArrayList<Player> players;
    private River river;


    /**
     * @param playerCount
     * @author Andrew
     * @desc creates and sets up a game with x number
     * of players. Haven't decided which helper classes
     * we want to make. A hand class that executes gameplay might be useful
     * but we could also just write some methods within this one.
     */
    public GameState(int playerCount) {
        this.playerCount = playerCount;
        deck = new Deck(); //create deck obj (arraylist of cards)
        river = new River(); //create river obj (kind of a player)
        players = new ArrayList<>();
        for (int p = 1; p <= MAX_PLAYERS; p++) { //add players
            players.add(new Player(p));
        }

        //toggle which players are playing
        //since the loop above creates the max number
        //of players
        if (playerCount == 5) {
            for (int p = 0; p < playerCount; p++) {
                players.get(p).setExists(true);
            }
        }
        if (playerCount == 4) {
            for (int p = 0; p < playerCount; p++) {
                players.get(p).setExists(true); //nobody in spot 5
            }
        }
        if (playerCount == 3) {
            players.get(0).setExists(true);
            players.get(2).setExists(true);
            players.get(3).setExists(true);
        }
        if (playerCount == 2) {
            players.get(1).setExists(true);
            players.get(3).setExists(true);
        }

        //just wanted to make sure my code would work
        //up to this point
        deck.dealCard(players.get(0));
    }

    //getters
    public int getPlayerCount() {
        return playerCount;
    }

    //needed to get player hands during gameplay
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public River getRiver() {
        return river;
    }
}
