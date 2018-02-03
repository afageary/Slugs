package com.oocode;

import com.teamoptimization.Race;
import com.teamoptimization.SlugSwaps;
import com.teamoptimization.SlugSwapsApi;

import java.math.BigDecimal;

class CheaperProvider implements DelegateProvider {

    long quotationTime;
    BigDecimal odds;

    @Override
    public String  quote(int slugId, String raceName, BigDecimal targetOdds) {
        String result;
        this.odds = targetOdds;
        Race race = SlugSwapsApi.forRace(raceName);
        if (race == null) {
            result = null;
        } else {
            result = race.quote(slugId, targetOdds);
            this.quotationTime = System.currentTimeMillis();
        }
        return result;
    }

   @Override
    public void accept(String quote) {
        try {
            SlugSwapsApi.accept(quote);
        } catch (SlugSwaps.Timeout timeout) {
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
}
