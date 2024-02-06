package com.kldv.gibought.Utils.Graph.interfaces.dataprovider;

import com.kldv.gibought.Utils.Graph.components.YAxis;
import com.kldv.gibought.Utils.Graph.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
