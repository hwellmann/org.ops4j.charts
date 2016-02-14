package org.ops4j.charts.component;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ops4j.charts.component.model.ChartModel;
import org.ops4j.charts.component.model.Series;
import org.primefaces.util.ComponentUtils;

public class PieRenderer extends AbstractChartRenderer {

    @Override
    protected void encodeData(FacesContext context, Chart chart) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        ChartModel model = (ChartModel) chart.getModel();

        writer.write(",data:{labels: [");

        for (Iterator<Object> it = model.getLabels().iterator(); it.hasNext();) {
            writeLabel(it, writer);
        }
        writer.write("], series:[");
        writeSeries(model.getSeries().get(0), writer);
        writer.write("]}");
    }

    private void writeSeries(Series series, ResponseWriter writer) throws IOException {
        for (Iterator<Number> numIt = series.getData().iterator(); numIt.hasNext();) {
            writeNumber(numIt, writer);
        }
    }

    private void writeNumber(Iterator<Number> numIt, ResponseWriter writer) throws IOException {
        Number number = numIt.next();
        String numberAsString = (number != null) ? number.toString() : "null";

        writer.write(numberAsString);

        if (numIt.hasNext()) {
            writer.write(",");
        }
    }

    private void writeLabel(Iterator<Object> it, ResponseWriter writer) throws IOException {
        Object label = it.next();

        if (label instanceof String) {
            writer.write("\"" + ComponentUtils.escapeText(label.toString()) + "\"");
        }
        else {
            writer.write(label != null ? label.toString() : "");
        }

        if (it.hasNext()) {
            writer.write(",");
        }
    }

    @Override
    protected void encodeOptions(FacesContext context, Chart chart) throws IOException {
        super.encodeOptions(context, chart);

        ResponseWriter writer = context.getResponseWriter();
        writer.write(",options:{");

        writer.write("}");
    }
}
