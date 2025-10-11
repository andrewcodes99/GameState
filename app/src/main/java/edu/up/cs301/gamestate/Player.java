package edu.up.cs301.gamestate;

import java.util.ArrayList;

/**
 * @author Andrew, Alex, Nikos, Joeseph
 *
 * @desc Creates a player object. Similar to
 * the river, it can hold cards and chips (the
 * rivers chips are the pot). Player class is a
 * child of CardHolder and sibling to River.
 */
public class Player extends CardHolder{

    public final static int initChipCount = 1000;
    private boolean isDealer; //determines LB and BB

    //we will always have the max number of players created.
    //this is what tells us if the are actually playing.
    //Should they be dealt to, have chips, etc.
    private boolean exists;
    private boolean folded;
    private int playerID;
    //in order to move onto the next round of betting
    //every player needs to have bet the same amount, be
    //folded, or have gone "all in"
    private int amountBet; //reset each hand
    private boolean isTurn;
    private boolean allIn;

    /**
     * Player
     *
     * @desc main constructor for the player class
     *
     * @param playerID int representation of player
     */
    public Player(int playerID){
        super(initChipCount, 2);
        this.isDealer = false; //determines LB and BB
        this.isTurn = false;
        this.exists = false;
        this.folded = false;
        this.allIn = false;
        this.amountBet = 0;
        this.playerID = playerID;
    }

    //copy constructor for the Player class
    //https://stackoverflow.com/questions/52762980/java-how-do-i-call-copy-constructor-for-subclass-of-subclass
    public Player(Player other){
        super(other);
        this.isDealer = other.isDealer;
        this.isTurn = other.isTurn;
        this.exists = other.exists;
        this.folded = other.folded;
        this.allIn = other.allIn;
        this.amountBet = other.amountBet;
        this.playerID = other.playerID;
    }

    //setters
    //Does the player exist within the context of them game
    public void setExists(boolean exists){  this.exists = exists;  }
    public void setDealer(boolean isDealer){  this.isDealer = isDealer;  }
    public void setTurn(boolean turn){  this.isTurn = turn;  }
    public void setFolded(boolean folded){  this.folded = folded;  }
    public void updateAmtBet(int updateAmt){  amountBet += updateAmt;  }
    public void setAllIn(boolean allIn){  this.allIn = allIn;  }
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

    /**
     * toString
     *
     * @desc Returns a formatted string that displays the player's
     * current status in the game.
     *
     * @return the resulting string
     */
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
