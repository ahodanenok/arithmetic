package ahodanenok.arithmetic;

import ahodanenok.arithmetic.operator.*;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class OperatorTest {

    @Test
    public void testSum() {
        SumOperator op = new SumOperator();

        assertEquals(new BigDecimal(3), op.evaluate(
                new BigDecimal[] { new BigDecimal(1), new BigDecimal(2) }));
        assertEquals(new BigDecimal(6), op.evaluate(
                new BigDecimal[] { new BigDecimal(1), new BigDecimal(2), new BigDecimal(3) }));
        assertEquals(new BigDecimal(28), op.evaluate(
                new BigDecimal[] { new BigDecimal(3), new BigDecimal(2), new BigDecimal(1),
                        new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7) }));
    }

    @Test
    public void testSubtract() {
        SubtractOperator op = new SubtractOperator();

        assertEquals(new BigDecimal(-1), op.evaluate(
                new BigDecimal[] { new BigDecimal(1), new BigDecimal(2) }));
        assertEquals(new BigDecimal(-4), op.evaluate(
                new BigDecimal[] { new BigDecimal(1), new BigDecimal(2), new BigDecimal(3) }));
        assertEquals(new BigDecimal(48), op.evaluate(
                new BigDecimal[] { new BigDecimal(100), new BigDecimal(20), new BigDecimal(10),
                        new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7) }));
    }

    @Test
    public void testNegate() {
        NegateOperator op = new NegateOperator();

        assertEquals(new BigDecimal(-10), op.evaluate(new BigDecimal[] { new BigDecimal(10) }));
        assertEquals(new BigDecimal(23), op.evaluate(new BigDecimal[] { new BigDecimal(-23) }));
    }

    @Test
    public void testMultiply() {
        MultiplyOperator op = new MultiplyOperator();

        assertEquals(new BigDecimal(6), op.evaluate(
                new BigDecimal[] { new BigDecimal(2), new BigDecimal(3) }));
        assertEquals(new BigDecimal(64), op.evaluate(
                new BigDecimal[] { new BigDecimal(2), new BigDecimal(4), new BigDecimal(8) }));
        assertEquals(new BigDecimal(15120), op.evaluate(
                new BigDecimal[] { new BigDecimal(3), new BigDecimal(2), new BigDecimal(3),
                        new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(7) }));
    }

    @Test
    public void testDivide() {
        DivideOperator op = new DivideOperator();

        assertEquals(new BigDecimal(5), op.evaluate(
                new BigDecimal[] { new BigDecimal(10), new BigDecimal(2) }));
        assertEquals(new BigDecimal("0.7"), op.evaluate(
                new BigDecimal[] { new BigDecimal(7), new BigDecimal(5), new BigDecimal(2) }));
        assertEquals(new BigDecimal("0.00625"), op.evaluate(
                new BigDecimal[] { new BigDecimal(3), new BigDecimal(2), new BigDecimal(1),
                        new BigDecimal(4), new BigDecimal(5), new BigDecimal(6), new BigDecimal(2) }));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideZero_1() {
        DivideOperator op = new DivideOperator();
        op.evaluate(new BigDecimal[] { new BigDecimal(10), new BigDecimal(0) });
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideZero_2() {
        DivideOperator op = new DivideOperator();
        op.evaluate(new BigDecimal[] { new BigDecimal(10), new BigDecimal(5), new BigDecimal(0) });
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideZero_3() {
        DivideOperator op = new DivideOperator();
        op.evaluate(new BigDecimal[] { new BigDecimal(10), new BigDecimal(5), new BigDecimal(0), new BigDecimal(3) });
    }
}
