package com.kldv.gibought.Utils.Graph;

public class DalesData {
    String Text;
    double values;

    public DalesData(String text, double values) {
        Text = text;
        this.values = values;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public double getValues() {
        return values;
    }

    public void setValues(double values) {
        this.values = values;
    }
}
