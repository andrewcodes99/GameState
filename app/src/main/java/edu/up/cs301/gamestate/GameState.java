package edu.up.cs301.gamestate;

import java.util.ArrayList;

public class GameState {
    public final int MAX_PLAYERS = 5;
    public final int USER_PLAYER_ID = 3;
    public final int LB_AMT = 5;
    public final int BB_AMT = 10;
    private Deck deck;
    private int playerCount;
    private ArrayList<Player> players;
    private River river;
    private Model model;

    /**
     * @param playerCount DON'T USE THIS FOR LOOPING OVER THE PLAYERS
     * @author Andrew
     * @desc creates and sets up a game with x number
     * of players. Haven't decided which helper classes
     * we want to make. A hand class that executes gameplay might be useful
     * but we could also just write some methods within this one. For now,
     * I just wrote the methods.
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

        //TODO: will probably want to move this and the getter to
        //  the view
        model = new Model();
    }

    //TODO: may want to deal 1 card to each player, then the 2nd
    //TODO: network play complicates things:
    //  1) cards can only be flipped if they belong to the player
    //      (different bitmaps draw based on the screen)
    //  2) Could have a root_user and a secondary_user to enable
    //      network play
    public int dealAction() {

        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (players.get(i).playerExists()) {
                deck.dealCard(players.get(i));
                deck.dealCard(players.get(i));
            }
            if (players.get(i).getPlayerID() == USER_PLAYER_ID) {
                for (int j = 0; j < 2; j++) {
                    players.get(i).getHand().get(j).flipCard(true);
                }
            }
        }

        //deal the river pre-flop
        for (int i = 0; i < 5; i++) {
            deck.dealCard(river);
        }

        //TODO: could make dealerID a model var to cut loop
        //little blind and big blind
        int dealerID = -1;
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (players.get(i).isDealer()) {
                dealerID = i;
            }
        }
        return dealerID;
    }


    //Where which bet could refer to pre-flop, post-flop, post-turn, or post-river betting
    //(0, 1, 2, 3) pre-flop needs to set the blinds, it is the only one that differs
    //TODO: when its not hardcoded, have the listeners update the bet amount to whatever the
    //TODO: chip bet selector is set to, on confirm raise, update a raise variable in the model
    //TODO: we will need to wait for each players input within the loop

    //TODO: for now I'm returning whoseTurn because I am using this as a method
    //  to set the blinds for the gameState submission. Later, this wont be necessary.
    //  For clarity, it's called setup now. Will be called betLoop when bets arent
    //  hardcoded.
    public int setup(int whichBet) {
        int dealerID = dealAction();
        int whoseTurn = (dealerID + 1) % MAX_PLAYERS;
        if (whichBet == 0) {//pre-flop blinds
            int blindCount = 0;
            while (blindCount < 2) {
                if (!players.get(whoseTurn).playerExists()) {
                    whoseTurn = (whoseTurn + 1) % MAX_PLAYERS;
                } else if (blindCount == 0) {
                    players.get(whoseTurn).setTurn(true);
                    players.get(whoseTurn).raise(model.callAmt, model.minRaise, LB_AMT);
                    model.callAmt = LB_AMT;
                    model.minRaise = LB_AMT;
                    river.updateChipInventory(LB_AMT);
                    players.get(whoseTurn).setTurn(false);
                    blindCount++;
                    whoseTurn = (whoseTurn + 1) % MAX_PLAYERS;
                } else {
                    players.get(whoseTurn).setTurn(true);
                    players.get(whoseTurn).raise(model.callAmt, model.minRaise, BB_AMT);
                    model.callAmt = BB_AMT;
                    river.updateChipInventory(BB_AMT);
                    players.get(whoseTurn).setTurn(false);
                    blindCount++;
                    whoseTurn = (whoseTurn + 1) % MAX_PLAYERS;
                }
            }
            return whoseTurn;
        }
        /**
         int count = 0;
         //now the same betting logic can be applied to all rounds
         //minimum betting for a round (everyone calls)
         while (count <= MAX_PLAYERS){
         if (players.get(whoseTurn).playerExists()){
         // from here the player can call, raise (implied call + more) or fold
         players.get(whoseTurn).setTurn(true);
         int playerBetAmt = raise + call;
         if (playerBetAmt < callAmt && (notFolded || allIn)){  error  };
         //whatever else we need to do
         count++;
         whoseTurn = (whoseTurn + 1) % MAX_PLAYERS;
         }
         //now check if pot is right after every bet
         while (!potRight())
             //listen and perform betting action for player whose turn it is
             //update whose turn it is
         }
         */
        else return -1; //not fully functional yet
    }

    public int calcCallAmt(int playerID){
        return model.callAmt - players.get(playerID).getAmountBet();
    }

    /**
     * Not being used yet, but will be used to check whether or
     * not we are ready to move onto the next part of the hand.
     * A betting round is not over until all players have either
     * folded, gone all in, or have matched what everyone else has
     * bet.
     *
     * @return false if betting round is not complete
     */
    public boolean potRight(){
        for (int i = 0; i < MAX_PLAYERS; i++){
            Player p = players.get(i);
            if (p.playerExists() && !p.playerFolded() && !p.allIn() &&
                    model.callAmt != p.getAmountBet()) {
                return false; //if this is the case for any player, betting must continue
            }
        }
        return true;
    }

    /**
     * playHand is not being used for GameState submission
     * I plan on calling the betLoop, flip community cards,
     * and a hand comp/chip allocation within this method
     *
     * @return Not sure yet
     */
    public boolean playHand(){
        return true;
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
