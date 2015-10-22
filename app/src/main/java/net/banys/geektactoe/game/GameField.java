package net.banys.geektactoe.game;

/**
 * Created by adam on 22.10.15.
 */
public enum GameField {

    EMPTY("*"), CROSS("X"), CIRCLE("O");

    GameField(String sign) {
        this.sign = sign;
    }

    private final String sign;

    public String getSign() {
        return sign;
    }
}
