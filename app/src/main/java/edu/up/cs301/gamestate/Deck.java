package edu.up.cs301.gamestate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.*;

/**
 * @author Andrew, Alex
 *
 * @desc Creates a deck object (an array list of cards)
 * When a card is dealt, a card property will be toggled.
 * When the deck is shuffled, the dealt properties of the
 * cards will be set to false, allowing them to be selected
 * again.
 */
public class Deck {
    private ArrayList<Card> deck;
    private static final int deckLen = 52;


    //https://stackoverflow.com/questions/1104975/a-for-loop-to-iterate-over-an-enum-in-java
    public Deck(){
        deck = new ArrayList<>();
        //add a card for all 52 cards that exist within the deck
        for (Card.SUIT suit : Card.SUIT.values()) {
            for (Card.VALUE value : Card.VALUE.values()) {
                deck.add(new Card(suit, value, false, deck.size()));
            }
        }
        //sanity check
        if (deck.size() != deckLen) {
            System.out.println("Wrong number of cards in deck");
        }
    }//constructor

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    //returns cardID so that the card can be added to players hand
    public void dealCard(CardHolder recipient){
        //add card obj to players hand
        recipient.setHand(deck.get(deck.size() - 1));
        //remove card obj from deck
        deck.remove(deck.size() - 1);
    }

    /**
     * @desc Returns a string representation of the current deck.
     * Displays the number of cards remaining in the deck, followed
     * by each card's details on a new line. Primarily used for
     * debugging or verifying that the deck is initialized,
     * shuffled, and dealt correctly.
     */

    @Override
    public String toString() {
        String output = "Deck has " + deck.size() + " cards:\n";
        for (Card c : deck) {
            output += c.toString() + "\n";
        }
        return output;
    }
}