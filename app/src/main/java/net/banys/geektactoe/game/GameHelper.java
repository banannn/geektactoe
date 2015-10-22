package net.banys.geektactoe.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 22.10.15.
 */
public class GameHelper {

    public static List<GameCommand> getCommandsForCurrentState(GameState state) {
        List<GameCommand> commands = new ArrayList<>();
        if (state == GameState.STATE_UNKNOWN) {
            commands.add(GameCommand.COMMAND_HELP);
            commands.add(GameCommand.COMMAND_START);
        } else {
            commands.add(GameCommand.COMMAND_HELP);
            commands.add(GameCommand.COMMAND_STATE);
            commands.add(GameCommand.COMMAND_MARK);
        }
        return commands;
    }

    public static GameCommand resolveCommand(GameState state, String command) {
        for (GameCommand availableCommand : getCommandsForCurrentState(state))
            if (availableCommand.getCommand().equalsIgnoreCase(command)) return availableCommand;
        // check for "mark" message
        String[] parts = command.split("\\s+");
        if (parts.length == 2){
            if (parts[0].equalsIgnoreCase(GameCommand.COMMAND_MARK.getCommand())) {
                if (getFieldValue(parts[1]) >= 0) return GameCommand.COMMAND_MARK;
            }
        }
        return null;
    }

    // returns field number for mark message
    // returns -1 for bad number
    public static short getFieldForMarkMessage(String command) throws NumberFormatException {
        String[] parts = command.split("\\s+");
        if (parts.length == 2){
            if (parts[0].equalsIgnoreCase(GameCommand.COMMAND_MARK.getCommand())) {
                return getFieldValue(parts[1]);
            }
        }
        return -1;
    }


    // returns -1 for bad number
    private static short getFieldValue(String val) {
        try {
            short value = Short.parseShort(val);
            if (value >= 0 && value <=8) return value;
        } catch (NumberFormatException e) {}
        return -1;
    }


    // returns array with indexes of fields in the same row for given element
    public static int[] getRowIndices(short element) {
        int result[] = new int[2];
        int position = element%3;
        switch (position) {
            case 0:
                result[0] = element+1;
                result[1] = element+2;
                break;
            case 1:
                result[0] = element+1;
                result[1] = element-1;
                break;
            case 2:
                result[0] = element-1;
                result[1] = element-2;
        }
        return result;
    }

    // returns array with indexes of fields in the same column for given element
    public static int[] getColumnIndices(short element) {
        int result[] = new int[2];
        int position = element/3;
        switch (position) {
            case 0:
                result[0] = element+3;
                result[1] = element+6;
                break;
            case 1:
                result[0] = element-3;
                result[1] = element+3;
                break;
            case 2:
                result[0] = element-3;
                result[1] = element-6;
        }
        return result;
    }

    // returns array with indexes of fields on the same diagonal for given element (including element)
    public static int[] getDiagonalIndices(short element) {
        if (element%2 != 0) return null;    // not on diagonal
        int result[];
        if (element == 4) {  // special case, will return array of lenght =5 (2 diagonals
            result = new int[6];
            result[0] = 0;
            result[1] = 4;
            result[2] = 8;
            result[3] = 2;
            result[3] = 4;
            result[3] = 6;
        }
        result = new int[3];
        if (element%4 == 0) {
            result[0] = 0;
            result[1] = 4;
            result[2] = 8;
        }
        else {
            result[0] = 2;
            result[1] = 4;
            result[2] = 6;
        }
        return result;
    }


}
