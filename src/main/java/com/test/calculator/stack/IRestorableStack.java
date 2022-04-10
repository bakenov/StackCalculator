package com.test.calculator.stack;

public interface IRestorableStack extends ISimpleStack {

    void setCheckpoint();

    void restore();

}
