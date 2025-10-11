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

        runTest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n") //the error was annoying me
            @Override
            public void onClick(View v) {
                GameState firstInstance = new GameState(5);
                Model model = firstInstance.getModel();
                model.output = ""; //clear output

                //copy const
                GameState secondInstance = new GameState(firstInstance);
                
                //Expecting 26 cards, all face down.
                //5 players existing within the context of the game, 1000 chips, and all else false
                //A river with no chips or cards
                model.output += "\n\nFirst Instance Construction: \n";
                model.output += firstInstance.toString();

                //should be 26-15 = 11 cards in the deck
                //2 in each players hand and 5 in the community cards
                //first betting round will be all calls and a fold
                //we should have player 2 folded, and everyone else bet 10 (40 in pot)
                //starts with player after big blind
                //there are a bunch of moves rolled into one print action. we printed every
                //betting round rather than every action, otherwise we would have 50 pages of prints
                model.output += "\n\nPre-flop betting (first instance): \n";
                firstInstance.getPlayers().get(3).setDealer(true); //set dealer to user
                //returns whoseTurn (within method, player 4 raises 5, player 0 raises 10 for the blinds)
                firstInstance.setup(0);
                firstInstance.getPlayers().get(1).setTurn(true);
                firstInstance.fold(1);
                firstInstance.getPlayers().get(2).setTurn(true);
                firstInstance.call(2);
                firstInstance.getPlayers().get(3).setTurn(true);
                firstInstance.call(3);
                firstInstance.getPlayers().get(4).setTurn(true); //back to small blind
                firstInstance.call(4);
                //since no-one raised, the bigblind is the last player to get
                //a chance to raise in the preflop betting
                firstInstance.getPlayers().get(0).setTurn(true);
                firstInstance.call(0);
                model.output += firstInstance.toString();

                //check to see that potRight is working as expected
                model.output += "\nPot Right?   ";
                model.output += firstInstance.potRight() + "\n";

                //second round of betting
                //player 4 folds but and bet the 10 in the last round
                //everyone but player 2 bets an additional 40 (50 total)
                //pot should be 160 now
                model.output += "\n\nflop betting (first instance): \n";
                //need to reset minraise each betting round
                model.minRaise = 0;
                //little blind starts the betting for each round
                //player 1 has folded
                firstInstance.getPlayers().get(4).setTurn(true);
                firstInstance.raise(4, 20);
                firstInstance.getPlayers().get(0).setTurn(true);
                firstInstance.raise(0, 20);
                firstInstance.getPlayers().get(2).setTurn(true);
                firstInstance.call(2);
                firstInstance.getPlayers().get(3).setTurn(true);
                firstInstance.fold(3);
                firstInstance.getPlayers().get(4).setTurn(true);
                firstInstance.call(4);
                model.output += firstInstance.toString();

                //third round of betting
                //player 1 folds (bet 50 total)
                //player 3 and 5 each bet an additional 50 (100 total)
                //pot should be 260
                model.output += "\n\nturn betting (first instance): \n";
                model.minRaise = 0;
                firstInstance.getPlayers().get(4).setTurn(true);
                firstInstance.raise(4, 50);
                firstInstance.getPlayers().get(0).setTurn(true);
                firstInstance.fold(0);
                firstInstance.getPlayers().get(2).setTurn(true);
                firstInstance.call(2);
                model.output += firstInstance.toString();

                //player 3 goes all in (1000 total)
                //player 5 folds
                //pot should be 1160
                model.output += "\n\nriver betting (first instance): \n";
                model.minRaise = 0;
                firstInstance.getPlayers().get(4).setTurn(true);
                firstInstance.call(4);
                firstInstance.getPlayers().get(2).setTurn(true);
                firstInstance.goAllIn(2);
                firstInstance.getPlayers().get(4).setTurn(true);
                firstInstance.fold(4);
                model.output += firstInstance.toString();

                //TODO: some sort of teardown hand method would be useful

                //create new instance of gamestate using default const
                GameState thirdInstance = new GameState(5);

                //check that second and third instances are equal
                String second = secondInstance.toString();
                String third = thirdInstance.toString();
                model.output += "\n\nCopy Constructor test:\n" + second.equals(third);

                //include second and third instances in output
                model.output += "\n\nSecond Instance:\n";
                model.output += second;

                model.output += "\n\nThird Instance:\n";
                model.output += third;

                //displays game info in edit text view
                editText.setText(model.output);
            }
        });
    }
}