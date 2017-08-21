package nl.smith.primenumber;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrimeNumberFactory {

    protected final static PrimenumberWithSquareValue FIRST_EVEN_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(2));

    protected final static PrimenumberWithSquareValue FIRST_ODD_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(3));

    public enum FACTORY_TYPE {

        ALL_NUMBER_TESTER(ONE, FIRST_EVEN_PRIMENUMBER), ODD_NUMBER_TESTER(ONE.add(ONE), FIRST_EVEN_PRIMENUMBER, FIRST_ODD_PRIMENUMBER);

        private BigDecimal testOffset;

        private List<PrimenumberWithSquareValue> untestedPrimeNumbers = new ArrayList<>();

        private FACTORY_TYPE(BigDecimal testOffset, PrimenumberWithSquareValue... untestedPrimeNumbers) {
            this.testOffset = testOffset;
            for (PrimenumberWithSquareValue untestedPrimeNumber : untestedPrimeNumbers) {
                this.untestedPrimeNumbers.add(untestedPrimeNumber);
            }
        }

        public PrimenumberWithSquareValue getFirstPrimeNumberDivider() {
            return untestedPrimeNumbers.get(untestedPrimeNumbers.size() - 1);
        }
    }

    private final FACTORY_TYPE testerType;

    private final List<PrimenumberWithSquareValue> primenumbers = new ArrayList<>();

    public PrimeNumberFactory(FACTORY_TYPE testerTpe) {
        this.testerType = testerTpe;
        this.primenumbers.add(getFirstPrimeNumberDivider());
    }

    public PrimeNumberFactory(FACTORY_TYPE testerTpe, List<PrimenumberWithSquareValue> primenumbers) {
        this.testerType = testerTpe;
        addPrimenumbers(primenumbers);
    }

    public BigDecimal getTestOffset() {
        return testerType.testOffset;
    }

    public List<PrimenumberWithSquareValue> getUntestedPrimeNumbers() {
        return testerType.untestedPrimeNumbers;
    }

    public PrimenumberWithSquareValue getFirstPrimeNumberDivider() {
        return testerType.getFirstPrimeNumberDivider();
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
