package com.kaz.lottery.rules;

import com.kaz.lottery.model.TicketLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kaz on 28/08/2016.
 */
@RunWith(SpringRunner.class)
public class Sum2WinsTicketLineRulesTest {

    private Sum2WinsTicketLineRules sum2WinsTicketLineRules;

    @Before
    public void setUp() {
        sum2WinsTicketLineRules = new Sum2WinsTicketLineRules();
    }

    @Test
    public void testRandomTicketLine() {
        TicketLine ticketLine = sum2WinsTicketLineRules.randomTicketLine();
        Assert.assertNotNull(ticketLine);
        Assert.assertEquals(0, ticketLine.getResult());
        Assert.assertNotNull(ticketLine.getValues());
        Assert.assertEquals(Sum2WinsTicketLineRules.LINE_LENGTH, ticketLine.getValues().length);
        // Check that only the allowed values (Sum2WinsTicketLineRules.LINE_VALUES) are present on the
        // ticket line.
        Assert.assertFalse(Arrays.stream(ticketLine.getValues())
                .filter(value -> Arrays.binarySearch(Sum2WinsTicketLineRules.LINE_VALUES, value) < 0)
                .findAny().isPresent());
    }

    @Test
    public void testRandomTicketLines() {
        List<TicketLine> ticketLines = sum2WinsTicketLineRules.randomTicketLines(25);
        Assert.assertNotNull(ticketLines);
        Assert.assertEquals(25, ticketLines.size());
        for (TicketLine ticketLine : ticketLines) {
            Assert.assertNotNull(ticketLine);
            Assert.assertEquals(0, ticketLine.getResult());
            Assert.assertNotNull(ticketLine.getValues());
            Assert.assertEquals(Sum2WinsTicketLineRules.LINE_LENGTH, ticketLine.getValues().length);
            // Check that only the allowed values (Sum2WinsTicketLineRules.LINE_VALUES) are present on the
            // ticket line.
            Assert.assertFalse(Arrays.stream(ticketLine.getValues())
                    .filter(value -> Arrays.binarySearch(Sum2WinsTicketLineRules.LINE_VALUES, value) < 0)
                    .findAny().isPresent());
        }
    }

    @Test
    public void testCalculateTicketLineResult() {
        // Result 10
        int[] values = new int[Sum2WinsTicketLineRules.LINE_LENGTH];
        // Assuming the line length is at least 3;
        Assert.assertTrue(Sum2WinsTicketLineRules.LINE_LENGTH > 2);
        values[0] = 1;
        values[1] = 1;
        // Shift the numbers right and verify the line result is 10 for all combinations.
        for (int i=0; i<Sum2WinsTicketLineRules.LINE_LENGTH-1; i++) {
            for (int j=Sum2WinsTicketLineRules.LINE_LENGTH-1; j > 0; j--) {
                values[j] = values[j-1];
            }
            values[0] = 0;
            TicketLine ticketLine = new TicketLine(values);
            sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
            Assert.assertEquals(10, ticketLine.getResult());
        }

        // Result 10
        values[0] = 2;
        // Shift the numbers right and verify the line result is 10 for all combinations.
        for (int i=0; i<Sum2WinsTicketLineRules.LINE_LENGTH-1; i++) {
            for (int j=Sum2WinsTicketLineRules.LINE_LENGTH-1; j > 0; j--) {
                values[j] = values[j-1];
            }
            values[0] = 0;
            TicketLine ticketLine = new TicketLine(values);
            sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
            Assert.assertEquals(10, ticketLine.getResult());
        }

        // Result 5
        // Build lines with the same values and verify the line result is 5 for all combinations.
        for (int value : Sum2WinsTicketLineRules.LINE_VALUES) {
            for (int i = 0; i < Sum2WinsTicketLineRules.LINE_LENGTH; i++) {
                values[i] = value;
            }
            TicketLine ticketLine = new TicketLine(values);
            sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
            Assert.assertEquals(5, ticketLine.getResult());
        }

        // Result 1
        for (int i=0; i<Sum2WinsTicketLineRules.LINE_VALUES.length; i++) {

        }
        TicketLine ticketLine = new TicketLine(new int[]{0, 1, 2});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());

        ticketLine = new TicketLine(new int[]{0, 2, 1});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());

        ticketLine = new TicketLine(new int[]{2, 1, 1});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());

        ticketLine = new TicketLine(new int[]{1, 2, 2});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());

        ticketLine = new TicketLine(new int[]{2, 1, 0});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());

        // Result 0
        ticketLine = new TicketLine(new int[]{0, 1, 2});
        sum2WinsTicketLineRules.calculateTicketLineResult(ticketLine);
        Assert.assertEquals(1, ticketLine.getResult());
    }

    @Test
    public void testSortTicketLines() {
        List<TicketLine> lines = new ArrayList<>();
        TicketLine ticketLine = new TicketLine(new int[]{2, 0, 0});
        ticketLine.setResult(10);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{2, 1, 2});
        ticketLine.setResult(0);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{2, 2, 2});
        ticketLine.setResult(5);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{0, 1, 1});
        ticketLine.setResult(1);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 1, 2});
        ticketLine.setResult(0);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 1, 1});
        ticketLine.setResult(5);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 0, 2});
        ticketLine.setResult(1);
        lines.add(ticketLine);
        ticketLine = new TicketLine(new int[]{1, 1, 0});
        ticketLine.setResult(10);
        lines.add(ticketLine);

        sum2WinsTicketLineRules.sortTicketLines(lines);
        Assert.assertEquals(8, lines.size());

        int prev = Integer.MAX_VALUE;
        for (TicketLine t : lines) {
            Assert.assertTrue(prev >= t.getResult());
            prev = t.getResult();
        }
    }
}
