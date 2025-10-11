package edu.up.cs301.gamestate;

import java.util.ArrayList;

/**
 * @author Andrew
 *
 * @desc A cardholder can be a player or the
 * river. Since the river has no chips to start and
 * 5 cards max, and a player has chips to start and
 * 2 cards, we pass in these vars into the childs const.
 */
public class CardHolder {
    private final int MAX_HAND_LENGTH;
    private ArrayList<Card> hand;
    private int chipInventory;

    /**
     * CardHolder
     *
     * @desc Main constructor for the CardHolder class. A cardholder
     *      can be a river or a player
     *
     * @param chipInventory starting chip inv
     * @param MAX_HAND_LENGTH the max # of cards that the holder can have
     */
    public CardHolder(int chipInventory, final int MAX_HAND_LENGTH){
        hand = new ArrayList<>();
        this.chipInventory = chipInventory;
        this.MAX_HAND_LENGTH = MAX_HAND_LENGTH;
    }

    //copy constructor for the CardHolder class
    public CardHolder(CardHolder other){
        this.hand = new ArrayList<Card>(other.hand.size());
        //copy the contents of the players hand
        for (Card c : other.hand){
            this.hand.add(new Card(c));
        }
        this.chipInventory = other.chipInventory;
        this.MAX_HAND_LENGTH = other.MAX_HAND_LENGTH;
    }

    //getters
    public ArrayList<Card> getHand() {  return hand;  }
    public int getChipInventory() {  return chipInventory;  }

    //setters
    //TODO: add better error checking when dealing cards
    public void setHand(Card c) {
        if(hand.size() <= MAX_HAND_LENGTH){  hand.add(c);  }
    }

    //not being used for gamestate submission
    public void removeHand(ArrayList<Card> deck){
        for (int i = 0; i < hand.size(); i++){
            deck.add(hand.get(i));
            hand.remove(i);
        }
    }
    public void updateChipInventory(int chipInventory){
        this.chipInventory += chipInventory;
    }
}

