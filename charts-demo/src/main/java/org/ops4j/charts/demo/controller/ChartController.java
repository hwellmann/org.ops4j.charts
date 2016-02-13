package org.ops4j.charts.demo.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ops4j.charts.component.model.ChartModel;
import org.ops4j.charts.component.model.Series;
import org.primefaces.event.ItemSelectEvent;

@Named
@ViewScoped
public class ChartController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ChartModel lineModel;


    @PostConstruct
    private void init() {
        createLineChartModel();
    }


    private void createLineChartModel() {
        lineModel = new ChartModel();
        lineModel.addLabel("Montag");
        lineModel.addLabel("Dienstag");
        lineModel.addLabel("Mittwoch");
        lineModel.addLabel("Donnerstag");
        lineModel.addLabel("Freitag");


        Series january = new Series("Januar");
        january.setData(Arrays.asList(32, 17, 25, 28, 24));
        lineModel.addSeries(january);

        Series february = new Series("Februar");
        february.setData(Arrays.asList(12, 14, 15, 18, 17));
        lineModel.addSeries(february);
    }

    public ChartModel getLineModel() {
        return lineModel;
    }


    public void onItemSelect(ItemSelectEvent event) {
        System.out.println(String.format("selected series %d, item %d", event.getSeriesIndex(), event.getItemIndex()));
    }
}
