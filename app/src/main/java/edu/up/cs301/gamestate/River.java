package edu.up.cs301.gamestate;

import java.util.ArrayList;

/**
 * @author Andrew, Alex, Nikos, Joseph
 *
 * @desc the river and a player are similar, but players require
 * more code and consideration. Rather than write similar classes
 * twice we had both be child classes of the parent CardHolder.
 */
public class River extends CardHolder{
    //main constructor for river class
    //sets starting chips and max hand length for the pot, and community cards
    public River(){
        super(0, 5);
    }

    //copy constructor for the river
    public River(River other){  super(other);  }

    /**
     * toString
     *
     * @desc overrides toString
     *
     * @return the string representation of the river
     */
    @Override
    public String toString(){
        String output = "";
        output += "\nThe pot has " + getChipInventory() + " chips in it.\n";
        output += "And has these cards: \n";
        ArrayList<Card> communityCards = getHand();
        for (Card c : communityCards){
            output += c.toString() + "\n";
        }
        return output;
    }
}
