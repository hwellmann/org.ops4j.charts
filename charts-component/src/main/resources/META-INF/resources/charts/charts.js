/**
 * @namespace Charts namespace.
 */

(function(ops4j, Charts, $, undefined) {

    Charts.cw = function(widgetName, widgetVar, cfg) {
        if (Charts.widget[widgetName]) {
            initWidget(widgetName, widgetVar, cfg);
        }
    }

    function initWidget(widgetName, widgetVar, cfg) {
        if (PrimeFaces.widgets[widgetVar]) {
            PrimeFaces.widgets[widgetVar].refresh(cfg);
        }
        else {
            PrimeFaces.widgets[widgetVar] = new Charts.widget[widgetName](cfg);
            if (PrimeFaces.settings.legacyWidgetNamespace) {
                window[widgetVar] = PrimeFaces.widgets[widgetVar];
            }
        }
    }

    function processOptions() {
        if (this.cfg.showTooltip) {
            tooltip.call(this, this.cfg.tooltipBuilder);
        }

        if (this.cfg.showLegend) {
            var options = {
                clickable : this.cfg.legendClickable != false
            };
            createLegend.call(this, options);
        }
    }

    function tooltip(tooltipBuilder) {
        var $chart = this.jq;
        var $toolTip = $chart.append('<div class="ct-tooltip"></div>').find('.ct-tooltip').hide();
        var chartType = this.type;

        $chart.on('mouseenter', '.ct-point, .ct-bar, .ct-slice-pie, .ct-slice-donut', function() {
            var $point = $(this);
            var value = $point.attr('ct:value');
            var meta = $point.attr('ct:meta');
            var seriesName = $point.parent().attr('ct:series-name');

            var tooltipText = value;
            if (chartType !== 'Pie') {
                tooltipText = seriesName + '<br>' + value;
            }
            if (tooltipBuilder) {
                tooltipText = tooltipBuilder(value, meta, seriesName);
            }
            $toolTip.html(tooltipText).show();
        });

        $chart.on('mouseleave', '.ct-point, .ct-bar, .ct-slice-pie, .ct-slice-donut', function() {
            $toolTip.hide();
        });

        $chart.on('mousemove', function(event) {
            $toolTip.css({
                left : (event.originalEvent.layerX || event.offsetX) - $toolTip.width() / 2 - 10,
                top : (event.originalEvent.layerY || event.offsetY) - $toolTip.height() - 40
            });
        });
    }

    function createLegend(options) {

        var $chart = this.jq;
        var chart = this.chart;

        var legendElement = document.createElement('ul');
        legendElement.className = 'ct-legend';
        if (chart instanceof Chartist.Pie) {
            legendElement.classList.add('ct-legend-inside');
        }
        if (typeof options.className === "string" && options.className.length > 0) {
            legendElement.classList.add(options.className);
        }

        // Get the right array to use for generating the legend.
        var legendNames = chart.data.series;
        if (chart instanceof Chartist.Pie) {
            legendNames = chart.data.labels;
        }

        // Loop through all legends to set each name in a list item.
        legendNames.forEach(function(legend, i) {
            var li = document.createElement('li');
            li.className = 'ct-legend-' + Chartist.alphaNumerate(i);
            li.setAttribute('data-legend', i);
            li.textContent = legend.name || legend;
            legendElement.appendChild(li);
        });
        chart.container.appendChild(legendElement);

        if (options.clickable) {
            legendElement.addEventListener('click', function(event) {
                var li = event.target;
                if (li.parentNode !== legendElement || !li.hasAttribute('data-legend')) {
                    return;
                }
                event.preventDefault();

                var seriesIndex = parseInt(li.getAttribute('data-legend'));
                var seriesClassName = chart.options.classNames.series + '-'
                        + Chartist.alphaNumerate(seriesIndex);
                var $series = $chart.find('.' + seriesClassName);
                if ($series) {
                    if ($series.attr("visibility") === "hidden") {
                        $series.attr({
                            visibility : "visible"
                        });
                        li.classList.remove('inactive');
                    }
                    else {
                        $series.attr({
                            visibility : "hidden"
                        });
                        li.classList.add('inactive');
                    }
                }
            });
        }
    }

    /**
     * @namespace Namespace for widgets.
     */
    Charts.widget = {};

    Charts.widget.Chart = PrimeFaces.widget.BaseWidget.extend({
        init : function(cfg) {

            this._super(cfg);
            this.type = this.cfg.type;
            this.data = this.cfg.data;
            this.options = this.cfg.options;
            this.responsiveOptions = this.cfg.responsiveOptions;

            this.chart = new Chartist[this.type](this.jqId, this.data, this.options,
                    this.responsiveOptions);

            processOptions.call(this);
            this.bindEvents();
        },

        bindEvents : function() {
            var $this = this;

            if (this.cfg.behaviors && this.cfg.behaviors['itemSelect']) {
                if (this.type == 'Line') {
                    this.jq.on('click', '.ct-point', function(event) {
                        $this.invokeItemSelectBehavior(event, $(this).index() - 1, $(this).parent()
                                .parent().find('.ct-series').index($(this).parent()))
                    });
                }
                else if (this.type == 'Bar') {
                    this.jq.on('click', '.ct-bar', function(event) {
                        $this.invokeItemSelectBehavior(event, $(this).index(), $(this).parent()
                                .parent().find('.ct-series').index($(this).parent()))
                    });
                }
                else if (this.type == 'Pie') {
                    this.jq.on('click', '.ct-slice', function(event) {
                        var element = jQuery(this).parent();
                        var reverseIndex = element.parent().children().length
                                - (element.index() + 1);
                        $this.invokeItemSelectBehavior(event, reverseIndex, reverseIndex)
                    });
                }

            }
        },

        invokeItemSelectBehavior : function(event, itemIndex, seriesIndex) {
            if (this.cfg.behaviors) {
                var itemSelectBehavior = this.cfg.behaviors['itemSelect'];

                if (itemSelectBehavior) {
                    var ext = {
                        params : [ {
                            name : 'itemIndex',
                            value : itemIndex
                        }, {
                            name : 'seriesIndex',
                            value : seriesIndex
                        } ]
                    };

                    itemSelectBehavior.call(this, ext);
                }
            }
        }

    });

}(window.ops4j = window.ops4j || {}, window.ops4j.Charts = window.ops4j.Charts || {}, jQuery));
