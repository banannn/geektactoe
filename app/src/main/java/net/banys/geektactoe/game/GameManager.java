package net.banys.geektactoe.game;

import net.banys.geektactoe.utils.StringUtils;
import net.banys.geektactoe.views.ConsoleView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by adam on 22.10.15.
 */
public class GameManager {

    private int mMovesCount;
    private boolean isCirclesTurn;

    private GameState mCurrentState;
    private GameField[] mBoard = new GameField[9];
    private WeakReference<ConsoleView> mConsole;

    public GameManager(ConsoleView console) {
        this.mConsole = new WeakReference<>(console);
    }

    public void init() {
        mConsole.get().appendGameMessage("Type help to see available commands.");
        mCurrentState = GameState.STATE_UNKNOWN;
    }

    public void processCommand(String userCommand) {
        if (!StringUtils.isEmptyOrNull(userCommand)) {
            mConsole.get().appendUserMessage(userCommand);
            GameCommand command = GameHelper.resolveCommand(mCurrentState, userCommand);
            if (command == GameCommand.COMMAND_HELP) printHelpMessage();
            else if (command == GameCommand.COMMAND_START) startGame();
            else if (command == GameCommand.COMMAND_STATE) printGameState();
            else if (command == GameCommand.COMMAND_MARK) markField(GameHelper.getFieldForMarkMessage(userCommand));
            else printWrongCommandMessage();
        }
    }

    private void markField(short fieldNumber) {
        if (fieldNumber >= 0) {
            if (mBoard[fieldNumber] != GameField.EMPTY) {
                mConsole.get().appendGameMessage("Field is already marked!");
            }
            else {
                mBoard[fieldNumber] = isCirclesTurn ? GameField.CIRCLE : GameField.CROSS;
                isCirclesTurn = !isCirclesTurn;
                if (++mMovesCount >= 5) {
                    // check if someone already wins
                    if (isGameWon(fieldNumber)) {
                        String winner = isCirclesTurn ? "crosses" : "circles";
                        mConsole.get().appendGameMessage("Game finished: " + winner + " wins");
                        init();
                    }
                    else if (mMovesCount == 9) {
                        mConsole.get().appendGameMessage("Game finished: DRAW!");
                        init();
                    }
                }
            }
        } else printWrongCommandMessage();
    }

    // checks if game is won after marking last field
    private boolean isGameWon(short lastField) {
        // check row
        int[] indices = GameHelper.getRowIndices(lastField);
        if ((mBoard[lastField] == mBoard[indices[0]]) && (mBoard[lastField] == mBoard[indices[1]])) return true;
        // check column
        indices = GameHelper.getColumnIndices(lastField);
        if ((mBoard[lastField] == mBoard[indices[0]]) && (mBoard[lastField] == mBoard[indices[1]])) return true;
        indices = GameHelper.getDiagonalIndices(lastField);
        if (indices != null) {
            for (int i=0; i<indices.length; i++) {
                if ((mBoard[lastField] == mBoard[indices[i%3]]) && (mBoard[lastField] == mBoard[indices[(i+1)%3]])
                        && (mBoard[lastField] == mBoard[indices[(i+2)%3]])) return true;
            }
        }
        return false;
    }

    private void printGameState() {
        printBoard();
        printNextMoveMessage();
    }

    private void startGame() {
        // init game paramteres
        mCurrentState = GameState.STATE_PENDING;
        isCirclesTurn = true;
        mMovesCount = 0;
        for (int i=0; i< mBoard.length; i++) mBoard[i] = GameField.EMPTY;
        mConsole.get().appendGameMessage("Game started.");
        printNextMoveMessage();
    }

    private void printNextMoveMessage() {
        String message = "Next move: ";
        if (isCirclesTurn) message += "circle";
        else message += "cross";
        mConsole.get().appendGameMessage(message);
    }

    private void printHelpMessage() {
        List<GameCommand> availableCommands = GameHelper.getCommandsForCurrentState(mCurrentState);
        StringBuilder builder = new StringBuilder();
        for (GameCommand command : availableCommands)
            builder.append(command.getCommand() + " - " + command.getDescription() + "\n");
        mConsole.get().appendGameMessage(builder.toString());
    }

    // prints current state of game
    private void printBoard() {
        StringBuilder builder = new StringBuilder("Board:\n");
        for (int i=0; i< mBoard.length; i++) {
            builder.append(mBoard[i].getSign());
            if (i%3 == 2) builder.append("\n");
        }
        mConsole.get().appendGameMessage(builder.toString());
    }

    private void printWrongCommandMessage(){
        mConsole.get().appendGameMessage("Command not recognized, type help to see available commands");
    }

}
