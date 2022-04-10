package com.test.calculator.stack;

import static java.lang.Double.NaN;

public class SimpleStack implements ISimpleStack {

    public static final int DEFAULT_CAPACITY = 10;

    int size = 0;
    private int capacity;
    double[] array;
    private StackIterator iterator;

    public SimpleStack() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleStack(int capacity) {
        this.capacity = capacity;
        array = new double[capacity];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return capacity == size;
    }

    @Override
    public void push(double value) {
        if(isFull()) {
            doubleArrayCapacity();
        }
        array[capacity - ++size] = value;
    }

    @Override
    public double pop() {
        if(isEmpty())
            return NaN;
        return array[capacity - size--];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public double top() {
        if (size == 0)
            return NaN;
        return array[capacity - size];
    }

    @Override
    public void clear() {
        size = 0;
    }

    private void doubleArrayCapacity() {
        int initialSize = capacity;
        capacity *= 2;
        double[] copy = new double[capacity];
        System.arraycopy(array, 0, copy, initialSize, array.length);
        array = copy;
    }

    @Override
    public IStackIterator getIterator() {
        if (iterator == null)
            iterator = new StackIterator();

        iterator.reset();
        return iterator;
    }

    private class StackIterator implements IStackIterator {

        int index;

        private void reset() {
            index = capacity - size;
        }

        @Override
        public boolean hasNext() {
            return index < capacity;
        }

        @Override
        public double next() {
            if(this.hasNext()){
                return array[index++];
            }
            return NaN;
        }
    }
}
