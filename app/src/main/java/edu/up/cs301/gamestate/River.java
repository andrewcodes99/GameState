package edu.up.cs301.gamestate;

/**
 * @author Andrew
 *
 * @desc the river and a player are similar, but players require
 * more code and consideration. Rather than write similar classes
 * twice we had both be child classes of the parent CardHolder.
 */
public class River extends CardHolder{
    public River(){
        super(0, 5);
    }
}
