package edu.up.cs301.gamestate;

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

    public Player(int playerID){
        super(1000, 2);
        this.isDealer = false; //determines LB and BB
        this.exists = false;
        this.folded = false;
        this.playerID = playerID;
    }

    //setters
    //Does the player exist within the context of them game
    public void setExists(boolean exists){  this.exists = exists;  }
    public void setDealer(boolean isDealer){  this.isDealer = isDealer;  }
    public void setFolded(boolean folded){  this.folded = folded;  }

    //getters
    public boolean isDealer(){  return isDealer;  }
    public boolean playerExists(){  return exists;  }
    public boolean playerFolded(){  return folded;  }
    public int getPlayerID(){  return playerID;  }

    @Override
    public String toString() {
        String output = "Player " + playerID + ": ";
        output += "Dealer=" + isDealer + ", ";
        output += "Exists=" + exists + ", ";
        output += "Folded=" + folded + ", ";
        output += "Hand=" + getHand();
        return output;
    }


}
