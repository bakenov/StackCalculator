package com.test.calculator.calcservice;

import com.test.calculator.command.CalcCommand;
import com.test.calculator.command.CommandType;
import com.test.calculator.stack.IRestorableStack;
import com.test.calculator.stack.IStackIterator;

import java.io.PrintStream;

import static java.lang.Double.isNaN;
import static com.test.calculator.command.CommandType.UNDO;

public class CalculationService implements ICalculationService {

    private static final String STACK_EMPTY_MESSAGE = "The stack is empty.";

    private final IRestorableStack stack;
    private final PrintStream out;
    private CommandType prevCommandType;


    public CalculationService(IRestorableStack stack, final PrintStream out) {
        this.stack = stack;
        this.out = out;
        this.prevCommandType = null;
    }

    protected IRestorableStack getStack() {
        return stack;
    }

    @Override
    public boolean processCommand(CalcCommand command) {
        String validationRes = validateCommand(command);
        if (validationRes != null) {
            out.println("The command " + command + " is not valid. " + validationRes);
            return false;
        }

        if (command.getType() == UNDO) {
            return processUndoCommand();
        }

        if (command.getType().isUndoeble()) {
            stack.setCheckpoint();
            prevCommandType = command.getType();
        }

        switch (command.getType()) {
            case PUSH:
                stack.push(command.getValue());
                break;
            case POP:
                if (stack.isEmpty()) {
                    out.println(STACK_EMPTY_MESSAGE);
                    return false;
                }
                stack.pop();
                break;
            case ADD:
                double value1 = stack.pop();
                double value2 = stack.pop();
                stack.push(value1 + value2);
                break;
            case MUL:
                value1 = stack.pop();
                value2 = stack.pop();
                stack.push(value1 * value2);
                break;
            case NEG:
                value1 = stack.pop();
                stack.push(-value1);
                break;
            case INV:
                value1 = stack.pop();
                if (value1 != 0.0) {
                    stack.push(1 / value1);
                    break;
                }
                stack.push(value1);
                out.println("The command " + command + " cannot be done. the top value is 0.");
                return false;
            case CLEAR:
                stack.clear();
                assert(stack.isEmpty());
                out.println(STACK_EMPTY_MESSAGE);
                return true;
            case PRINT:
                printStackContent();
                return true;
            default:
                return false;
        }

        out.println("Top element:" + stack.top());
        return true;
    }

    String validateCommand(CalcCommand command) {
        switch (command.getType()) {
            case UNKNOWN:
                return "Unknown command";
            case PUSH:
                if (isNaN(command.getValue()))
                    return "Invalid digital parameter for the PUSH command";
                break;
            case ADD:
            case MUL:
                if (stack.size() < 2)
                    return "There are less than two elements in the stack";
                else if (!isNaN(command.getValue()))
                    return "The digital parameter for this command is prohibited";
                break;
            case NEG:
            case INV:
                if (stack.size() < 1)
                    return "The stack is empty, the command cannot be done";
                else if (!isNaN(command.getValue()))
                    return "The digital parameter for this command is prohibited";
                break;
            default:
                if (!isNaN(command.getValue()))
                    return "The digital parameter for this command is prohibited";
        }
        return null;

    }

    boolean processUndoCommand() {
        boolean retVal = true;
        if (prevCommandType == null || !prevCommandType.isUndoeble()) {
            out.println("cannot fulfil UNDO command, previous valid command is " + (prevCommandType != null ? prevCommandType : "NULL"));
            retVal = false;
        } else {
            stack.restore();
            prevCommandType = UNDO;
        }
        double v = stack.top();
        out.println("Top element:" + (isNaN(v) ? "" : v));
        return retVal;
    }

    private void printStackContent() {
        if (stack.isEmpty()) {
            out.println(STACK_EMPTY_MESSAGE);
            return;
        }
        out.println("Stack content:");
        IStackIterator iter = stack.getIterator();
        while (iter.hasNext()) {
            out.println(iter.next());
        }
    }

}
