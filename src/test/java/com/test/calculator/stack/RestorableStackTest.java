package com.test.calculator.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

public class RestorableStackTest {

    private RestorableStack stack;

    @BeforeEach
    public void before() {
        stack = new RestorableStack();
    }

    @Test
    public void setCheckpoint_0_test() {
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        IStackIterator iter = stack.getIterator();
        assertFalse(iter.hasNext());
        assertEquals(NaN, iter.next());

        stack.setCheckpoint();
        assertEquals(0, stack.checkpointSize());

        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(3, stack.size());

        stack.restore();
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        iter = stack.getIterator();
        assertFalse(iter.hasNext());
        assertEquals(NaN, iter.next());
    }

    @Test
    public void restoration_test() {
        stack.push(13);
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
        assertFalse(stack.isFull());
        IStackIterator iter = stack.getIterator();
        assertTrue(iter.hasNext());
        assertEquals(13.0, iter.next());
        assertEquals(NaN, iter.next());

        stack.setCheckpoint();
        assertEquals(1, stack.checkpointSize());

        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(4, stack.size());

        stack.restore();
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
        assertFalse(stack.isFull());
        iter = stack.getIterator();
        assertTrue(iter.hasNext());
        assertEquals(13.0, iter.next());
        assertEquals(NaN, iter.next());
    }


}
