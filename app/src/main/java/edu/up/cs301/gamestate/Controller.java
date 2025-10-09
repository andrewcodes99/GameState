package edu.up.cs301.gamestate;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author ...
 *
 * @desc Acts as the controller in the MVC pattern.
 * Handles communication between the GameState (model)
 * and the UI components (view). Will later include logic
 * for handling button clicks and updating the model.
 */

public class Controller{
    private Model model;  // reference to the data model that holds game info
    private GameState game;  // reference to the overall game state

    /**
     * Constructor initializes the controller with a GameState object.
     * Grabs the model from the GameState to interact with game data.
     */
    public Controller(GameState game) {
        this.game = game;
        model = game.getModel();
    }
}
