package com.oocode;

import com.teamoptimization.*;

import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;


public class TodoTests {

    SlugRacingOddsApi expensiveProvider = mock(SlugRacingOddsApi.class);
    SlugSwapsApi cheaperProvider = mock(SlugSwapsApi.class);


    BetPlacer betPlacer = new BetPlacer();


    @Test
    public void usesCheaperProviderIfOddsTheSame() throws Exception {

        when(SlugRacingOddsApi.on(2, "The Thursday race")).thenReturn( new Quote(new BigDecimal("0.50"),"7c7b0857-88e5-4fc1-aeea-bef815995ef3"));
        when(SlugSwapsApi.forRace("The Thursday race").quote(2,new BigDecimal("0.5") )).thenReturn("ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02");
        betPlacer.placeBet(2, "The Thursday race", new BigDecimal("0.50"));
        verify(cheaperProvider, times(1)).accept("ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02");
        verify(expensiveProvider, never()).agree("7c7b0857-88e5-4fc1-aeea-bef815995ef3");

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
