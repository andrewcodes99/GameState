package edu.up.cs301.gamestate;

import java.util.ArrayList;

public class GameState {
    public static final int MAX_PLAYERS = 5;
    public static final int USER_PLAYER_ID = 3;
    public static final int LB_AMT = 5;
    public static final int BB_AMT = 10;
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

        //TODO: will probably want to move this and the getter to
        //  the view
        model = new Model();
        model.chipsInCirc = playerCount * Player.initChipCount;
    }

    //deep copy constructor for the GameState
    public GameState(GameState other){
        this.playerCount = other.playerCount;
        this.deck = new Deck(other.deck);
        this.river = new River(other.river);

        this.players = new ArrayList<Player>(other.players.size());
        for (Player p : other.players){
            this.players.add(new Player(p));
        }

        this.model = other.model;
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
                    raise(whoseTurn, LB_AMT);
                    blindCount++;
                    whoseTurn = (whoseTurn + 1) % MAX_PLAYERS;
                } else {
                    players.get(whoseTurn).setTurn(true);
                    raise(whoseTurn, BB_AMT-LB_AMT);
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
    public int getBets(){
        int betTotal = 0;
        for (int i = 0; i < MAX_PLAYERS; i++){
            betTotal += players.get(i).getAmountBet();
        }
        return betTotal;
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


    //Poker action methods
    public boolean fold(int playerID){
        Player p = players.get(playerID);
        if (p.playerExists() && p.isPlayerTurn() && !p.allIn()) {
            p.setFolded(true);
            p.setTurn(false);
            model.playersFolded++;
            return true;
        }
        return false;
    }

    //will use the pass in model.callAmt
    //pot will be updated using the sum of amount bets
    //at the end of a betting action, all players should have
    //bet the same amount, be folded, or be all in
    public boolean call(int playerID){
        Player p = players.get(playerID);
        if (p.playerExists() && p.isPlayerTurn() && !p.allIn()) {
            int bet = model.callAmt - p.getAmountBet();
            //player has the money to call
            if (bet <= p.getChipInventory()){
                p.updateAmtBet(bet);
                p.updateChipInventory(-bet);
                river.updateChipInventory(bet);
                System.out.println(playerID);
                System.out.println(bet);
            }
            else {  return goAllIn(playerID);  } //player must go all in
            p.setTurn(false);
            return true;
        }
        return false;
    }
    public boolean raise(int playerID, int raiseAmt){
        Player p = players.get(playerID);
        if (p.playerExists() && p.isPlayerTurn() && !p.allIn() && model.minRaise <= raiseAmt) {
            int bet = model.callAmt - p.getAmountBet() + raiseAmt;
            if (bet <= p.getChipInventory()){
                p.updateAmtBet(bet);
                p.updateChipInventory(-bet);
                river.updateChipInventory(bet);
                model.callAmt = p.getAmountBet();
                model.minRaise = raiseAmt;
            }
            else {  return goAllIn(playerID);  } //Not enough funds, go all in
            p.setTurn(false);
            return true;
        }
        return false;
    }
    public boolean goAllIn(int playerID){
        Player p = players.get(playerID);
        if (p.playerExists() && p.isPlayerTurn() && !p.allIn()) {
            int bet = p.getChipInventory();
            p.setAllIn(true);
            p.updateAmtBet(bet);
            p.updateChipInventory(-bet);
            river.updateChipInventory(bet);
            int raiseAmt = bet - model.callAmt + p.getAmountBet();
            if (model.minRaise <= raiseAmt){
                model.minRaise = raiseAmt;
            }
            model.callAmt += bet;
            p.setTurn(false);
            return true;
        }
        return false;
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

    public Model getModel(){
        return model;
    }

    @Override
    public String toString(){
        String output = "";
        output += "\nDeck: \n";
        output += deck.toString() + "\n";

        output += "Players: \n";
        for (Player p : players){
            output += p.toString();
        }

        output += "\nCommunity Cards: \n";
        output += river.toString();

        return output;
    }
}
