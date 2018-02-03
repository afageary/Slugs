package com.oocode;

import java.math.BigDecimal;

public interface DelegateProvider {

    String  quote(int slugId, String raceName, BigDecimal targetOdds);

    void accept(String quote);

    long getQuotationTime();

    BigDecimal getOdds();

}
