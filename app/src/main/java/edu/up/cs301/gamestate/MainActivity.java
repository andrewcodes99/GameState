package edu.up.cs301.gamestate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author
 *
 * @desc Handles initializing the UI, setting up the game,
 * and connecting the button click to the game logic.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

       // UI components
        EditText editText = findViewById(R.id.editText);
        Button runTest = findViewById(R.id.runTest);

        //this will need to depend on user selections
        //should also eventually include which bots
        GameState game = new GameState(5);

        //TODO: not being used, not sure how to set a view in controller
        // so I use anonymous class
        Controller cc = new Controller(game);
        Model model = game.getModel();

        runTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n") //the error was annoying me
            @Override
            public void onClick(View v) {
                model.output += "\n\nGame constructor: \n\n";
                model.output += game.toString();
                //Expecting 52 cards, all face down.
                //5 players existing within the context of the game, 1000 chips, and all else false
                //A river with no chips or cards
                //simulates setting up the game
                model.output += "\n\nGame init: \n\n";
                game.getPlayers().get(3).setDealer(true); //set dealer
                game.setup(0);
                model.output += game.toString();
                //displays gamae info in edit text view
                editText.setText(model.output);
            }
        });
    }
}