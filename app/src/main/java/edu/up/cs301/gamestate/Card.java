package edu.up.cs301.gamestate;

/**
 * @author Andrew, Alex, Joseph
 *
 * @desc Creates a card object that will be used within the deck.
 *
 */
public class Card {
    private SUIT suit;
    private VALUE value;
    //flipped = true means that the face of card should be drawn
    private boolean flipped;
    private int cardID;

    enum SUIT {
        HEARTS, DIAMONDS //shortened the deck for gamestate
    }

    enum VALUE {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    /**
     * Card
     *
     * @desc main card constructor
     *
     * @param suit suit of card
     * @param value value of card
     * @param flipped face of card should be drawn?
     * @param cardID int representation of card
     */
    public Card(SUIT suit, VALUE value, boolean flipped, int cardID) {
        this.suit = suit;
        this.value = value;
        this.flipped = flipped;
        this.cardID = cardID;
    }

    //copy constructor for the Card class
    public Card(Card other){
        this.suit = other.suit;
        this.value = other.value;
        this.flipped = other.flipped;
        this.cardID = other.cardID;
    }

    //getters
    public boolean isFlipped() {
        return flipped;
    }

    //https://stackoverflow.com/questions/6667243/using-enum-values-as-string-literals
    //could be useful when drawing bitmaps
    public String getCardName() {
        return value.toString() + " of " + suit.toString();
    }

    public int getCardID() {
        return cardID;
    }

    //setters
    public void flipCard(boolean isFlipped) {
        this.flipped = isFlipped;
    }

    /**
     * toString
     *
     * @desc override the toString method for card class
     *
     * @return the string representation of a card
     */
    @Override
    public String toString() {
            if (flipped) {
                return getCardName() + " is FACE UP and has a card ID of " + cardID;
            }
            else {
                return getCardName() + " is FACE DOWN and has a card ID of " + cardID;
            }
        }
    }

