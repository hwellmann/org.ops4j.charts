package org.ops4j.charts.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;

public class ChartRenderer extends CoreRenderer {

    private static final Logger logger = Logger.getLogger(ChartRenderer.class.getName());

    public static final String RENDERER_TYPE = "org.ops4j.charts.component.ChartRenderer";

    private final static String TYPE_LINE = "Line";
    private final static String TYPE_BAR = "Bar";
    private final static String TYPE_PIE = "Pie";

    final static Map<String, AbstractChartRenderer> CHART_RENDERERS;

    static {
        CHART_RENDERERS = new HashMap<String, AbstractChartRenderer>();
        CHART_RENDERERS.put(TYPE_LINE, new LineRenderer());
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        super.decodeBehaviors(context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Chart chart = (Chart) component;

        encodeMarkup(context, chart);
        encodeScript(context, chart);
    }

    protected void encodeMarkup(FacesContext context, Chart chart) throws IOException {

        if (chart == null || chart.getModel() == null) {
            logger.warning("ChartModel is null, make sure it's initialized");
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        String style = chart.getStyle();
        String styleClass = chart.getStyleClass() == null ? "" : " " + chart.getStyleClass();

        writer.startElement("div", null);
        writer.writeAttribute("id", chart.getClientId(context), null);
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }
        writer.writeAttribute("class", styleClass, "styleClass");

        writer.endElement("div");
    }

    protected void encodeScript(FacesContext context, Chart chart) throws IOException {
        if (chart == null || chart.getModel() == null) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        String type = chart.getType();
        AbstractChartRenderer chartistRenderer = CHART_RENDERERS.get(type);
        String clientId = chart.getClientId(context);

        startScript(writer, clientId);

        writer.write("$(function(){");
        writer.write("ops4j.Charts.cw('Chart','" + chart.resolveWidgetVar() + "',{");
        writer.write("id:'" + clientId + "'");
        writer.write(",type:'" + type + "'");
        if (chart.getTooltipBuilder() != null) {
            writer.write(",tooltipBuilder:" + chart.getTooltipBuilder());
        }
        if (chart.isShowTooltip() != null) {
            writer.write(",showTooltip:" + chart.isShowTooltip());
        }
        if (chart.isShowLegend() != null) {
            writer.write(",showLegend:" + chart.isShowLegend());
        }

        if (chart.isLegendClickable() != null) {
            writer.write(",legendClickable:" + chart.isLegendClickable());
        }

        chartistRenderer.render(context, chart);
        encodeClientBehaviors(context, chart);
        writer.write("});});");

        endScript(writer);
    }
}
