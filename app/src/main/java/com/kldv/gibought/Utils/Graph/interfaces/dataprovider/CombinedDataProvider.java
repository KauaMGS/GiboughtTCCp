package com.kldv.gibought.Utils.Graph.interfaces.dataprovider;

import com.kldv.gibought.Utils.Graph.data.CombinedData;

/**
 * Created by philipp on 11/06/16.
 */
public interface CombinedDataProvider extends LineDataProvider, BarDataProvider, BubbleDataProvider, CandleDataProvider, ScatterDataProvider {

    CombinedData getCombinedData();
}
