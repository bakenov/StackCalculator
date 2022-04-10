package com.test.calculator.command;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static com.test.calculator.command.CommandType.UNKNOWN;

public class CalcCommand {

    private CommandType type;
    private double value;

    public CalcCommand() {
        reset();
    }

    public void reset() {
        this.type = UNKNOWN;
        this.value = NaN;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public CommandType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return type.toString() + (isNaN(value) ? "" : " (" + value +")");
    }

}
