function buildTooltip(value, meta, seriesName) {
    var tooltip = 'Tip: ' + seriesName + '<br>';
    if (meta) {
        tooltip += meta + ' : ';
    }
    tooltip += value;
    return tooltip;
}

