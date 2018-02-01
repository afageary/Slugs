package com.oocode;

import com.teamoptimization.SlugRacingOddsApi;
import com.teamoptimization.SlugSwapsApi;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


public class BetPlacerTest {

    SlugRacingOddsApi expensiveProvider;
    SlugSwapsApi cheaperProvider;
    BetPlacer betPlacer;


    int slugId;
    String raceName ;
    String cheaperProviderQuote;
    String expensiveProviderQuote;
    BigDecimal targetOdds;
    BigDecimal offeredOdds;

    @Before
    public void setup() {
        slugId = 2;
        raceName = "The Thursday race";
        cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";
        targetOdds = new BigDecimal("0.60");
        offeredOdds = new BigDecimal("0.60");

        expensiveProvider = mock(SlugRacingOddsApi.class);
        cheaperProvider = mock(SlugSwapsApi.class);
        betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);
    }


    @Test
    public void usesCheaperProviderIfOddsTheSame() throws Exception {

       // when(expensiveProvider.on(slugId, raceName)).thenReturn( new Quote(offeredOdds,expensiveProviderQuote));
       // when(cheaperProvider.forRace(raceName).quote(slugId,targetOdds)).thenReturn(cheaperProviderQuote);
        betPlacer.placeBet(slugId, raceName, targetOdds);
       // verify(cheaperProvider, times(1)).accept(eq(cheaperProviderQuote));
       // verify(expensiveProvider, never()).agree(eq(expensiveProviderQuote));

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
