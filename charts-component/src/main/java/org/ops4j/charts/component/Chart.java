package org.ops4j.charts.component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.FacesEvent;

import org.ops4j.charts.component.model.ChartModel;
import org.primefaces.event.ItemSelectEvent;

@ResourceDependencies({ @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
    @ResourceDependency(library = "primefaces", name = "primefaces.js"),
    @ResourceDependency(library = "charts", name = "chartist/chartist.min.css"),
    @ResourceDependency(library = "charts", name = "chartist/chartist.min.js"),
    @ResourceDependency(library = "charts", name = "charts.js") })
public class Chart extends UIComponentBase implements org.primefaces.component.api.Widget,
    javax.faces.component.behavior.ClientBehaviorHolder {

    public static final String COMPONENT_TYPE = "org.ops4j.charts.component.Chart";
    public static final String COMPONENT_FAMILY = "org.ops4j.charts";

    private final static String DEFAULT_EVENT = "itemSelect";

    private static final Collection<String> EVENT_NAMES = Collections.singletonList(DEFAULT_EVENT);

    protected enum PropertyKeys {

        widgetVar, type, model, style, styleClass, tooltipBuilder, showLegend, legendClickable;

    }

    public Chart() {
        setRendererType(ChartRenderer.RENDERER_TYPE);
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public java.lang.String getType() {
        String type = (java.lang.String) getStateHelper().eval(PropertyKeys.type, null);
        type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        return type;
    }

    public void setType(java.lang.String _type) {
        getStateHelper().put(PropertyKeys.type, _type);
    }

    public ChartModel getModel() {
        return (ChartModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(ChartModel _model) {
        getStateHelper().put(PropertyKeys.model, _model);
    }

    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
    }

    public void setStyle(java.lang.String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
    }

    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    public void setStyleClass(java.lang.String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }

    public java.lang.String getTooltipBuilder() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.tooltipBuilder, null);
    }

    public void setTooltipBuilder(java.lang.String _builder) {
        getStateHelper().put(PropertyKeys.tooltipBuilder, _builder);
    }

    public java.lang.Boolean isShowLegend() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.showLegend, null);
    }

    public void setShowLegend(java.lang.Boolean _showLegend) {
        getStateHelper().put(PropertyKeys.showLegend, _showLegend);
    }

    public java.lang.Boolean isLegendClickable() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.legendClickable, null);
    }

    public void setLegendClickable(java.lang.Boolean _legendClickable) {
        getStateHelper().put(PropertyKeys.legendClickable, _legendClickable);
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    @Override
    public String getDefaultEventName() {
        return DEFAULT_EVENT;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        if (event instanceof AjaxBehaviorEvent) {
            BehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            Map<String, String> map = getFacesContext().getExternalContext()
                .getRequestParameterMap();
            int itemIndex = Integer.parseInt(map.get("itemIndex"));
            int seriesIndex = Integer.parseInt(map.get("seriesIndex"));

            ItemSelectEvent itemSelectEvent = new ItemSelectEvent(this, behaviorEvent.getBehavior(),
                itemIndex, seriesIndex);

            super.queueEvent(itemSelectEvent);
        }
    }

    @Override
    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null) {
            return userWidgetVar;
        }
        else {
            return "widget_" + getClientId(context)
                .replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }
}
