package nl.smith.primenumber;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.smith.primenumbers.main.Main;

public class PrimeNumberFactory implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrimeNumberFactory.class);

    private final static PrimenumberWithSquareValue FIRST_EVEN_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(2));

    private final static PrimenumberWithSquareValue FIRST_ODD_PRIMENUMBER = new PrimenumberWithSquareValue(new BigDecimal(3));

    public enum FACTORY_TYPE {

        /** Test every number to be a prime except the first even prime number */
        ALL_NUMBER_TESTER(ONE, FIRST_EVEN_PRIMENUMBER),

        /** Test only odd numbers to be a prime except the first even prime number and the first odd prime number */
        ODD_NUMBER_TESTER(ONE.add(ONE), FIRST_EVEN_PRIMENUMBER, FIRST_ODD_PRIMENUMBER);

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

    private final FACTORY_TYPE factoryType;

    private final List<PrimenumberWithSquareValue> primenumbers = new ArrayList<>();

    public PrimeNumberFactory(Main main, FACTORY_TYPE factoryType) {
        this.factoryType = factoryType;
        this.primenumbers.add(getFirstPrimeNumberDivider());
    }

    public PrimeNumberFactory(FACTORY_TYPE factoryType, List<PrimenumberWithSquareValue> primenumbers) {
        this.factoryType = factoryType;
        addPrimenumbers(primenumbers);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public BigDecimal getTestOffset() {
        return factoryType.testOffset;
    }

    public List<PrimenumberWithSquareValue> getUntestedPrimeNumbers() {
        return factoryType.untestedPrimeNumbers;
    }

    public PrimenumberWithSquareValue getFirstPrimeNumberDivider() {
        return factoryType.getFirstPrimeNumberDivider();
    }

    public void addPrimenumbers(List<PrimenumberWithSquareValue> primenumbers) {
        this.primenumbers.addAll(primenumbers);
    }

    public Optional<PrimenumberWithSquareValue> getPrimenumber(BigDecimal number) {
        LOGGER.info("\tTesting number: {}", number);
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
