package com.oocode;

import org.junit.*;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class BetPlacerTest {

    @Test
    public void usesCheaperProviderIfOddsTheSame()  {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.60");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, times(1)).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);

    }

    @Test
    public void usesCheaperProviderIfOddsBetter()  {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.59");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, times(1)).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);
    }

    @Test
    public void usesExpensiveProviderIfOddsBetter()  {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.61");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
        verify(expensiveProvider, times(1)).accept(expensiveProviderQuote);
    }

    @Test
    public void placesExpensiveBetIfTargetOddsNotMetOnCheapBet()  {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = null;
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.60");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
        verify(expensiveProvider, times(1)).accept(expensiveProviderQuote);
    }

    @Test
    public void placesNoBetIfTargetOddsNotMetOnEither()  {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = null;
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.53");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);
    }

    @Test
    public void NeverPlaceCheaperBetBasedOnOfferFoundToExpire(){

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.50");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()-1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
    }

    @Test
    public void PlacesExpensiveBetIfMeetingTargetOddsWhenCheaperOfferFoundToExpire() {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.60");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()-1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
        verify(expensiveProvider, times(1)).accept(expensiveProviderQuote);

    }

    @Test
    public void PlacesNoBetIfCheaperOfferIsFoundToExpireAndTargetOddsNotMetByExpensiveOffer() {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);

        BigDecimal offeredOdds = new BigDecimal("0.53");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()-1000L);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, never()).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);

    }

    @Test
    public void PlacesExpensiveBetIfMeetingTargetOddsWhenCheaperOfferTimedOut() {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);
        CheaperProvider exceptionWrapper = new CheaperProvider();

        BigDecimal offeredOdds = new BigDecimal("0.60");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);
        doThrow(exceptionWrapper.new OfferTimeOutException()).when(cheaperProvider).accept(cheaperProviderQuote);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, times(1)).accept(cheaperProviderQuote);
        verify(expensiveProvider, times(1)).accept(expensiveProviderQuote);
        assertTrue(betPlacer.acceptTimedOut);

    }

    @Test
    public void PlacesNoBetWhenCheaperOfferTimedOutAndTargetOddsNotMetByExpensiveOffer() {

        int slugId = 2;
        String raceName = "The Saturday race";
        String cheaperProviderQuote = "ffaaa0c7-18e6-4bae-b4c1-7eabf3222f02";
        String expensiveProviderQuote = "7c7b0857-88e5-4fc1-aeea-bef815995ef3";

        DelegateProvider cheaperProvider = mock(CheaperProvider.class);
        DelegateProvider expensiveProvider = mock(ExpensiveProvider.class);
        BetPlacer betPlacer = new BetPlacer(cheaperProvider, expensiveProvider);
        CheaperProvider exceptionWrapper = new CheaperProvider();

        BigDecimal offeredOdds = new BigDecimal("0.50");
        BigDecimal targetOdds = new BigDecimal("0.60");

        when(cheaperProvider.quote(slugId, raceName, targetOdds)).thenReturn(cheaperProviderQuote);
        when(expensiveProvider.quote(slugId, raceName, targetOdds)).thenReturn(expensiveProviderQuote);
        when(expensiveProvider.getOdds()).thenReturn(offeredOdds);
        when(cheaperProvider.getQuotationTime()).thenReturn(System.currentTimeMillis()+1000L);
        doThrow(exceptionWrapper.new OfferTimeOutException()).when(cheaperProvider).accept(cheaperProviderQuote);

        betPlacer.placeBet(slugId, raceName, targetOdds);
        verify(cheaperProvider, times(1)).accept(cheaperProviderQuote);
        verify(expensiveProvider, never()).accept(expensiveProviderQuote);
        assertTrue(betPlacer.acceptTimedOut);

    }
}
