package edu.up.cs301.gamestate;

import java.util.ArrayList;

/**
 * @author Andrew, Alex
 *
 * @desc Creates a player object. Similar to
 * the river, it can hold cards and chips (the
 * rivers chips are the pot). Player class is a
 * child of CardHolder and sibling to River.
 */
public class Player extends CardHolder{
    private boolean isDealer;

    //we will always have the max number of players created.
    //this is what tells us if the are actually playing.
    //Should they be dealt to, have chips, etc.
    private boolean exists;
    private boolean folded;
    private int playerID;
    //in order to move onto the next round of betting
    //every player needs to have bet the same amount, be
    //folded, or have gone "all in"
    //amountBet will be what we use to add chips to the pot.
    private int amountBet;
    private boolean isTurn;
    private boolean allIn;

    public Player(int playerID){
        super(1000, 2);
        this.isDealer = false; //determines LB and BB
        this.isTurn = false;
        this.exists = false;
        this.folded = false;
        this.allIn = false;
        this.amountBet = 0;
        this.playerID = playerID;
    }

    //setters
    //Does the player exist within the context of them game
    public void setExists(boolean exists){  this.exists = exists;  }
    public void setDealer(boolean isDealer){  this.isDealer = isDealer;  }
    public void setTurn(boolean turn){  this.isTurn = turn;  }

    //will need to set players turn to true everytime we want to toggle any
    //hand specific field (raise, fold, call, all-in). It's the least intuitive
    //when we need to unfold folded players in between hands.
    public boolean fold(boolean folded){
        if (this.exists && isTurn && !allIn) {
            this.folded = folded;
            return true;
        }
        return false;
    }

    //will use the pass in model.callAmt
    //pot will be updated using the sum of amount bets
    //at the end of a betting action, all players should have
    //bet the same amount, be folded, or be all in
    public boolean call(int callAmt){
        if (exists && isTurn && !folded) {
            int bet = callAmt - amountBet;
            //player has the money to call
            if (bet <= getChipInventory()){
                amountBet += bet;
                this.updateChipInventory(-bet);
            }
            else {  return goAllIn();  } //player must go all in
            return true;
        }
        return false;
    }
    public boolean raise(int callAmt, int minRaise, int raiseAmt){
        if (exists && isTurn && !folded && minRaise <= raiseAmt) {
            int bet = callAmt - amountBet + raiseAmt;
            if (bet <= getChipInventory()){
                amountBet += bet;
                updateChipInventory(-bet);
            }
            else {  return goAllIn();  } //Not enough funds, go all in
            return true;
        }
        return false;
    }
    public boolean goAllIn(){
        if (exists && isTurn && !folded) {
            allIn = true;
            amountBet += this.getChipInventory();
            this.updateChipInventory(-amountBet);
            return true;
        }
        return false;
    }
    public boolean resetPlayers() {
        //will handle toggling dealer in game loop
        //should be performed after chips have been allocated to winners
        //since this will toggle exists for any player with no chips
        if (exists){
            allIn = false;
            folded = false;
            isTurn = false;
            amountBet = 0;
            if (getChipInventory() == 0){  exists = false;  }
            return true;
        }
        return false;
    }
    //getters
    public boolean isDealer(){  return isDealer;  }
    public boolean playerExists(){  return exists;  }
    public boolean playerFolded(){  return folded;  }
    public int getPlayerID(){  return playerID;  }
    public boolean isPlayerTurn(){  return isTurn;  }
    public int getAmountBet(){  return amountBet;  }
    public boolean allIn(){  return allIn;  }

    @Override
    public String toString() {
        String output = "\nPlayer" + playerID + ": \n";
        output += "Exists within the context of the game: " + exists + "\n";
        output += "Has this many chips: " + getChipInventory() + "\n";
        output += "After having bet: " + amountBet + " this hand\n";
        output += "Is the dealer: " + isDealer + "\n";
        output += "Has folded: " + folded + "\n";
        output += "Is all-in: " + allIn + "\n";
        output += "It is their turn: " + isTurn + "\n";
        output += "Has these cards in their hand:\n";
        ArrayList<Card> hand = getHand();
        for (Card c : hand){
            output += c.toString() + "\n";
        }
        return output;
    }
}
