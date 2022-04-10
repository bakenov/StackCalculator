package com.test.calculator.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CommandType {

    PUSH(true), POP(true), CLEAR(true), ADD(true), MUL(true), NEG(true), INV(true), UNDO(false), PRINT(false), QUIT(false), UNKNOWN(false);

    private final boolean undoeble;
    private static Map<String, CommandType> MAP;

    CommandType(final boolean undoeble) {
        this.undoeble = undoeble;
    }

    public boolean isUndoeble() {
        return undoeble;
    }

    public static CommandType getCommandByName(final String name) {
        if (MAP == null) {
            MAP = new HashMap<>();
            Arrays.stream(CommandType.values()).forEach(c -> MAP.put(c.toString(), c));
        }
        return MAP.get(name);
    }
}
