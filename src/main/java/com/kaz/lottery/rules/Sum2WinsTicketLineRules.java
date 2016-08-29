package com.kaz.lottery.rules;

import com.kaz.lottery.model.TicketLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is an implementation of the TicketLineRules interface. It generates lines which are of length 3.
 * The only allowed values on the line are 0, 1, 2.
 */
@Service("sum2WinsTicketLineRules")
public class Sum2WinsTicketLineRules implements TicketLineRules {

    public static final int LINE_LENGTH = 3;
    public static final int[] LINE_VALUES = {0, 1, 2};

    /**
     * This instance variable is used for generating random TicketLine values.
     */
    private Random random = new Random();

    /**
     * This method generates a random values for TicketLine according to the following rules:
     * - the ticket line must contain 3 values from the following set: [0, 1, 2]
     * @return The randomly generated TicketLine.
     */
    @Override
    public TicketLine randomTicketLine() {
        int[] values = new int[LINE_LENGTH];
        for (int j=0; j<LINE_LENGTH; j++) {
            values[j] = LINE_VALUES[random.nextInt(LINE_VALUES.length)];
        }
        return new TicketLine(values);
    }

    /**
     * This method generates a number of TicketLines
     * @param noOfLines The number of lines to generate.
     * @return The list of generated lines.
     */
    @Override
    public List<TicketLine> randomTicketLines(int noOfLines) {
        List<TicketLine> lines = new ArrayList<>();
        for (int i=0; i<noOfLines; i++) {
            lines.add(randomTicketLine());
        }
        return lines;
    }

    /**
     * This method calculates the line result according the the following rules:
     * - if the sum of the values equals 2 then the result is 10,
     * - if all the values are the same the result is 5,
     * - if all the values are different the result is 1,
     * - else the result is 0.
     * @param ticketLine The TicketLine for calculating the result.
     */
    @Override
    public void calculateTicketLineResult(TicketLine ticketLine) {
        if (Arrays.stream(ticketLine.getValues()).sum() == 2) {
            ticketLine.setResult(10);
        } else if (Arrays.stream(ticketLine.getValues()).distinct().count() == 1) {
            ticketLine.setResult(5);
        } else if (Arrays.stream(ticketLine.getValues(), 1, ticketLine.getValues().length)
                .noneMatch(value ->  value == ticketLine.getValues()[0])) {
            ticketLine.setResult(1);
        } else {
            ticketLine.setResult(0);
        }
    }

    /**
     * This method sorts the TicketLines according to the following rules:
     * - highest to lowest result score.
     * @param lines The lines to sortTicketLines.
     */
    @Override
    public void sortTicketLines(List<TicketLine> lines) {
        lines.sort((l1, l2) -> l1.getResult() > l2.getResult() ? -1 : l1.getResult() < l2.getResult() ? 1 : 0);
    }
}
