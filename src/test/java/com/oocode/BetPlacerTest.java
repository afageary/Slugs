package com.oocode;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


public class BetPlacerTest {

    DelegateProvider cheaperProvider;
    DelegateProvider expensiveProvider;
    BetPlacer betPlacer;


    int slugId;
    String raceName ;
    String cheaperProviderQuote;
    String expensiveProviderQuote;

    @Before
    public void setup() {
        slugId = 2;
        raceName = "The Thursday race";
        cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        cheaperProvider = mock(DelegateProvider.class);
        expensiveProvider = mock(DelegateProvider.class);
        betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);
    }


    @Test
    public void usesCheaperProviderIfOddsTheSame() throws Exception {

        BigDecimal offeredOdds = new BigDecimal("0.60");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, offeredOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, offeredOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);

    }

    @Test
    public void usesCheaperProviderIfOddsBetter() throws Exception {
    }

    @Test
    public void usesExpensiveProviderIfOddsBetter() throws Exception {
    }

    @Test
    public void placesExpensiveBetIfTargetOddsNotMetOnCheapBet() throws Exception {
    }

    @Test
    public void placesNoBetIfTargetOddsNotMetOnEither() throws Exception {
    }
}
