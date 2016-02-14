package org.ops4j.charts.demo.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ops4j.charts.component.model.ChartModel;
import org.primefaces.event.ItemSelectEvent;

@Named
@ViewScoped
public class ChartController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ChartModel lineModel;
    private ChartModel pieModel;

    @PostConstruct
    private void init() {
        createLineChartModel();
        createPieChartModel();
    }

    private void createLineChartModel() {
        lineModel = new ChartModel();
        lineModel.addLabel("Montag");
        lineModel.addLabel("Dienstag");
        lineModel.addLabel("Mittwoch");
        lineModel.addLabel("Donnerstag");
        lineModel.addLabel("Freitag");

        lineModel.addSeries("Januar", 32, 17, 25, 28, 24);
        lineModel.addSeries("Februar", 12, 14, 15, 18, 17);
    }

    private void createPieChartModel() {
        pieModel = new ChartModel();
        pieModel.addLabel("Ja");
        pieModel.addLabel("Nein");
        pieModel.addLabel("Weiß nicht");
        pieModel.addSeries(7, 4, 2);
    }

    public ChartModel getLineModel() {
        return lineModel;
    }

    public ChartModel getPieModel() {
        return pieModel;
    }

    public void onItemSelect(ItemSelectEvent event) {
        System.out.println(String.format("selected series %d, item %d", event.getSeriesIndex(),
            event.getItemIndex()));
    }
}
