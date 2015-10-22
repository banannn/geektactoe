package net.banys.geektactoe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.banys.geektactoe.R;

/**
 * Created by adam on 22.10.15.
 */
public class ConsoleView extends FrameLayout {

    private static final String USER_PREFIX = "user $\t";
    private static final String GAME_PREFIX = "game $\t";

    private TextView mConsole;
    private ScrollView mScrollView;

    private Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };

    public ConsoleView(Context context) {
        super(context);
        init(context);
    }

    public ConsoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConsoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.console_view, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mConsole = (TextView)findViewById(R.id.output);
        mScrollView = (ScrollView)findViewById(R.id.scroll);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void appendUserMessage(String text) {
        append(USER_PREFIX, text);
    }

    public void appendGameMessage(String text) {
        append(GAME_PREFIX, text);
    }

    private void append(String prefix, String text) {
        mConsole.append("\n");
        mConsole.append(prefix + text);
        mScrollView.post(scrollRunnable);
    }



}
