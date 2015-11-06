// Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package ca.ualberta.trinkettrader;

import java.util.ArrayList;

public class TradeArchiver {

    private ArrayList<Trade> currentTrades;
    private ArrayList<Trade> pastTrades;


    public TradeArchiver() {
    }

    public void addTrade(Trade trade) { currentTrades.add(trade); }

    public void archiveTrade(Trade trade) { pastTrades.add(trade); }

    // TODO implementation details: will only be used to update currentTrades
    public void deleteTrade(Trade trade) {
        // find trade in list and delete. need to delete anything anywhere else?
    }

    public ArrayList<Trade> getPastTrades() { return pastTrades; }

    public ArrayList<Trade> getCurrentTrades() { return currentTrades; }

    // will return trade from ArrayList of pastTrades
    public Trade getPastTrade(Trade trade){
        return trade;
    }

    public Boolean hasCurrentTrade(Trade trade){
        return Boolean.TRUE;
    }

    public Boolean hasPastTrade(Trade trade){
        return Boolean.TRUE;
    }

    public Boolean isCurrentTradesEmpty(){ return currentTrades.isEmpty(); }

    public Boolean isPastTradesEmpty(){ return pastTrades.isEmpty(); }
}
