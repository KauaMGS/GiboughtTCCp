package com.kldv.gibought.Utils.Graph.interfaces.dataprovider;

import com.kldv.gibought.Utils.Graph.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isHighlightFullBarEnabled();
}
