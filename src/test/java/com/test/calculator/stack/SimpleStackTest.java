package com.test.calculator.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Double.NaN;
import static com.test.calculator.stack.SimpleStack.DEFAULT_CAPACITY;

public class SimpleStackTest {

    private SimpleStack stack;

    @BeforeEach
    public void before() {
        stack = new SimpleStack();
    }

    @Test
    public void constructor_test() {
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        IStackIterator iter = stack.getIterator();
        assertFalse(iter.hasNext());
    }

    @Test
    public void two_push_test() {
        stack.push(1);
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
        assertFalse(stack.isFull());
        IStackIterator iter = stack.getIterator();
        assertTrue(iter.hasNext());
        assertEquals(1.0, iter.next());
        assertEquals(NaN, iter.next());

        stack.push(2);
        assertEquals(2, stack.size());
        iter = stack.getIterator();
        assertTrue(iter.hasNext());
        assertEquals(2.0, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(1.0, iter.next());
        assertEquals(NaN, iter.next());
    }

    @Test
    public void doubleArrayCapacity_test() {
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            stack.push(DEFAULT_CAPACITY - i);
        }
        assertEquals(10, stack.size());
        assertFalse(stack.isEmpty());
        assertTrue(stack.isFull());
        IStackIterator iter = stack.getIterator();
        int i = 1;
        while (iter.hasNext()) {
            assertEquals(i++, iter.next());
        }

        stack.push(11);
        assertEquals(11, stack.size());
        assertFalse(stack.isFull());
        iter = stack.getIterator();
        i = 0;
        while (iter.hasNext()) {
            if (i == 0)
                assertEquals(11, iter.next());
            else
                assertEquals(i, iter.next());
            i++;
        }
    }

}
