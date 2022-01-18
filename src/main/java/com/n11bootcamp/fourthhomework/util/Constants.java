package com.n11bootcamp.fourthhomework.util;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Constants {

    public static final BigDecimal MIN_RATE = BigDecimal.valueOf(1.5);

    public static final BigDecimal MAX_RATE = BigDecimal.valueOf(2.0);

    public static final LocalDate LIMIT_DATE  = LocalDate.of(2018,01,01);

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(new BigDecimal(100));
    }
}
