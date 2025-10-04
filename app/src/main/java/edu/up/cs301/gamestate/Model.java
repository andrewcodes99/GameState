package edu.up.cs301.gamestate;

public class Model {
    //temp var that applied to all players
    //chipAddition - callAmt = RaiseAmt
    public int currentBet = 0;
    //call amt is a running total of what is required to
    //remain in the hand
    public int callAmt = 0;

    //how much a player needs to raise
    //each raise must be at least the size of the last raise
    public int minRaise = 0;
}
