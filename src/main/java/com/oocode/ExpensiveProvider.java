package com.oocode;

import com.teamoptimization.Quote;
import com.teamoptimization.SlugRacingOddsApi;

import java.math.BigDecimal;

public class ExpensiveProvider implements BetProvider {

    private long quotationTime;
    private BigDecimal odds;

    @Override
    public String  quote(int slugId, String raceName, BigDecimal targetOdds) {
        Quote quote = SlugRacingOddsApi.on(slugId, raceName);
        this.odds = quote.odds;
        return quote.uid;
    }

    @Override
    public void accept(String quote) {
        SlugRacingOddsApi.agree(quote);
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


