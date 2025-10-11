package edu.up.cs301.gamestate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.*;

/**
 * @author Andrew, Alex, Nikos, Joseph
 *
 * @desc Creates a deck object (an array list of cards)
 * Cards are physically removed from the deck and placed into
 * the players hand. Deck order is randomized for shuffling.
 */
public class Deck {
    private ArrayList<Card> deck;
    private static final int deckLen = 52; //TODO: suits * values

    /**
     * Deck
     *
     * @desc the default constructor for the deck obj
     */
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


    /**
     * Deck (copy const)
     *
     * @desc deep copy constructor for the deck class. Used stack overflow
     *      to copy array list.
     *
     * @param other the other deck object that we want to create the copy of
     */
    //https://stackoverflow.com/questions/16328602/java-copy-constructor-arraylists
    public Deck(Deck other){
        this.deck = new ArrayList<Card>(other.deck.size());
        //copys the cards in the order they are in within the passed in deck
        for (Card c : other.deck) {
            this.deck.add(new Card(c));
        }
    }

    //not being used for gamestate submission
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    /**
     * dealCard
     * @desc adds the last card in the deck (assumed to be shuffled) to
     *      the recipients hand and removes it from the deck.
     *
     * @param recipient which player (or the river) should receive the card
     */
    public void dealCard(CardHolder recipient){
        //add card obj to players hand
        recipient.setHand(deck.get(deck.size() - 1));
        //remove card obj from deck
        deck.remove(deck.size() - 1);
    }

    /**
     * ToString
     *
     * @desc Returns a string representation of the current deck.
     *
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