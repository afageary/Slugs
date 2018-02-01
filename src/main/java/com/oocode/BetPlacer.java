package com.oocode;

import com.teamoptimization.*;

import java.math.BigDecimal;

public class BetPlacer {

    SlugSwapsApi slugSwapsApi;
    SlugRacingOddsApi slugRacingOddsApi;

    BetPlacer(SlugSwapsApi slugSwapsApi, SlugRacingOddsApi slugRacingOddsApi) {
        this.slugSwapsApi = slugSwapsApi;
        this.slugRacingOddsApi = slugRacingOddsApi;
    }
    BetPlacer() {
        slugSwapsApi = new SlugSwapsApi();
        slugRacingOddsApi = new SlugRacingOddsApi();
    }
    public static void main(String[] args) throws Exception {
        /* Results usually look like a bit like one of the following:
           Time out on SlugSwaps
           accepted quote = 14281567-1fde-4996-a61f-0ba60b2c95c0 with offered odds 0.87
           accepted quote = dada5f35-c244-4da6-a370-648ea35f7a03 with required odds 0.50
        */

        // Note that the names of today’s races change every day!
        new BetPlacer().placeBet(3, "The Thursday race", new BigDecimal("0.50"));
    }

    public void placeBet(int slugId, String raceName, BigDecimal targetOdds) {
        String result;
        long quotationTime = System.currentTimeMillis();
        Race race = slugSwapsApi.forRace(raceName);
        if (race == null) {
            result = null;
        } else {
            result = race.quote(slugId, targetOdds);
            quotationTime = System.currentTimeMillis();
        }
        String p2p = result;
        Quote b = slugRacingOddsApi.on(slugId, raceName);
        if (p2p != null && targetOdds.compareTo(b.odds) >= 0) {
            if(quotationTime + 1000L > System.currentTimeMillis()) {
                try {
                    slugSwapsApi.accept(p2p);
                } catch (SlugSwaps.Timeout timeout) {
                    if (b.odds.compareTo(targetOdds) == 0)
                        slugRacingOddsApi.agree(b.uid);
                }
            }
            else {
                if (b.odds.compareTo(targetOdds) == 0)
                    slugRacingOddsApi.agree(b.uid);
            }
        } else {
            if (b.odds.compareTo(targetOdds) >= 0) {
                slugRacingOddsApi.agree(b.uid);
            }
        }
    }
}
