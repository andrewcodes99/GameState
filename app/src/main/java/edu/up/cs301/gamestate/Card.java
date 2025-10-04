package edu.up.cs301.gamestate;

/**
 * @author Andrew, Alex
 *
 * @desc Creates a card object that will be used within the deck.
 * We had a tough time with the graphics, and creating a small framework
 * gave us a much better idea of what we needed.
 *
 */
public class Card {
    private SUIT suit;
    private VALUE value;
    //private boolean dealt;
    private boolean flipped;
    private int cardID;


    enum SUIT {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    enum VALUE {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    //could've added an ownedBy property, but doing so
    //would mean having to loop through 52 cards on top of everything
    //else everytime the drawing is invalidated. Super slow. Make player
    //have a hand.
    public Card(SUIT suit, VALUE value, boolean flipped, int cardID) {
        this.suit = suit;
        this.value = value;
        //this.dealt = dealt; TODO: since we are physically adding to hand
        this.flipped = flipped;
        this.cardID = cardID;
    }

    //getters
    public boolean isFlipped() {
        return flipped;
    }

    //could be changed to isDealt().

    //https://stackoverflow.com/questions/6667243/using-enum-values-as-string-literals
    //could be useful when drawing bitmaps
    public String getCardName() {
        return suit.toString() + " of " + value.toString();
    }

    public int getCardID() {
        return cardID;
    }

    //setters
    //make deal card return the card that invoked it. Will be
    //necessary when we deal a card from the deck and want to
    //place it in a players hand.

    //should the face of this card be draw (flipped = true)
    //or the back?
    public void flipCard(boolean isFlipped) {
        this.flipped = isFlipped;
    }

    @Override
    public String toString() {
            if (flipped) {
                return getCardName() + " is face up and has a card ID of " + cardID;
            }
            else {
                return getCardName() + " is face down and has a card ID of " + cardID;
            }
        }

    }

