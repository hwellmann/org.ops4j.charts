package org.ops4j.charts.component;

import java.io.IOException;

import javax.faces.context.FacesContext;

public abstract class AbstractChartRenderer {

    public void render(FacesContext context, Chart chart) throws IOException {
        encodeData(context, chart);
        encodeOptions(context, chart);
    }

    protected abstract void encodeData(FacesContext context, Chart chart) throws IOException;

    protected void encodeOptions(FacesContext context, Chart chart) throws IOException {

    }
}
