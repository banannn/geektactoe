package net.banys.geektactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.banys.geektactoe.game.GameManager;
import net.banys.geektactoe.utils.StringUtils;
import net.banys.geektactoe.views.ConsoleView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.input) EditText input;
    @Bind(R.id.console) ConsoleView console;
    private GameManager mGameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (!StringUtils.isEmptyOrNull(textView.getText().toString())) {
                    mGameManager.processCommand(textView.getText().toString());
                    input.setText("");
                    return true;
                }
                return false;
            }
        });
        mGameManager = new GameManager(console);
        mGameManager.init();
    }

}
