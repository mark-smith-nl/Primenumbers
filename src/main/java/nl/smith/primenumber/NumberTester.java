package nl.smith.primenumber;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberTester extends AbstractNumberTester {

    private final static BigDecimal TEST_OFFSET = ONE;

    public NumberTester() {

    }

    public NumberTester(List<PrimenumberWithSquareValue> primenumbers) {
        addPrimenumbers(primenumbers);
    }

    public BigDecimal getTestOffset() {
        return TEST_OFFSET;
    }

    @Override
    public PrimenumberWithSquareValue getFirstPrimeNumberDivider() {
        return FIRST_EVEN_PRIMENUMBER;
    }

    public List<PrimenumberWithSquareValue> getUntestedPrimeNumbers() {
        return new ArrayList<PrimenumberWithSquareValue>(Arrays.asList(FIRST_EVEN_PRIMENUMBER));
    }

}
