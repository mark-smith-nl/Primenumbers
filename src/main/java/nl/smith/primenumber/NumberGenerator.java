package nl.smith.primenumber;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberGenerator {

    private final static Logger LOGGER = LoggerFactory.getLogger(NumberGenerator.class);

    private BigDecimal current;

    private final BigDecimal offset;

    private BigDecimal ceiling;

    /**
     * 
     * @param current
     * @param offset
     * @param ceiling
     */
    public NumberGenerator(BigDecimal current, BigDecimal offset, BigDecimal ceiling) {
        if (current == null || offset == null || ceiling == null) {
            throw new IllegalArgumentException("Null values for current, offset and ceiling are not allowed.");
        }
        if (current.compareTo(ceiling) == 1) {
            throw new IllegalArgumentException("Values for current larger then ceiling are not allowed.");
        }
        if (offset.compareTo(BigDecimal.ONE) == -1) {
            throw new IllegalArgumentException("VValues for offset smaller then one are not allowed.");
        }
        this.current = current;
        this.offset = offset;
        this.ceiling = ceiling;
    }

    public synchronized BigDecimal getNumber() {
        if (current.compareTo(ceiling) == 1) {
            return null;
        }

        BigDecimal number = current;
        current = current.add(offset);

        return number;
    }

    public static void main(String[] args) {
        NumberGenerator numberGenerator = new NumberGenerator(new BigDecimal(10), new BigDecimal(3), new BigDecimal(20));
        BigDecimal current;
        do {
            current = numberGenerator.getNumber();
            System.out.println(current);
        } while (current != null);

        System.out.println("Einde main");

    }

}
