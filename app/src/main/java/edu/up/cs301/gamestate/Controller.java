package edu.up.cs301.gamestate;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Controller{
    private Model model;
    private GameState game;

    public Controller(GameState game) {
        this.game = game;
        model = game.getModel();
    }
}
