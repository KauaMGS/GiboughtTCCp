package com.kldv.gibought.Utils.Graph.interfaces.dataprovider;

import com.kldv.gibought.Utils.Graph.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
