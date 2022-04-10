package com.test.calculator.calcservice;

import com.test.calculator.command.CalcCommand;
import com.test.calculator.command.CommandType;
import com.test.calculator.stack.IRestorableStack;
import com.test.calculator.stack.RestorableStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.test.calculator.command.CommandType.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationServiceTest {

    private CalculationService service;

    @BeforeEach
    public void before() {
        service = new CalculationService(
                new RestorableStack(5), System.out);
    }

    private CalcCommand buildCommand(CommandType type) {
        return buildCommand(type, NaN);
    }

    private CalcCommand buildCommand(CommandType type, double value) {
        CalcCommand cmd = new CalcCommand();
        cmd.setType(type);
        cmd.setValue(value);
        return cmd;
    }

    @Test
    public void validateCommand() {
        String res = service.validateCommand(buildCommand(PUSH, 1.2));
        assertNull(res);
        res = service.validateCommand(buildCommand(POP));
        assertNull(res);
        res = service.validateCommand(buildCommand(PRINT));
        assertNull(res);
        res = service.validateCommand(buildCommand(CLEAR));
        assertNull(res);
        res = service.validateCommand(buildCommand(ADD));
        assertEquals("There are less than two elements in the stack", res);
        res = service.validateCommand(buildCommand(MUL));
        assertEquals("There are less than two elements in the stack", res);
        res = service.validateCommand(buildCommand(NEG));
        assertEquals("The stack is empty, the command cannot be done", res);
        res = service.validateCommand(buildCommand(INV));
        assertEquals("The stack is empty, the command cannot be done", res);
        // fill stack
        service.processCommand(buildCommand(PUSH, 0.0));
        service.processCommand(buildCommand(PUSH, 1.0));
        res = service.validateCommand(buildCommand(NEG));
        assertNull(res);
        res = service.validateCommand(buildCommand(INV));
        assertNull(res);
        res = service.validateCommand(buildCommand(ADD));
        assertNull(res);
        res = service.validateCommand(buildCommand(MUL));
        assertNull(res);

        res = service.validateCommand(buildCommand(UNKNOWN));
        assertEquals("Unknown command", res);
        res = service.validateCommand(buildCommand(PUSH));
        assertEquals("Invalid digital parameter for the PUSH command", res);
        res = service.validateCommand(buildCommand(POP, 1.2));
        assertEquals("The digital parameter for this command is prohibited", res);
    }

    @Test
    public void processCommand() {
        // empty stack
        IRestorableStack stack = service.getStack();
        boolean res = service.processCommand(buildCommand(UNDO));
        assertFalse(res);
        res = service.processCommand(buildCommand(CLEAR));
        assertTrue(res);
        res = service.processCommand(buildCommand(PRINT));
        assertTrue(res);
        res = service.processCommand(buildCommand(PUSH, 2));
        assertTrue(res);
        res = service.processCommand(buildCommand(PUSH, 2));
        assertTrue(res);
        res = service.processCommand(buildCommand(ADD));
        assertTrue(res);
        assertEquals(4, stack.top());
        assertEquals(1, stack.size());
        res = service.processCommand(buildCommand(NEG));
        assertTrue(res);
        assertEquals(-4, stack.top());
        res = service.processCommand(buildCommand(INV));
        assertTrue(res);
        assertEquals(-0.25, stack.top());
        assertEquals(1, stack.size());
        service.processCommand(buildCommand(PUSH, 0));
        assertEquals(2, stack.size());
        res = service.processCommand(buildCommand(INV));
        assertFalse(res);
        assertEquals(2, stack.size());
        res = service.processCommand(buildCommand(MUL));
        assertTrue(res);
        assertEquals(-0.0, stack.top());
        assertEquals(1, stack.size());
        res = service.processCommand(buildCommand(POP));
        assertTrue(res);
        assertEquals(0, stack.size());
        res = service.processCommand(buildCommand(PUSH, 2));
        assertTrue(res);
        assertEquals(1, stack.size());
        res = service.processCommand(buildCommand(CLEAR));
        assertTrue(res);
        assertEquals(0, stack.size());
    }

    @Test
    public void processUndoCommand() {
        // empty stack
        IRestorableStack stack = service.getStack();
        boolean res = service.processCommand(buildCommand(UNDO));
        assertFalse(res);
        assertEquals(0, stack.size());
        res = service.processCommand(buildCommand(PUSH, 3));
        assertTrue(res);
        assertEquals(1, stack.size());
        res = service.processCommand(buildCommand(UNDO));
        assertTrue(res);
        assertEquals(0, stack.size());
        res = service.processCommand(buildCommand(UNDO));
        assertFalse(res);

        res = service.processCommand(buildCommand(PUSH, 3));
        assertTrue(res);
        res = service.processCommand(buildCommand(PUSH, 2));
        assertTrue(res);
        assertEquals(2, stack.size());
        assertEquals(2, stack.top());
        res = service.processCommand(buildCommand(UNDO));
        assertTrue(res);
        assertEquals(1, stack.size());
        assertEquals(3, stack.top());

        res = service.processCommand(buildCommand(PUSH, 2));
        assertTrue(res);
        res = service.processCommand(buildCommand(ADD));
        assertTrue(res);
        assertEquals(5, stack.top());
        assertEquals(1, stack.size());

        res = service.processCommand(buildCommand(UNDO));
        assertTrue(res);
        assertEquals(2, stack.size());
        assertEquals(2, stack.top());

        res = service.processCommand(buildCommand(CLEAR));
        assertTrue(res);
        assertEquals(0, stack.size());
        res = service.processCommand(buildCommand(UNDO));
        assertTrue(res);
        assertEquals(2, stack.size());
        assertEquals(2, stack.top());
    }

}
