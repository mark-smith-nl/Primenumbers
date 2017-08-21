package nl.smith.primenumbers.main;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.smith.primenumber.PrimeNumberFactory;
import nl.smith.primenumber.PrimeNumberFactory.PrimenumberWithSquareValue;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        List<PrimenumberWithSquareValue> primenumbers = new ArrayList<>();

        PrimeNumberFactory primeNumberFactory = new PrimeNumberFactory(PrimeNumberFactory.FACTORY_TYPE.ODD_NUMBER_TESTER);

        primenumbers.addAll(primeNumberFactory.getUntestedPrimeNumbers());
        BigDecimal offset = primeNumberFactory.getTestOffset();

        BigDecimal number = primeNumberFactory.getFirstPrimeNumberDivider().getPrimenumber().add(offset);
        BigDecimal ceiling = primeNumberFactory.getFirstPrimeNumberDivider().getSquareValue();

        for (int i = 1; i <= 3; i++) {
            List<PrimenumberWithSquareValue> newPrimenumbers = new ArrayList<>();
            LOGGER.info("Calculate cycle {}. Start: {}. Ceiling: {}.", i, number, ceiling);
            while (number.compareTo(ceiling) < 0) {
                Optional<PrimenumberWithSquareValue> primenumber = primeNumberFactory.getPrimenumber(number);
                if (primenumber.isPresent()) {
                    // LOGGER.info("{}", primenumber.get().getPrimenumber());
                    newPrimenumbers.add(primenumber.get());
                }

                number = number.add(ONE).add(ONE);
            }
            primeNumberFactory.addPrimenumbers(newPrimenumbers);
            primenumbers.addAll(newPrimenumbers);
            number = ceiling.add(offset);
            ceiling = ceiling.multiply(ceiling);
        }

        primenumbers.forEach(primenumber -> LOGGER.info("{}", primenumber.getPrimenumber()));
    }
}
