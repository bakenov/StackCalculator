package com.test.calculator.command;

import java.io.PrintStream;

import static com.test.calculator.command.CommandType.*;
import static java.lang.Double.NaN;


public class CommandBuilder implements ICommandHandler {

    private final static String SEPARATOR = " ";

    private final CalcCommand reusableCommand;
    private final PrintStream out;

    public CommandBuilder(final PrintStream out) {
        this.reusableCommand = new CalcCommand();
        this.out = out;
    }

    @Override
    public CalcCommand processCommand(String commandStr) {
        String[] array = commandStr.split(SEPARATOR);
        CommandType command = UNKNOWN;
        double value = NaN;
        if (array.length >= 1) {
            command =  getCommand(array[0].trim());
            if (array.length == 2) {
                try {
                    value = Double.parseDouble(array[1].trim());
                } catch (NumberFormatException ex) {
                    out.println("Error converting input parameter to double. " + ex);
                }
            }
        }
        if (command == UNKNOWN)
            out.println("Unknown command.");
        reusableCommand.reset();
        reusableCommand.setType(command);
        reusableCommand.setValue(value);
        return reusableCommand;
    }

    private CommandType getCommand(String cmdString) {
        CommandType type = getCommandByName(cmdString);
        if (type == null)
            type = UNKNOWN;
        return type;
    }

}
