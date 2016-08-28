package com.kaz.lottery.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model object for a lottery Ticket
 */
public class Ticket implements Identified {

    /**
     * The ticket id.
     */
    private String id;
    /**
     * This list of ticket lines represented by TicketLine object.
     */
    private List<TicketLine> lines;
    /**
     * This indicates whether the ticket has been checked or not.
     */
    private boolean checked;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public List<TicketLine> getLines() {
        return lines;
    }

    public void setLines(List<TicketLine> lines) {
        this.lines = lines;
    }

    public void addLines(List<TicketLine> lines) {
        if (this.lines == null) {
            this.lines = new ArrayList<>();
        }
        this.lines.addAll(lines);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
