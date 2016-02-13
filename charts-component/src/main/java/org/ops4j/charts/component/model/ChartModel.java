package org.ops4j.charts.component.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChartModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Object> labels = new ArrayList<>();

    private List<Series> series = new ArrayList<>();


    /**
     * @return the labels
     */
    public List<Object> getLabels() {
        return labels;
    }


    /**
     * @param labels the labels to set
     */
    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    public void addLabel(String label) {
        labels.add(label);
    }


    /**
     * @return the series
     */
    public List<Series> getSeries() {
        return series;
    }


    /**
     * @param series the series to set
     */
    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public void addSeries(Series series) {
        this.series.add(series);
    }

}
