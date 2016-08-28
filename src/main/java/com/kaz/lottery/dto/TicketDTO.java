package com.kaz.lottery.dto;

import java.util.List;

/**
 * This is a DTO object for Ticket.
 */
public class TicketDTO {

    private String id;
    private List<TicketLineDTO> lines;
    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TicketLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<TicketLineDTO> lines) {
        this.lines = lines;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
