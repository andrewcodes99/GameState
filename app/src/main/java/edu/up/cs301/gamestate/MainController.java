package edu.up.cs301.gamestate;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainController implements View.OnClickListener {
    private GameModel model;
    private EditText editText;

    public MainController(GameModel model, EditText editText, Button runTest) {
        this.model = model;
        this.editText = editText;

        runTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        model.setMessage("You clicked me");
        editText.setText(model.getMessage());
    }
}
