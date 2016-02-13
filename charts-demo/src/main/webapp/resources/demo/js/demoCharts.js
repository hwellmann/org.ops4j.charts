function buildTooltip(value, meta, seriesName) {
	return 'Tip:' + seriesName + '<br>' + meta + ' : ' + value;
};

function responsiveOptions() {
	return [ [ 'screen and (min-width: 800px) and (max-width: 1400px)', {
		seriesBarDistance : 5,
		axisX : {
			labelInterpolationFnc : function(value) {
				return value.substring(0, 3);
			}
		}
	} ], [ 'screen and (max-width: 800px)', {
		seriesBarDistance : 5,
		axisX : {
			labelInterpolationFnc : function(value) {
				return value[0];
			}
		}
	} ] ];
}
