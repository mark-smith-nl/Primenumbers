package nl.smith.primenumber;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.smith.primenumber.PrimenumberGenerator.PrimenumberWithSquareValue;

public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        List<PrimenumberWithSquareValue> initialPrimenumbers = PrimenumberGenerator.getInitialPrimenumber();

        PrimenumberGenerator primenumberGenerator = new PrimenumberGenerator(initialPrimenumbers);

        PrimenumberWithSquareValue initialPrimenumber = initialPrimenumbers.get(0);

        BigDecimal number = initialPrimenumber.getPrimenumber().add(ONE);
        BigDecimal ceiling = initialPrimenumber.getSquareValue();

        for (int i = 1; i <= 5; i++) {
            List<PrimenumberWithSquareValue> newPrimenumbers = new ArrayList<>();
            LOGGER.info("Calculate cycle {}. Start: {}. Ceiling: {}.", i, number, ceiling);
            while (number.compareTo(ceiling) < 0) {
                Optional<PrimenumberWithSquareValue> primenumber = primenumberGenerator.getPrimenumber(number);
                if (primenumber.isPresent()) {
                    newPrimenumbers.add(primenumber.get());
                }

                number = number.add(ONE);
            }
            primenumberGenerator.addPrimenumbers(newPrimenumbers);
            number = ceiling.add(ONE);
            ceiling = ceiling.multiply(ceiling);
        }

        primenumberGenerator.printPrimenumbers();
    }
}
