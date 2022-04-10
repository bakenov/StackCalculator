package com.test.calculator.stack;

public interface ISimpleStack {

    void push(double v);

    double pop();

    double top();

    int size();

    boolean isEmpty();

    boolean isFull();

    void clear();

    IStackIterator getIterator();

}
