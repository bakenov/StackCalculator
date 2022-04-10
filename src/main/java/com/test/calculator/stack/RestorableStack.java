package com.test.calculator.stack;

public class RestorableStack extends SimpleStack implements IRestorableStack {

    private double[] checkpointArray;

    public RestorableStack() {
        super();
    }

    public RestorableStack(int capacity) {
        super(capacity);
    }

    @Override
    public void setCheckpoint() {
        if (isEmpty()) {
            checkpointArray = null;
            return;
        }
        int index = array.length - size();
        checkpointArray = new double[size()];
        System.arraycopy(array, index, checkpointArray, 0, size());
    }

    @Override
    public void restore() {
        if (checkpointArray == null) {
            size = 0;
        } else {
            size = checkpointArray.length;
            int index = array.length - size;
            System.arraycopy(checkpointArray, 0, array, index, checkpointArray.length);
        }
    }

    public int checkpointSize() {
        if (checkpointArray == null)
            return 0;
        return checkpointArray.length;
    }

}
