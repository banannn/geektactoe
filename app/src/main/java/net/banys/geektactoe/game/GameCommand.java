package net.banys.geektactoe.game;

/**
 * Created by adam on 22.10.15.
 * class represents available game commands
 */
public enum GameCommand {

    COMMAND_HELP("help", "show this message"),
    COMMAND_START("start", "starts new game"),
    COMMAND_STATE("state", "prints current game state"),
    COMMAND_MARK("mark", "marks field; usage: 'mark n', where 0<=n<=8");

    GameCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    private final String command;
    private final String description;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

}
