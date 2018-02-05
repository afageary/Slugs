package com.oocode;

import java.math.BigDecimal;

public class BetPlacer {

    private BetProvider cheapProvider;
    private BetProvider expensiveProvider;
    boolean acceptTimedOut;

    BetPlacer(BetProvider cheapProvider, BetProvider expensiveProvider) {
        this.cheapProvider = cheapProvider;
        this.expensiveProvider = expensiveProvider;
        this.acceptTimedOut = false;
    }
    public BetPlacer() {
        cheapProvider = new CheaperProvider();
        expensiveProvider = new ExpensiveProvider();
    }

    public void placeBet(int slugId, String raceName, BigDecimal targetOdds)  {

        String a = cheapProvider.quote(slugId, raceName, targetOdds);
        String b = expensiveProvider.quote(slugId, raceName, targetOdds);
        if ( a != null && targetOdds.compareTo(expensiveProvider.getOdds()) >= 0) {
            if(cheapProvider.getQuotationTime() + 1000L > System.currentTimeMillis()) {
                try {
                    cheapProvider.accept(a);
                } catch (CheaperProvider.OfferTimeOutException e) {
                    if (expensiveProvider.getOdds().compareTo(targetOdds) == 0) {
                        expensiveProvider.accept(b);
                    }
                    e.getStackTrace();
                    acceptTimedOut = true;
                }
            } else if (expensiveProvider.getOdds().compareTo(targetOdds) == 0) {
                expensiveProvider.accept(b);
            }
        } else if (expensiveProvider.getOdds().compareTo(targetOdds) >= 0) {
            expensiveProvider.accept(b);
        }
    }
}
