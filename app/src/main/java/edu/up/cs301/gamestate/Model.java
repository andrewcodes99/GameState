package edu.up.cs301.gamestate;

/**
 * @author Andrew, Alex, Nikos, Joseph
 *
 * @desc Holds game data used during play.
 * Tracks current betting information. Will acts as the central
 * data model for the controller to reference and update.
 */

public class Model {
    //call amt is a running total of what is required to
    //remain in the hand. Should be reset each hand.
    public int callAmt = 0;

    //how much a player needs to raise to be able to raise
    //each raise must be at least the size of the last raise
    //this should be reset at the end of each betting round.
    public int minRaise = 0;

    //if playersFolded == playerCount - 1: hand ends
    //reset each hand
    public int playersFolded = 0;

    public String output = "";

}
