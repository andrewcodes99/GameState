package edu.up.cs301.gamestate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        Button runTest = findViewById(R.id.runTest);

        //this will need to depend on user selections
        //should also eventually include which bots
        GameState game = new GameState(5);
        game.getPlayers().get(3).setDealer(true); //set dealer
        game.setup(0);
    }
}