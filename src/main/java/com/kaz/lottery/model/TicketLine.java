package com.kaz.lottery.model;

/**
 * This is a model object for lottery ticket line.
 */
public class TicketLine {

    /**
     * The line values.
     */
    private int[] values;
    /**
     * The line result.
     */
    private int result;

    public TicketLine() {}

    public TicketLine(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
