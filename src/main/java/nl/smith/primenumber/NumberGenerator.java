package nl.smith.primenumber;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberGenerator {

    private final static Logger LOGGER = LoggerFactory.getLogger(NumberGenerator.class);

    private BigDecimal current;

    private final BigDecimal offset;

    private BigDecimal ceiling;

    public NumberGenerator(BigDecimal current, BigDecimal offset, BigDecimal ceiling) {
        this.current = current;
        this.offset = offset;
        this.ceiling = ceiling;
    }

    public synchronized BigDecimal getNumber() {
        if (current.compareTo(ceiling) > 1) {
            return null;
        }

        BigDecimal number = current;
        current = current.add(offset);

        return number;
    }

}
