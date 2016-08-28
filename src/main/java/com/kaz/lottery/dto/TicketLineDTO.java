package com.kaz.lottery.dto;

/**
 * This is a DTO object for TicketLine.
 */
public class TicketLineDTO {

    private int[] values;
    private int result;

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
