package nl.smith.primenumber;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimenumberGenerator {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrimenumberGenerator.class);

    private final List<PrimenumberWithSquareValue> primenumbers;

    public PrimenumberGenerator(List<PrimenumberWithSquareValue> primenumbers) {
        this.primenumbers = primenumbers;
    }

    public static List<PrimenumberWithSquareValue> getInitialPrimenumber() {
        return new ArrayList<PrimenumberWithSquareValue>(Arrays.asList(new PrimenumberWithSquareValue(new BigDecimal(2))));
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

    public List<PrimenumberWithSquareValue> getPrimenumbers() {
        return primenumbers;
    }

    public void printPrimenumbers() {
        primenumbers.forEach(primenumber -> LOGGER.info("{}", primenumber.primenumber));
    }

    public static class PrimenumberWithSquareValue {

        private final BigDecimal primenumber;

        private final BigDecimal squareValue;

        private PrimenumberWithSquareValue(BigDecimal primenumber) {
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
