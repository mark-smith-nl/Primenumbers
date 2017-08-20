package nl.smith.primenumber;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractNumberTester {

    protected final static PrimenumberWithSquareValue FIRST_EVEN_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(2));

    protected final static PrimenumberWithSquareValue FIRST_ODD_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(3));

    private final List<PrimenumberWithSquareValue> primenumbers = new ArrayList<>();

    public abstract BigDecimal getTestOffset();

    public abstract List<PrimenumberWithSquareValue> getUntestedPrimeNumbers();

    public abstract PrimenumberWithSquareValue getFirstPrimeNumberDivider();

    public AbstractNumberTester() {
        this.primenumbers.add(getFirstPrimeNumberDivider());
    }

    public void addPrimenumbers(List<PrimenumberWithSquareValue> primenumbers) {
        this.primenumbers.addAll(primenumbers);
    }

    public Optional<PrimenumberWithSquareValue> getPrimenumber(BigDecimal number) {
        for (PrimenumberWithSquareValue primenumber : primenumbers) {

            int compareTo = primenumber.squareValue.compareTo(number);
            switch (compareTo) {
            case -1:
                BigDecimal[] divideAndRemainder = number.divideAndRemainder(primenumber.primenumber);
                if (divideAndRemainder[1].equals(ZERO)) {
                    return Optional.empty();
                }
                break;
            case 0:
                return Optional.empty();
            }
        }

        return Optional.of(new PrimenumberWithSquareValue(number));
    }

    public static class PrimenumberWithSquareValue {

        private final BigDecimal primenumber;

        private final BigDecimal squareValue;

        protected PrimenumberWithSquareValue(BigDecimal primenumber) {
            this.primenumber = primenumber;
            this.squareValue = primenumber.multiply(primenumber);
        }

        public BigDecimal getPrimenumber() {
            return primenumber;
        }

        public BigDecimal getSquareValue() {
            return squareValue;
        }

        @Override
        public String toString() {
            return "PrimenumberWithSquareValue [primenumber=" + primenumber + ", squareValue=" + squareValue + "]";
        }

    }
}
