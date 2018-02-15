package com.oocode;

import com.teamoptimization.Race;
import com.teamoptimization.SlugSwaps;
import com.teamoptimization.SlugSwapsApi;

import java.math.BigDecimal;

class CheaperProvider implements BetProvider {

    private long quotationTime;
    private BigDecimal odds;

    @Override
    public String  quote(int slugId, String raceName, BigDecimal targetOdds) {
        this.odds = targetOdds;
        Race race = SlugSwapsApi.forRace(raceName);
        if (race == null) {
            return null;
        } else {
            this.quotationTime = System.currentTimeMillis();
            return race.quote(slugId, targetOdds);
        }
    }

   @Override
    public void accept(String quote)  {
        try {
            SlugSwapsApi.accept(quote);
        } catch (SlugSwaps.Timeout timeout) {
            throw new OfferTimeOutException("Cheaper Provider accept method timed-out", timeout);
        }
    }

    @Override
    public long getQuotationTime() {
        return this.quotationTime;
    }

    @Override
    public BigDecimal getOdds() {
        return this.odds;
    }

    class OfferTimeOutException extends RuntimeException {
        Exception error;
        OfferTimeOutException(String msg, Exception e) {
            super(msg);
            this.error = e;
        }
        OfferTimeOutException() {}
    }
}
