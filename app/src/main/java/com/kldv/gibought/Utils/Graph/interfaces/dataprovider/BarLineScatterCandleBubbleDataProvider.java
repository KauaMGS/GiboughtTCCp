package com.kldv.gibought.Utils.Graph.interfaces.dataprovider;

import com.kldv.gibought.Utils.Graph.components.YAxis.AxisDependency;
import com.kldv.gibought.Utils.Graph.data.BarLineScatterCandleBubbleData;
import com.kldv.gibought.Utils.Graph.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
