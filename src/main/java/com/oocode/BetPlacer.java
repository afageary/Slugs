package com.oocode;

import java.math.BigDecimal;

public class BetPlacer {

    private DelegateProvider cheapProvider;
    private DelegateProvider expensiveProvider;
    boolean acceptTimedOut;

    BetPlacer(DelegateProvider cheapProvider, DelegateProvider expensiveProvider) {
        this.cheapProvider = cheapProvider;
        this.expensiveProvider = expensiveProvider;
        this.acceptTimedOut = false;
    }
    public BetPlacer() {
        cheapProvider = new CheaperProvider();
        expensiveProvider = new ExpensiveProvider();
    }
    public static void main(String[] args) throws Exception {
        /* Results usually look like a bit like one of the following:
           Time out on SlugSwaps
           accepted quote = 14281567-1fde-4996-a61f-0ba60b2c95c0 with offered odds 0.87
           accepted quote = dada5f35-c244-4da6-a370-648ea35f7a03 with required odds 0.50
        */

        // Note that the names of today’s races change every day!
        new BetPlacer().placeBet(3, "The Sunday race", new BigDecimal("0.50"));
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
